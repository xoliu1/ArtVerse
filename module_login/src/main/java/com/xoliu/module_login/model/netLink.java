package com.xoliu.module_login.model;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class netLink {
    public void  getdate(String x, String y, Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",x)
                            .add("password",y)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://8.130.118.185:6666/api/user/login")
                            .method("POST",requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    Log.d("TAD", "run: " + date);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = date;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
