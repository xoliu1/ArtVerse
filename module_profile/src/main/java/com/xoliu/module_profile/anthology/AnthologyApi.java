package com.xoliu.module_profile.anthology;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnthologyApi {
    private static final String TAG = "AnthologyApi";
    private static final String BASE_URL = "http://127.0.0.1:9999/api/anthology";

    public static final int MSG_GET_MY = 301;
    public static final int MSG_GET_ALL = 302;
    public static final int MSG_CREATE = 303;
    public static final int MSG_UPDATE = 304;
    public static final int MSG_DELETE = 305;

    /**
     * 获取我的文集
     */
    public static void getMyAnthology(int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/my?user_id=" + userId)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getMyAnthology: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_MY;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 获取所有文集（诗友圈）
     */
    public static void getAllAnthology(Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/all")
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getAllAnthology: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_ALL;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 创建文集
     */
    public static void createAnthology(int userId, String username, String title, String content, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", String.valueOf(userId))
                        .add("username", username)
                        .add("title", title)
                        .add("content", content)
                        .build();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/create")
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "createAnthology: " + data);
                Message message = Message.obtain();
                message.what = MSG_CREATE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 更新文集
     */
    public static void updateAnthology(int id, int userId, String title, String content, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", String.valueOf(id))
                        .add("user_id", String.valueOf(userId))
                        .add("title", title)
                        .add("content", content)
                        .build();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/update")
                        .put(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "updateAnthology: " + data);
                Message message = Message.obtain();
                message.what = MSG_UPDATE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 删除文集
     */
    public static void deleteAnthology(int id, int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String jsonBody = "{\"id\":" + id + ",\"user_id\":" + userId + "}";
                RequestBody requestBody = RequestBody.create(JSON, jsonBody);
                Request request = new Request.Builder()
                        .url(BASE_URL + "/delete")
                        .delete(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "deleteAnthology: " + data);
                Message message = Message.obtain();
                message.what = MSG_DELETE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
