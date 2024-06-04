package com.xoliu.module_community.mModel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class lModel implements mModel{

    @Override
    public void getPoem(Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://1.92.123.214:16666/api/user/poetry")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = date;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Log.d("TAD", "run: " + e);
                }
            }
        }).start();
    }
}
