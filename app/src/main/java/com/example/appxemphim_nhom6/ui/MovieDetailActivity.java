package com.example.appxemphim_nhom6.ui;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;
import com.bumptech.glide.Glide;
import com.example.appxemphim_nhom6.R;
import com.example.appxemphim_nhom6.data.model.Episode;
import com.example.appxemphim_nhom6.data.model.Movie;
import com.example.appxemphim_nhom6.data.model.MovieDetailResponse;
import com.example.appxemphim_nhom6.data.network.ApiService;
import com.example.appxemphim_nhom6.data.network.RetrofitClient;
import com.example.appxemphim_nhom6.data.model.ServerData;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@UnstableApi
public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle, textViewDescription, textViewYear, textViewActors, textViewDirector;
    private Button buttonWatchMovie; // Thêm biến cho nút "Xem Phim"
    private String movieSlug; // Biến để lưu slug
    private String movieLink; // Biến để lưu link phim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Khởi tạo các view
        imageViewPoster = findViewById(R.id.image_view_detail_poster);
        textViewTitle = findViewById(R.id.text_view_detail_title);
        textViewDescription = findViewById(R.id.text_view_detail_content);
        textViewYear = findViewById(R.id.text_view_detail_year);
        textViewActors = findViewById(R.id.text_view_detail_actors);
        textViewDirector = findViewById(R.id.text_view_detail_director);
        buttonWatchMovie = findViewById(R.id.button_watch_movie); // Khởi tạo nút "Xem Phim"

        // Lấy slug từ Intent
        movieSlug = getIntent().getStringExtra("slug");

        // Lấy chi tiết phim
        fetchMovieDetail();

        // Xử lý sự kiện click nút xem phim
        buttonWatchMovie.setOnClickListener(view -> {
            if (movieLink != null && !movieLink.isEmpty()) {
                // Khởi động activity phát video
                Intent intent = new Intent(this, WatchMovieActivity.class);
                intent.putExtra("movie_link", movieLink);  // Truyền link phim
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, "Link phim không khả dụng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovieDetail() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getMovieDetail(movieSlug).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Movie movie = response.body().getMovie();

                    // Hiển thị thông tin phim
                    textViewTitle.setText(movie.getName());
                    textViewDescription.setText(movie.getContent());
                    textViewYear.setText(String.valueOf(movie.getYear()));
                    textViewActors.setText(TextUtils.join(", ", movie.getActor()));
                    textViewDirector.setText(TextUtils.join(", ", movie.getDirector()));

                    // Tải poster bằng Glide
                    Glide.with(MovieDetailActivity.this)
                            .load(movie.getPosterUrl())
                            .into(imageViewPoster);

                    // Lấy link_embed từ episodes
                    List<Episode> episodes = response.body().getEpisodes();
                    if (episodes != null && !episodes.isEmpty()) {
                        ServerData serverData = episodes.get(0).getServerData().get(0);
                        movieLink = serverData.getLinkEmbed(); // Lưu link phim
                        Log.d("MovieDetailActivity", "Movie Link: " + movieLink);
                    }

                } else {
                    Toast.makeText(MovieDetailActivity.this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}





