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
                mediaPlayer.start(); // 开始播放音乐
                startMusicPlayback();
                binding.btnStart.setImageResource(R.drawable.pause);
                binding.ivCd.startAnimation(animation);
            } else {
                mediaPlayer.pause(); // 暂停播放音乐
                stopMusicPlayback();
                binding.btnStart.setImageResource(R.drawable.n2);
                binding.ivCd.clearAnimation();
            }
        });


        binding.musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 在进度变化时，更新音乐的播放进度
                if (fromUser) {
                    // 如果是用户通过点击SeekBar来触发的进度变化
                    // 将音乐的播放进度跳转到对应节点
                    jumpToProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 在开始拖动SeekBar时，暂停音乐播放
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 在停止拖动SeekBar时，恢复音乐播放
                mediaPlayer.start();
                startMusicPlayback();
            }
        });
    }

    private void jumpToProgress(int progress) {
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration(); // 获取音乐的总时长
            int newPosition = (int) ((duration / 100) * progress); // 计算新的播放位置

            mediaPlayer.seekTo(newPosition); // 将播放器的播放位置跳转到新位置
        }
    }

    private void initData() {
        mediaPlayer = MediaPlayer.create(CDActivity.this, R.raw.libai_jiangjinjiu);
    }

    RotateAnimation animation;
    private void initView() {
        // 创建旋转动画，设置旋转中心为图片中心点，角度从0到360度
         animation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(30000); // 设置动画持续时间为18秒
        animation.setRepeatCount(Animation.INFINITE); // 设置动画重复次数为无限循环


    }



    private Handler handler = new Handler();

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int currentPosition = mediaPlayer.getCurrentPosition(); // 获取当前播放位置
                int totalDuration = mediaPlayer.getDuration(); // 获取音乐的总时长

                // 计算当前播放位置在进度条上的百分比
                int progress = (int) ((currentPosition * 100) / totalDuration);

                // 更新进度条
                binding.musicSeekbar.setProgress(progress);
            }

            // 每隔一段时间更新进度条
            handler.postDelayed(this, 1000);
        }
    };

    private void startMusicPlayback() {
        // 初始化音乐播放器，开始播放音乐

        // 启动定时器
        handler.post(updateSeekBar);
    }

    private void stopMusicPlayback() {
        // 停止音乐播放器

        // 停止定时器
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMusicPlayback();
            mediaPlayer.release(); // 释放MediaPlayer资源
            mediaPlayer = null;
        }
    }
}