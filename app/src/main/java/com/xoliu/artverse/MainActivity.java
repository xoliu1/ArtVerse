package com.xoliu.artverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


@Route(path = "/my/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //跳转到登录界面
        ARouter.getInstance().build("/login/main").navigation();

    }
}