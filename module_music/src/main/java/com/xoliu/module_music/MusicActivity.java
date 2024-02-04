package com.xoliu.module_music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoliu.module_music.databinding.ActivityMusicBinding;

@Route(path = "/music/main")
public class MusicActivity extends AppCompatActivity {

    private ActivityMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();

        initData();

    }

    private void initData() {

    }

    private void initView() {
        // 使用 Glide 加载图片并裁剪成圆形
        Glide.with(this)
                .load(R.drawable.singer_libai)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.singerImage);

    }
}