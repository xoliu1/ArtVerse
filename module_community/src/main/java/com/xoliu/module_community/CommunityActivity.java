package com.xoliu.module_community;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_community.databinding.ActivityCommunityBinding;

@Route(path = "/community/main")
public class CommunityActivity extends AppCompatActivity {

    private ActivityCommunityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}