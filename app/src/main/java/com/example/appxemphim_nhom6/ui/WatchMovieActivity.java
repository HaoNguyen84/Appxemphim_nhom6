package com.example.appxemphim_nhom6.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim_nhom6.R;

public class WatchMovieActivity extends AppCompatActivity {
    private SimpleExoPlayer player; // Đổi ExoPlayer thành SimpleExoPlayer
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        playerView = findViewById(R.id.player_view);

        String movieLink = getIntent().getStringExtra("movie_link");
        if (movieLink != null) {
            // Tạo đối tượng SimpleExoPlayer
            player = new SimpleExoPlayer.Builder(this).build(); // Sử dụng SimpleExoPlayer
            playerView.setPlayer(player);

            // Thêm video vào player
            MediaItem mediaItem = MediaItem.fromUri(movieLink);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release(); // Giải phóng tài nguyên nếu player không null
            player = null; // Đặt player về null để tránh lỗi
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause(); // Tạm dừng phát video khi Activity bị tạm dừng
        }
    }
}
