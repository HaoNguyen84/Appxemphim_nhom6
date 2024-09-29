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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim_nhom6.R;
import com.example.appxemphim_nhom6.adapter.EpisodeAdapter;
import com.example.appxemphim_nhom6.data.model.Episode;
import com.example.appxemphim_nhom6.data.model.Movie;
import com.example.appxemphim_nhom6.data.model.MovieDetailResponse;
import com.example.appxemphim_nhom6.data.network.ApiService;
import com.example.appxemphim_nhom6.data.network.RetrofitClient;
import com.example.appxemphim_nhom6.data.model.ServerData;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@UnstableApi
public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle, textViewDescription, textViewYear, textViewActors, textViewDirector;
    private Button buttonWatchMovie;
    private String movieSlug;
    private String movieLink;
    private RecyclerView recyclerViewEpisodes;
    private EpisodeAdapter episodeAdapter;
    private List<ServerData> serverDataList = new ArrayList<>();


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
        buttonWatchMovie = findViewById(R.id.button_watch_movie);
        recyclerViewEpisodes = findViewById(R.id.recycler_view_episodes);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerViewEpisodes.setLayoutManager(layoutManager);
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

                    // Lấy danh sách các tập phim
                    List<Episode> episodes = response.body().getEpisodes();
                    if (episodes != null && !episodes.isEmpty()) {
                        // Lưu danh sách các tập phim
                        serverDataList.clear(); // Xóa danh sách cũ
                        for (Episode episode : episodes) {
                            List<ServerData> data = episode.getServerData();
                            if (data != null) {
                                serverDataList.addAll(data); // Thêm tất cả các tập phim vào danh sách
                            }
                        }

                        // Cập nhật RecyclerView với danh sách tập phim
                        episodeAdapter = new EpisodeAdapter(serverDataList, linkM3u8 -> {
                            // Khi người dùng click vào tập phim
                            Intent intent = new Intent(MovieDetailActivity.this, WatchMovieActivity.class);
                            intent.putExtra("movie_link", linkM3u8);
                            startActivity(intent);
                        });
                        recyclerViewEpisodes.setAdapter(episodeAdapter);

                        // Lấy link của tập đầu tiên
                        ServerData firstServerData = serverDataList.get(0);
                        if (firstServerData != null) {
                            movieLink = firstServerData.getLinkM3u8();
                            Log.d("MovieDetailActivity", "Link phim tập 1: " + movieLink);
                        }
                    } else {
                        Toast.makeText(MovieDetailActivity.this, "Không có tập phim nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Không thể tải thông tin chi tiết phim", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}