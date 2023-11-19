package com.xoliu.module_main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_main.databinding.ActivityMainFrameBinding;

@Route(path = "/main/frame")
public class main_frame extends AppCompatActivity {

    private ActivityMainFrameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化ViewBinding
        binding = ActivityMainFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}