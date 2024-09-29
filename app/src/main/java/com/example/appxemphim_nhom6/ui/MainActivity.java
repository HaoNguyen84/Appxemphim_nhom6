package com.example.appxemphim_nhom6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@UnstableApi
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>(); // Danh sách phim
    private boolean isLoading = false; // Trạng thái đang tải phim
    private int currentPage = 1; // Trang hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_movies);

        // Sử dụng GridLayoutManager với 3 cột
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo adapter cho danh sách phim
        movieAdapter = new MovieAdapter(movieList, MainActivity.this, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                // Chuyển đến MovieDetailActivity khi nhấn vào một bộ phim
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("slug", movie.getSlug()); // Truyền slug phim
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(movieAdapter);

        // Gọi hàm lấy phim trang đầu tiên
        fetchMovies(currentPage);

        // Lắng nghe sự kiện cuộn để tải thêm phim khi đến cuối danh sách
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading) {
                    // Lấy vị trí của item cuối cùng hiển thị
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Kiểm tra nếu đã cuộn tới cuối danh sách
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        // Tải trang tiếp theo
                        currentPage++;
                        fetchMovies(currentPage);
                    }
                }
            }
        });
    }

    private void fetchMovies(int page) {
        isLoading = true; // Đánh dấu đang tải dữ liệu

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getMovies(page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<Movie> movies = response.body().getItems();

                    // Cập nhật danh sách phim
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged(); // Thông báo dữ liệu đã thay đổi

                    // Cập nhật tổng số trang nếu API trả về số này
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
                isLoading = false; // Hoàn thành tải dữ liệu
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                isLoading = false;
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


