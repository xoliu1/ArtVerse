package com.xoliu.module_profile.note;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoteApi {
    private static final String TAG = "NoteApi";
    private static final String BASE_URL = "http://10.0.2.2:9999/api/note";

    public static final int MSG_GET_NOTES = 201;
    public static final int MSG_CREATE_NOTE = 202;
    public static final int MSG_UPDATE_NOTE = 203;
    public static final int MSG_DELETE_NOTE = 204;

    /**
     * 获取用户的所有笔记
     */
    public static void getNotes(int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/list?user_id=" + userId)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getNotes: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_NOTES;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 创建笔记
     */
    public static void createNote(int userId, String userEmail, String title, String content, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", String.valueOf(userId))
                        .add("user_email", userEmail)
                        .add("title", title)
                        .add("content", content)
                        .build();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/create")
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "createNote: " + data);
                Message message = Message.obtain();
                message.what = MSG_CREATE_NOTE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 更新笔记
     */
    public static void updateNote(int noteId, int userId, String title, String content, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", String.valueOf(noteId))
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
                Log.d(TAG, "updateNote: " + data);
                Message message = Message.obtain();
                message.what = MSG_UPDATE_NOTE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 删除笔记
     */
    public static void deleteNote(int noteId, int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                // 使用 JSON body，因为 Gin 对 DELETE 请求的 form body 不会自动解析
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String jsonBody = "{\"id\":" + noteId + ",\"user_id\":" + userId + "}";
                RequestBody requestBody = RequestBody.create(JSON, jsonBody);
                Request request = new Request.Builder()
                        .url(BASE_URL + "/delete")
                        .delete(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "deleteNote: " + data);
                Message message = Message.obtain();
                message.what = MSG_DELETE_NOTE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
