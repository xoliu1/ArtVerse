package com.xoliu.module_music.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoliu.module_music.R;
import com.xoliu.module_music.databinding.ActivityMusicBinding;
import com.xoliu.module_music.model.MusicRepository;
import com.xoliu.module_music.model.bean.Recitation;
import com.xoliu.module_music.model.bean.Song;
import com.xoliu.module_music.view.adapter.RecitationAdapter;
import com.xoliu.module_music.view.adapter.SongAdapter;
import com.xoliu.module_music.viewmodel.MusicViewModel;

import java.util.List;

@Route(path = "/music/main")
public class MusicActivity extends AppCompatActivity {

    private ActivityMusicBinding binding;

    private List<Song> songList ;

    private  List<Recitation> recitationList;


    MusicViewModel viewModel;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        mediaPlayer = MediaPlayer.create(MusicActivity.this, R.raw.libai_jiangjinjiu);
        initListener();
        initData();
        initView();
        setSupportActionBar(binding.toolbar);

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set the toolbar navigation click listener
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button press
                onBackPressed();
            }
        });


    }

    private void initListener() {
        //设置点击事件
        binding.btnInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, CDActivity.class));
        });

        binding.singerImage.setOnClickListener(v -> {
            startActivity(new Intent(this, CDActivity.class));
        });

        binding.btnOn.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start(); // 开始播放音乐

                binding.btnOn.setImageResource(R.drawable.pause2);
            } else {
                mediaPlayer.pause(); // 暂停播放音乐
                binding.btnOn.setImageResource(R.drawable.btn_on);
            }
        });
    }

    private void initData() {
//        viewModel.getRecitationList().observe(this, new Observer<list<Recitation>>() {
//            @Override
//            public void onChanged(list<Recitation> recitations) {
//                recitationList = recitations;
//            }
//        });
//        viewModel.getSongList().observe(this, new Observer<list<Song>>() {
//            @Override
//            public void onChanged(list<Song> songs) {
//                songList = songs;
//            }
//        });

        songList = MusicRepository.getInstance().getSongs();

        recitationList = MusicRepository.getInstance().getRecitations();


    }

    private void initView() {
        // 使用 Glide 加载图片并裁剪成圆形
        Glide.with(this)
                .load(R.drawable.singer_libai)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.singerImage);

        binding.rvOrigin.setLayoutManager(new LinearLayoutManager(this));
        binding.rvOrigin.setAdapter(new RecitationAdapter(this, recitationList));


        binding.rvPopular.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPopular.setAdapter(new SongAdapter(this, songList));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // 释放MediaPlayer资源
            mediaPlayer = null;
        }
    }
}