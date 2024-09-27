package com.example.appxemphim_nhom6.ui;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim_nhom6.R;
import com.example.appxemphim_nhom6.data.model.Movie;

import java.util.List;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;
    private OnItemClickListener listener;  // Thêm listener để xử lý sự kiện click

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, Context context, OnItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;  // Gán listener
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);

        // Load poster bằng Glide
        Glide.with(context)
                .load(movie.getPosterUrl())
                .into(holder.imageViewPoster);

        // Xử lý sự kiện khi nhấn vào poster
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(movie);  // Gọi hàm khi người dùng nhấn vào item
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewYear;
        ImageView imageViewPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewYear = itemView.findViewById(R.id.text_view_year);
            imageViewPoster = itemView.findViewById(R.id.image_view_poster);
        }

        public void bind(Movie movie) {
            textViewTitle.setText(movie.getName());
            textViewYear.setText(String.valueOf(movie.getYear()));
        }
    }
}

