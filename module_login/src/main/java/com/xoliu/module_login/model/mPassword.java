package com.xoliu.module_login.model;



import android.os.Handler;
import android.os.Message;
import android.util.Log;



import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class mPassword implements mModel{


    netLink netLink1 = new netLink();
    @Override
    public void login(String x, String y, Handler handler) {
        netLink1.getdate(x,y,handler);
    }

    @Override
    public void reg(String x, String y, String z, String k,Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",x)
                            .add("password",y)
                            .add("email",z)
                            .add("code",k)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://8.130.118.185:6666/api/user/register")
                            .method("POST",requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    Log.d("TAD", "run: " + date);
                    Message message = Message.obtain();
                    message.what = 10;
                    message.obj = date;
                    handler.sendMessage(message);
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void logat(String c,Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("email",c)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://8.130.118.185:6666/api/user/register-email")
                            .method("POST",requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String date = response.body().string();
                    Log.d("TAD", "run: " + date);
                    Message message = Message.obtain();
                    message.what = 100;
                    message.obj = date;
                    handler.sendMessage(message);
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
