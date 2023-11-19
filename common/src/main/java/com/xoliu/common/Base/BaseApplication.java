package com.xoliu.common.Base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;


public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication application;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //声明Activity管理
        context = getApplicationContext();
        application = this;
        if (true) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);



    }


    //内容提供器
    public static Context getContext() {
        return context;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}

