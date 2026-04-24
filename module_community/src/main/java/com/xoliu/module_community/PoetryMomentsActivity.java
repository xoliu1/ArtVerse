package com.xoliu.module_community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xoliu.module_community.Adapter.PoetryAdapter;
import com.xoliu.module_community.Present.mid;
import com.xoliu.module_community.mModel.customer;
import com.xoliu.module_community.mModel.player;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import utils.MVUtil;

@Route(path = "/poetryMoments/main")
public class PoetryMomentsActivity extends AppCompatActivity {

    private static final String TAG = "PoetryMoments";
    private static final String BASE_URL = "http://127.0.0.1:9999/api";

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    List<player> playerList;
    int currentUserId;

    // 记录待处理的关注操作位置
    private int pendingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_moments);
        recyclerView = findViewById(R.id.pervous);

        currentUserId = MVUtil.getInt("user_id", 0);

        mid kol = new mid();
        Handler handlerTwo = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 2) {
                    String fgh = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + fgh);
                    Gson gson = new Gson();
                    customer datelist = gson.fromJson(fgh, customer.class);
                    Log.d(TAG, "handleMessage: " + datelist);

                    playerList = datelist.getPlayerList();
                    if (playerList == null) {
                        playerList = new ArrayList<>();
                    }

                    poetryAdapter = new PoetryAdapter(getApplicationContext(), playerList,
                            (item, position, isCurrentlyFollowed) -> {
                                if (currentUserId == 0) {
                                    Toast.makeText(PoetryMomentsActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (item.getUserId() == currentUserId) {
                                    Toast.makeText(PoetryMomentsActivity.this, "不能关注自己", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                pendingPosition = position;
                                if (isCurrentlyFollowed) {
                                    unfollowUser(currentUserId, item.getUserId());
                                } else {
                                    followUser(currentUserId, item.getUserId());
                                }
                            });
                    recyclerView.setLayoutManager(new LinearLayoutManager(
                            getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(poetryAdapter);

                    // 列表加载完后批量查询关注状态
                    if (currentUserId > 0) {
                        batchCheckFollowStatus();
                    }
                }
            }
        };
        kol.getPeople(handlerTwo);
    }

    /**
     * 关注用户
     */
    private void followUser(int followerID, int followedID) {
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

                JSONObject obj = new JSONObject(data);
                int code = obj.optInt("Code");
                String message = obj.optString("Message", "");

                runOnUiThread(() -> {
                    if (code == 200) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (poetryAdapter != null && pendingPosition >= 0) {
                            poetryAdapter.updateFollowStatus(pendingPosition, true);
                        }
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "followUser error", e);
            }
        }).start();
    }

    /**
     * 取消关注
     */
    private void unfollowUser(int followerID, int followedID) {
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

                JSONObject obj = new JSONObject(data);
                int code = obj.optInt("Code");
                String message = obj.optString("Message", "");

                runOnUiThread(() -> {
                    if (code == 200) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (poetryAdapter != null && pendingPosition >= 0) {
                            poetryAdapter.updateFollowStatus(pendingPosition, false);
                        }
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "unfollowUser error", e);
            }
        }).start();
    }

    /**
     * 批量查询当前用户对诗友圈所有用户的关注状态
     */
    private void batchCheckFollowStatus() {
        if (playerList == null || playerList.isEmpty()) return;

        // 收集所有不重复的 user_id
        List<Integer> targetIds = new ArrayList<>();
        for (player p : playerList) {
            if (p.getUserId() > 0 && p.getUserId() != currentUserId && !targetIds.contains(p.getUserId())) {
                targetIds.add(p.getUserId());
            }
        }
        if (targetIds.isEmpty()) return;

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                // 构建 JSON body: {"follower_id": x, "followed_ids": [a, b, c]}
                StringBuilder sb = new StringBuilder();
                sb.append("{\"follower_id\":").append(currentUserId).append(",\"followed_ids\":[");
                for (int i = 0; i < targetIds.size(); i++) {
                    if (i > 0) sb.append(",");
                    sb.append(targetIds.get(i));
                }
                sb.append("]}");

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, sb.toString());
                Request request = new Request.Builder()
                        .url(BASE_URL + "/follow/batch-check")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "batchCheckFollow: " + data);

                JSONObject obj = new JSONObject(data);
                int code = obj.optInt("Code");
                if (code == 200) {
                    JSONObject dataObj = obj.optJSONObject("data");
                    if (dataObj != null) {
                        runOnUiThread(() -> {
                            for (int i = 0; i < playerList.size(); i++) {
                                player p = playerList.get(i);
                                String key = String.valueOf(p.getUserId());
                                if (dataObj.optBoolean(key, false)) {
                                    p.setFollowed(true);
                                }
                            }
                            if (poetryAdapter != null) {
                                poetryAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "batchCheckFollow error", e);
            }
        }).start();
    }
}