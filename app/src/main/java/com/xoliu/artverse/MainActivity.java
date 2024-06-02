package com.xoliu.artverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.mmkv.MMKV;

import utils.MVUtil;


@Route(path = "/my/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MMKV.initialize(this);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (!MVUtil.getInstance().getBoolean("Logined", false)){
//            //跳转到登录界面
            ARouter.getInstance().build("/login/main").navigation();
//        }else{
//            ARouter.getInstance().build("/main/shell").navigation();
//        }

        finish();

    }
}