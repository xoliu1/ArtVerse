package com.xoliu.artverse.App;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import bean.CardPic;
import db.AppDatabase;
import utils.MVUtil;

public class App extends Application {

    //数据库
    //public static AppDatabase db;

    //上下文

    public static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        //Arouter相关内容
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        //获取上下文
        context = getApplicationContext();
        //Room数据库初始化，创建本地数据库
        //db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "PoemCards").build();
    }



    public static Context getContext(){
        return context;
    }

    /***
     * 获取Room数据库对象
     *
     * @return db.AppDatabase
     * @author xoliu
     * @create 23-12-6
     **/

//    public static AppDatabase getDb(){
//        return db;
//    }

}
