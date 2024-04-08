package com.xoliu.module_community.Present;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mid {

    public void getPeople(Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.130.118.185:6666/api/user/signature")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    Log.d("TAD", "run: " + date);
                    Message message = Message.obtain();
                    message.what = 2;
                    message.obj = date;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Log.d("TAD", "run: " + e);
                }
            }
        }).start();
    }
}
