package com.xoliu.module_community.Present;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class commentNet {
    public void getComment(Handler handler){
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
                    Message message = Message.obtain();
                    message.what = 4;
                    message.obj = date;
                    handler.sendMessage(message);
                    Log.d("TAD", "run:drtfyguhijdsads " + date);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
