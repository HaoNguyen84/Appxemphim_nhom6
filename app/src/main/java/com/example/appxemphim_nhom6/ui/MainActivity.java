package com.example.appxemphim_nhom6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim_nhom6.R;
import com.example.appxemphim_nhom6.data.model.Movie;
import com.example.appxemphim_nhom6.data.model.MovieResponse;
import com.example.appxemphim_nhom6.data.network.ApiService;
import com.example.appxemphim_nhom6.data.network.RetrofitClient;
import com.example.appxemphim_nhom6.adapter.MovieAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@UnstableApi
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList; // Danh sách phim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_movies);

        // Sử dụng GridLayoutManager với 4 cột
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        // Gọi hàm lấy phim
        fetchMovies();
    }

    private void fetchMovies() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getMovies().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<Movie> movies = response.body().getItems();
                    // Khởi tạo adapter với OnItemClickListener
                    movieAdapter = new MovieAdapter(movies, MainActivity.this, new MovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Movie movie) {
                            // Chuyển đến MovieDetailActivity khi nhấn vào một bộ phim
                            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                            intent.putExtra("slug", movie.getSlug()); // Truyền slug
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


