package com.xoliu.module_art;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import db.bean.GalleryBean;

/**
 * 个人画作详情页（在西域画展中点击个人作品卡片时跳转）
 */
public class ArtPersonalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_personal_detail);

        GalleryBean data = (GalleryBean) getIntent().getSerializableExtra("gallery_data");
        if (data == null) {
            finish();
            return;
        }

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // 大图
        ImageView image = findViewById(R.id.personal_detail_image);
        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(data.getImageUrl())
                    .centerCrop()
                    .into(image);
        }

        // 文字信息
        TextView title = findViewById(R.id.personal_detail_title);
        TextView creator = findViewById(R.id.personal_detail_creator);
        TextView year = findViewById(R.id.personal_detail_year);
        TextView material = findViewById(R.id.personal_detail_material);
        TextView size = findViewById(R.id.personal_detail_size);
        TextView description = findViewById(R.id.personal_detail_description);

        title.setText(data.getTitle() != null ? data.getTitle() : "未命名");
        creator.setText(data.getCreator() != null && !data.getCreator().isEmpty()
                ? data.getCreator() : "未知");
        year.setText(data.getYear() != null && !data.getYear().isEmpty()
                ? data.getYear() : "未知");
        material.setText(data.getMaterial() != null && !data.getMaterial().isEmpty()
                ? data.getMaterial() : "未知");
        size.setText(data.getSize() != null && !data.getSize().isEmpty()
                ? data.getSize() : "未知");
        description.setText(data.getDescription() != null && !data.getDescription().isEmpty()
                ? data.getDescription() : "暂无简介");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.xoliu.common.R.anim.anim_enter, com.xoliu.common.R.anim.anim_exit);
    }
}
