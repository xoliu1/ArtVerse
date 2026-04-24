package com.xoliu.module_music.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import com.xoliu.module_music.R;
import com.xoliu.module_music.databinding.ActivityCdactivityBinding;

public class CDActivity extends AppCompatActivity {

    private ActivityCdactivityBinding binding;

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCdactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initView();
        initListener();

    }

    private void initListener() {
        binding.btnStart.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                startMusicPlayback();
                binding.btnStart.setImageResource(R.drawable.pause);
            } else {
                stopMusicPlayback();
                binding.btnStart.setImageResource(R.drawable.n2);
            }
        });


        binding.musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    jumpToProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
                startMusicPlayback();
            }
        });
    }

    private void jumpToProgress(int progress) {
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration();
            int newPosition = (int) ((duration / 100) * progress);
            mediaPlayer.seekTo(newPosition);
        }
    }

    private void initData() {
        // 接收外部传入的音频资源 ID，如果没有传入则使用默认音频
        int audioResId = getIntent().getIntExtra("audio_res_id", 0);
        if (audioResId == 0) {
            audioResId = R.raw.libai_jiangjinjiu;
        }
        mediaPlayer = MediaPlayer.create(CDActivity.this, audioResId);
    }

    RotateAnimation animation;
    private void initView() {
         animation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(30000);
        animation.setRepeatCount(Animation.INFINITE);
    }

    private Handler handler = new Handler();

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int totalDuration = mediaPlayer.getDuration();
                int progress = (int) ((currentPosition * 100) / totalDuration);
                binding.musicSeekbar.setProgress(progress);
            }
            handler.postDelayed(this, 1000);
        }
    };

    private void startMusicPlayback() {
        mediaPlayer.start();
        handler.post(updateSeekBar);
        binding.ivCd.startAnimation(animation);
    }

    private void stopMusicPlayback() {
        mediaPlayer.pause();
        binding.ivCd.clearAnimation();
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMusicPlayback();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}