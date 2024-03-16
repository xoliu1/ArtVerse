package com.xoliu.module_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xoliu.module_login.databinding.ActivityLoginBinding;

@Route(path = "/login/main")
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btnLogin.setOnClickListener(v -> {
            //下面一行代码用于跳转主界面，在登录验证通过后使用
            ARouter.getInstance().build("/main/shell").navigation();
        });

    }
}