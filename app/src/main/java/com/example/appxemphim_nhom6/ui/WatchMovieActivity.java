package com.example.appxemphim_nhom6.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;
import com.example.appxemphim_nhom6.R;
import androidx.media3.common.util.UnstableApi; // Import UnstableApi annotation

@UnstableApi // Đánh dấu class này sử dụng các API không ổn định
public class WatchMovieActivity extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        playerView = findViewById(R.id.player_view);
        //String movieLink = getIntent().getStringExtra("movie_link");
        String movieLink = "https://s5.phim1280.tv/20240928/DZnilRfG/index.m3u8";
        if (movieLink != null) {
            // Tạo đối tượng ExoPlayer
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            // Tạo HlsMediaSource
            DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
            HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(movieLink));

// Thêm video vào player
            player.setMediaSource(hlsMediaSource);
            player.prepare();
            player.play();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.play();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
