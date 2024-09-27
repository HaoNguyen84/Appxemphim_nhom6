package com.example.appxemphim_nhom6.data.network;
import com.example.appxemphim_nhom6.data.model.MovieDetailResponse;
import com.example.appxemphim_nhom6.data.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiService {

    // API danh sách phim
    @GET("danh-sach/phim-moi-cap-nhat?page=1")
    Call<MovieResponse> getMovies();

    // API chi tiết phim
    @GET("phim/{slug}")
    Call<MovieDetailResponse> getMovieDetail(@Path("slug") String slug);
}


