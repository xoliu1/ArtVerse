package com.xoliu.module_music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xoliu.module_music.databinding.ActivityCdactivityBinding;

public class CDActivity extends AppCompatActivity {

    private ActivityCdactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCdactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();

        initData();

    }

    private void initData() {
    }

    private void initView() {

    }
}