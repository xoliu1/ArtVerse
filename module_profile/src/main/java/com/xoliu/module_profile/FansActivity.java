package com.xoliu.module_profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utils.MVUtil;

public class FansActivity extends AppCompatActivity {

    private static final String TAG = "FansActivity";

    private TabLayout tabLayout;
    private RecyclerView rvFollowList;
    private TextView tvEmpty;
    private TextView tvTitle;

    private List<FanItem> fanList = new ArrayList<>();
    private FansAdapter adapter;
    private int userId;
    private int currentTab = 0; // 0=粉丝, 1=关注

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Log.d(TAG, "Handler received: what=" + msg.what + ", json=" + json);
            switch (msg.what) {
                case FollowApi.MSG_GET_FOLLOWERS:
                case FollowApi.MSG_GET_FOLLOWING:
                    handleListResponse(json);
                    break;
                case FollowApi.MSG_FOLLOW:
                    handleFollowResult(json, true);
                    break;
                case FollowApi.MSG_UNFOLLOW:
                    handleFollowResult(json, false);
                    break;
            }
        }
    };

    // 记录上一次操作的 position
    private int pendingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        userId = MVUtil.getInt("user_id", 0);

        tabLayout = findViewById(R.id.tabLayout);
        rvFollowList = findViewById(R.id.rvFollowList);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvTitle = findViewById(R.id.tvTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        adapter = new FansAdapter(this, fanList, false, (item, position, isCurrentlyFollowed) -> {
            pendingPosition = position;
            if (isCurrentlyFollowed) {
                // 取消关注
                FollowApi.unfollowUser(userId, item.getUserId(), handler);
            } else {
                // 关注 / 回关
                FollowApi.followUser(userId, item.getUserId(), handler);
            }
        });

        rvFollowList.setLayoutManager(new LinearLayoutManager(this));
        rvFollowList.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // 默认加载粉丝列表
        loadData();
    }

    private void loadData() {
        if (userId == 0) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentTab == 0) {
            tvTitle.setText("我的粉丝");
            tvEmpty.setText("暂无粉丝");
            adapter.setFollowingTab(false);
            FollowApi.getMyFollowers(userId, handler);
        } else {
            tvTitle.setText("我的关注");
            tvEmpty.setText("暂无关注");
            adapter.setFollowingTab(true);
            FollowApi.getMyFollowing(userId, handler);
        }
    }

    private void handleListResponse(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray dataArr = obj.optJSONArray("data");

            fanList.clear();

            if (dataArr != null && dataArr.length() > 0) {
                // 收集所有 user_id 用于批量检查关注状态
                for (int i = 0; i < dataArr.length(); i++) {
                    JSONObject item = dataArr.getJSONObject(i);
                    int uid = item.optInt("user_id");
                    String username = item.optString("username", "");
                    String avatarUrl = item.optString("avatar_url", "");

                    // 在粉丝列表中：判断我是否回关了此人
                    // 在关注列表中：默认已关注
                    boolean isFollowed = (currentTab == 1); // 关注列表中全都是已关注的
                    fanList.add(new FanItem(uid, username, avatarUrl, isFollowed));
                }

                // 如果是粉丝列表，需要批量检查我是否关注了这些粉丝
                if (currentTab == 0) {
                    checkFollowStatusForFans();
                }

                tvEmpty.setVisibility(View.GONE);
                rvFollowList.setVisibility(View.VISIBLE);
            } else {
                tvEmpty.setVisibility(View.VISIBLE);
                rvFollowList.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e(TAG, "handleListResponse error", e);
        }
    }

    /**
     * 对粉丝列表中的每个人，检查我是否已经关注了他们
     */
    private void checkFollowStatusForFans() {
        for (int i = 0; i < fanList.size(); i++) {
            final int position = i;
            final FanItem item = fanList.get(i);
            // 简单用 check API 逐个检查（数据量不大时性能ok）
            new Thread(() -> {
                try {
                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("http://127.0.0.1:9999/api/follow/check?follower_id=" + userId + "&followed_id=" + item.getUserId())
                            .get()
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    JSONObject obj = new JSONObject(data);
                    boolean isFollowing = obj.optBoolean("is_following", false);

                    runOnUiThread(() -> {
                        item.setFollowed(isFollowing);
                        adapter.notifyItemChanged(position);
                    });
                } catch (Exception e) {
                    Log.e(TAG, "checkFollow error for user " + item.getUserId(), e);
                }
            }).start();
        }
    }

    private void handleFollowResult(String json, boolean followed) {
        try {
            JSONObject obj = new JSONObject(json);
            int code = obj.optInt("Code");
            String message = obj.optString("Message", "");

            if (code == 200) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                if (pendingPosition >= 0 && pendingPosition < fanList.size()) {
                    adapter.updateItem(pendingPosition, followed);
                }
                // 如果是在关注列表取消关注，刷新列表
                if (!followed && currentTab == 1) {
                    loadData();
                }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "handleFollowResult error", e);
        }
    }
}
