package com.xoliu.module_profile;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FollowApi {
    private static final String TAG = "FollowApi";
    private static final String BASE_URL = "http://10.0.2.2:9999/api";

    public static final int MSG_FOLLOW = 401;
    public static final int MSG_UNFOLLOW = 402;
    public static final int MSG_GET_FOLLOWERS = 403;
    public static final int MSG_GET_FOLLOWING = 404;
    public static final int MSG_CHECK_FOLLOW = 405;

    /**
     * 关注用户
     */
    public static void followUser(int followerID, int followedID, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("follower_id", String.valueOf(followerID))
                        .add("followed_id", String.valueOf(followedID))
                        .build();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "followUser: " + data);
                Message message = Message.obtain();
                message.what = MSG_FOLLOW;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "followUser error", e);
            }
        }).start();
    }

    /**
     * 取消关注
     */
    public static void unfollowUser(int followerID, int followedID, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String jsonBody = "{\"follower_id\":" + followerID + ",\"followed_id\":" + followedID + "}";
                RequestBody body = RequestBody.create(JSON, jsonBody);
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow")
                        .delete(body)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "unfollowUser: " + data);
                Message message = Message.obtain();
                message.what = MSG_UNFOLLOW;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "unfollowUser error", e);
            }
        }).start();
    }

    /**
     * 获取我的粉丝列表
     */
    public static void getMyFollowers(int userID, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow/followers?user_id=" + userID)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getMyFollowers: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_FOLLOWERS;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "getMyFollowers error", e);
            }
        }).start();
    }

    /**
     * 获取我关注的人
     */
    public static void getMyFollowing(int userID, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow/following?user_id=" + userID)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getMyFollowing: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_FOLLOWING;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "getMyFollowing error", e);
            }
        }).start();
    }

    /**
     * 检查是否关注
     */
    public static void checkFollow(int followerID, int followedID, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow/check?follower_id=" + followerID + "&followed_id=" + followedID)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "checkFollow: " + data);
                Message message = Message.obtain();
                message.what = MSG_CHECK_FOLLOW;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "checkFollow error", e);
            }
        }).start();
    }
}
