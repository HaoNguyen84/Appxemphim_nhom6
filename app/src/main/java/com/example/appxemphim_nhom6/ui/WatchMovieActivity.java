package com.example.appxemphim_nhom6.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;
import com.example.appxemphim_nhom6.R;
import androidx.media3.common.util.UnstableApi;

@UnstableApi // Đánh dấu class này sử dụng các API không ổn định
public class WatchMovieActivity extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        playerView = findViewById(R.id.player_view);
        String movieLink = getIntent().getStringExtra("movie_link");
        if (movieLink != null) {
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);
            // Thêm listener để lắng nghe lỗi
            player.addListener(new Player.Listener() {
                @Override
                public void onPlayerError(PlaybackException error) {
                    Toast.makeText(WatchMovieActivity.this, "Phim lỗi vui lòng báo cáo cho admin or xem phim khác" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            // Tạo HlsMediaSource
            DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
            HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(movieLink));

            // Thêm video vào player
            player.setMediaSource(hlsMediaSource);
            player.prepare();
            player.play();
        } else {
            Toast.makeText(this, "Link phim không hợp lệ", Toast.LENGTH_SHORT).show();
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
