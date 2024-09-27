package com.example.appxemphim_nhom6.data.model;
import java.util.List;
public class MovieResponse {
    private boolean status;
    private List<Movie> items;

    public boolean isStatus() {
        return status;
    }

    public List<Movie> getItems() {
        return items;
    }
}
