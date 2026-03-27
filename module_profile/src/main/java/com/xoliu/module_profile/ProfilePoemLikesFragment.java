package com.xoliu.module_profile;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xoliu.module_profile.anthology.AnthologyApi;
import com.xoliu.module_profile.anthology.AnthologyBean;
import com.xoliu.module_profile.databinding.FragmentProfilePoemLikesBinding;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import utils.MVUtil;


public class ProfilePoemLikesFragment extends Fragment {
    private static final String TAG = "ProfilePoemLikes";
    private List<AnthologyBean> anthologyList;
    private AnthologyAdapter adapter;
    private int userId;
    private String username;

    private FragmentProfilePoemLikesBinding binding;

    public ProfilePoemLikesFragment() {
    }

    public static ProfilePoemLikesFragment newInstance() {
        return new ProfilePoemLikesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePoemLikesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Log.d(TAG, "Handler received msg.what=" + msg.what + ", json=" + json);
            switch (msg.what) {
                case AnthologyApi.MSG_GET_MY:
                    handleGetMyAnthology(json);
                    break;
                case AnthologyApi.MSG_CREATE:
                case AnthologyApi.MSG_UPDATE:
                case AnthologyApi.MSG_DELETE:
                    handleOperationResult(json);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId = MVUtil.getInt("user_id", 0);
        username = MVUtil.getString("username", "");

        Log.d(TAG, "onViewCreated: userId=" + userId + ", username=" + username);

        anthologyList = new ArrayList<>();
        adapter = new AnthologyAdapter(anthologyList, new AnthologyAdapter.OnItemActionListener() {
            @Override
            public void onDelete(AnthologyBean item, int position) {
                showDeleteDialog(item, position);
            }

            @Override
            public void onUpdate(AnthologyBean item, int position) {
                showUpdateDialog(item, position);
            }
        });

        binding.profilePoemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profilePoemRecyclerView.setAdapter(adapter);
        // 确保 RecyclerView 不拦截 NestedScrollView 的滚动
        binding.profilePoemRecyclerView.setNestedScrollingEnabled(false);

        Log.d(TAG, "onViewCreated: RecyclerView adapter set, adapter itemCount=" + adapter.getItemCount());

        binding.swipeRefresh.setOnRefreshListener(this::loadData);

        binding.fabAdd.setOnClickListener(v -> showCreateDialog());

        // 首次加载数据
        loadData();
    }

    private void loadData() {
        Log.d(TAG, "loadData: userId=" + userId + ", username=" + username);
        if (userId == 0) {
            if (binding.swipeRefresh != null) {
                binding.swipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            showEmpty(true);
            return;
        }
        Log.d(TAG, "loadData: calling AnthologyApi.getMyAnthology with userId=" + userId);
        AnthologyApi.getMyAnthology(userId, handler);
    }

    private void handleGetMyAnthology(String json) {
        Log.d(TAG, "handleGetMyAnthology: raw json=" + json);
        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.setRefreshing(false);
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code", jsonObject.optInt("Code", 0));
            Log.d(TAG, "handleGetMyAnthology: code=" + code);
            if (code == 200) {
                org.json.JSONArray dataArray = jsonObject.optJSONArray("data");
                if (dataArray == null) {
                    // 可能 data 是字符串格式
                    String dataStr = jsonObject.optString("data", "[]");
                    Log.d(TAG, "handleGetMyAnthology: dataArray is null, trying dataStr=" + dataStr);
                    dataArray = new org.json.JSONArray(dataStr);
                }
                String dataStr = dataArray.toString();
                Log.d(TAG, "handleGetMyAnthology: dataStr=" + dataStr);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<AnthologyBean>>() {}.getType();
                List<AnthologyBean> list = gson.fromJson(dataStr, listType);

                anthologyList.clear();
                if (list != null && !list.isEmpty()) {
                    anthologyList.addAll(list);
                    Log.d(TAG, "handleGetMyAnthology: parsed " + list.size() + " items");
                    for (int i = 0; i < list.size(); i++) {
                        AnthologyBean b = list.get(i);
                        Log.d(TAG, "  item[" + i + "] id=" + b.getId() + " title=" + b.getTitle() + " content=" + b.getContent());
                    }
                } else {
                    Log.d(TAG, "handleGetMyAnthology: list is null or empty");
                }

                adapter.notifyDataSetChanged();
                showEmpty(anthologyList.isEmpty());

                Log.d(TAG, "handleGetMyAnthology: adapter notified, anthologyList.size()=" + anthologyList.size()
                        + ", adapter.getItemCount()=" + adapter.getItemCount());
            } else {
                Log.w(TAG, "handleGetMyAnthology: code=" + code + ", not 200");
                String message = jsonObject.optString("message", jsonObject.optString("Message", "加载失败"));
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                showEmpty(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleGetMyAnthology exception: ", e);
            Toast.makeText(getContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
            showEmpty(true);
        }
    }

    private void showEmpty(boolean empty) {
        if (binding.emptyHint != null) {
            binding.emptyHint.setVisibility(empty ? View.VISIBLE : View.GONE);
        }
    }

    private void handleOperationResult(String json) {
        try {
            Log.d(TAG, "handleOperationResult raw: " + json);
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("Code", jsonObject.optInt("code", 0));
            String message = jsonObject.optString("Message", jsonObject.optString("message", ""));
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (code == 200) {
                loadData(); // 操作成功后刷新列表
            } else {
                Log.w(TAG, "handleOperationResult failed, code=" + code);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleOperationResult: ", e);
        }
    }

    private void showCreateDialog() {
        if (userId == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("新增文集");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 10);

        EditText titleInput = new EditText(requireContext());
        titleInput.setHint("标题/出处（如：王维《使至塞上》）");
        titleInput.setSingleLine(true);
        layout.addView(titleInput);

        EditText contentInput = new EditText(requireContext());
        contentInput.setHint("诗句正文");
        contentInput.setMinLines(3);
        layout.addView(contentInput);

        builder.setView(layout);

        builder.setPositiveButton("发布", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(getContext(), "请输入诗句内容", Toast.LENGTH_SHORT).show();
                return;
            }
            AnthologyApi.createAnthology(userId, username, title, content, handler);
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showUpdateDialog(AnthologyBean item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("编辑文集");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 10);

        EditText titleInput = new EditText(requireContext());
        titleInput.setHint("标题/出处");
        titleInput.setText(item.getTitle());
        titleInput.setSingleLine(true);
        layout.addView(titleInput);

        EditText contentInput = new EditText(requireContext());
        contentInput.setHint("诗句正文");
        contentInput.setText(item.getContent());
        contentInput.setMinLines(3);
        layout.addView(contentInput);

        builder.setView(layout);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(getContext(), "请输入诗句内容", Toast.LENGTH_SHORT).show();
                return;
            }
            AnthologyApi.updateAnthology(item.getId(), userId, title, content, handler);
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showDeleteDialog(AnthologyBean item, int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("删除文集")
                .setMessage("确定要删除「" + item.getContent() + "」吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    AnthologyApi.deleteAnthology(item.getId(), userId, handler);
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
