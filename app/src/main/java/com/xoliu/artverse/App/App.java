package com.xoliu.artverse.App;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import utils.MVUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        
    }
}
