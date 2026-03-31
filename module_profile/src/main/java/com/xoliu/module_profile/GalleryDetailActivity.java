package com.xoliu.module_profile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.xoliu.module_profile.gallery.GalleryBean;

public class GalleryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        GalleryBean data = (GalleryBean) getIntent().getSerializableExtra("gallery_data");
        if (data == null) {
            finish();
            return;
        }

        // 返回按钮
        findViewById(R.id.gallery_detail_back).setOnClickListener(v -> finish());

        // 大图
        ImageView image = findViewById(R.id.gallery_detail_image);
        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(data.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.wechat_icon)
                    .error(R.drawable.wechat_icon)
                    .into(image);
        }

        // 文字信息
        TextView title = findViewById(R.id.gallery_detail_title);
        TextView creator = findViewById(R.id.gallery_detail_creator);
        TextView year = findViewById(R.id.gallery_detail_year);
        TextView material = findViewById(R.id.gallery_detail_material);
        TextView size = findViewById(R.id.gallery_detail_size);
        TextView description = findViewById(R.id.gallery_detail_description);

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
}
