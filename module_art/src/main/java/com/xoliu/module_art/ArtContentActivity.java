package com.xoliu.module_art;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xoliu.module_art.databinding.ActivityArtContentBinding;

/***
 * 点击卡片进入的卡片详情界面
 * @author xoliu
 * @create 23-12-11
 **/

public class ArtContentActivity extends AppCompatActivity {

    private ActivityArtContentBinding binding;

    ArtContent artContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initView();
    }

    private void initView() {
        //设置数据
        binding.artContentImg.setImageResource(artContent.artContentImg);
        binding.artContentName.setText(artContent.name);
        binding.artContentAuthor.setText(artContent.creator);
        binding.artContentYear.setText(artContent.year);
        binding.artContentMaterial.setText(artContent.material);
        binding.artContentSize.setText(artContent.size);
        binding.artContentInfo.setText(artContent.content);

        //设置返回
        binding.btnClose.setOnClickListener(v -> {
            finish();
        });

        //设置缩放动画
        binding.artContentImg.setVisibility(View.VISIBLE);
        binding.artContentImg.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(1000)
                .start();


        // 设置进入动画，提出的不生效
        overridePendingTransition(com.xoliu.common.R.anim.anim_enter, com.xoliu.common.R.anim.anim_exit);
    }

    private void initData() {
        //初始化信息
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            artContent = getIntent().getParcelableExtra("theArtCardContentInfo", ArtContent.class);
        }
    }


    @Override
    public void finish() {
        super.finish();
        // 设置退出动画
        overridePendingTransition(com.xoliu.common.R.anim.anim_enter, com.xoliu.common.R.anim.anim_exit);
    }
}