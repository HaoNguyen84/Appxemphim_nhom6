package com.example.appxemphim_nhom6.data.network;
import com.example.appxemphim_nhom6.data.model.MovieDetailResponse;
import com.example.appxemphim_nhom6.data.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // API danh sách phim với tham số trang
    @GET("danh-sach/phim-moi-cap-nhat")
    Call<MovieResponse> getMovies(@Query("page") int page);

    // API chi tiết phim
    @GET("phim/{slug}")
    Call<MovieDetailResponse> getMovieDetail(@Path("slug") String slug);
}


