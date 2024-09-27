package com.example.appxemphim_nhom6.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://phimapi.com/";  // Thay bằng URL API thực tế
    private static Retrofit retrofit = null;

    // Phương thức để lấy đối tượng Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Đặt base URL của API
                    .addConverterFactory(GsonConverterFactory.create())  // Sử dụng Gson cho việc parse JSON
                    .build();
        }
        return retrofit;
    }

    // Phương thức để lấy đối tượng ApiService
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}
