package com.xoliu.module_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xoliu.module_main.databinding.ActivityAimBinding;

public class AimActivity extends AppCompatActivity {
    Fragment fragment;
    String fragmentType;
    private ActivityAimBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAimBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentType = getIntent().getStringExtra("fragmentType");

        initView();
    }

    private void initView() {


        // 根据传入的参数选择要加载的碎片
        if ("poem".equals(fragmentType)) {
            fragment = (Fragment) ARouter.getInstance().build("/poem/fragment").navigation();
        } else if ("music".equals(fragmentType)) {
            fragment = (Fragment) ARouter.getInstance().build("/poem/fragment").navigation();
        } else if ("art".equals(fragmentType)) {
            fragment = (Fragment) ARouter.getInstance().build("/art/main").navigation();
        } else if ("community".equals(fragmentType)) {
            fragment = (Fragment) ARouter.getInstance().build("/poem/fragment").navigation();
        } else if ("profile".equals(fragmentType)) {
            fragment = (Fragment) ARouter.getInstance().build("/profile/main").navigation();
        }

// 将碎片添加到容器中
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }



}