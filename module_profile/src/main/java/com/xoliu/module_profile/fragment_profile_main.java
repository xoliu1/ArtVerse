package com.xoliu.module_profile;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xoliu.module_profile.anthology.AnthologyApi;
import com.xoliu.module_profile.anthology.AnthologyBean;
import com.xoliu.module_profile.assistant.AssistantChatActivity;
import com.xoliu.module_profile.databinding.FragmentProfileMainBinding;
import utils.GalleryApi;
import db.bean.GalleryBean;
import com.xoliu.module_profile.note.NoteListActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import utils.MVUtil;

@Route(path = "/profile/main")
public class fragment_profile_main extends Fragment {
    private static final String TAG = "ProfileMain";
    private FragmentProfileMainBinding binding;

    // 诗集相关
    private List<AnthologyBean> anthologyList;
    private AnthologyAdapter anthologyAdapter;
    private int userId;
    private String username;

    // 画廊相关
    private List<GalleryBean> galleryList;
    private GalleryAdapter galleryAdapter;
    private byte[] selectedImageData;
    private String selectedImageName;
    private ImageView dialogImagePreview;
    private ActivityResultLauncher<String> imagePickerLauncher;

    public fragment_profile_main() {
    }

    public static fragment_profile_main newInstance() {
        return new fragment_profile_main();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册图片选择器（必须在 onCreate 中注册）
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        onImagePicked(uri);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMainBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView called");
        return binding.getRoot();
    }

    // Handler 处理诗集和画廊网络响应
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
                case GalleryApi.MSG_GET_MY:
                    handleGetMyGallery(json);
                    break;
                case GalleryApi.MSG_CREATE:
                case GalleryApi.MSG_UPDATE:
                case GalleryApi.MSG_DELETE:
                    handleGalleryOperationResult(json);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated called");

        userId = MVUtil.getInt("user_id", 0);
        username = MVUtil.getString("username", "");
        Log.d(TAG, "userId=" + userId + ", username=" + username);

        initData();
        initView();
        initPoemList();
        initGalleryList();
    }

    private void initView() {
        // 设置"助手"点击事件
        binding.profileAssistant.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AssistantChatActivity.class));
        });

        // 设置头像点击事件
        binding.profileUserIcon.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ImagePickerActivity.class));
        });

        // 设置"笔记"点击事件
        binding.profileNote.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), NoteListActivity.class));
        });

        // 设置"发现"点击事件
        binding.profileDiscover.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, "https://sou-yun.cn/");
            intent.putExtra(WebViewActivity.EXTRA_TITLE, "发现");
            startActivity(intent);
        });

        // 设置"周边"点击事件
        binding.profileNearby.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, "https://www.thechinajourney.com/zh_cn/%E6%99%AF%E7%82%B9/");
            intent.putExtra(WebViewActivity.EXTRA_TITLE, "周边");
            startActivity(intent);
        });

        // 设置"粉丝"点击事件
        binding.profileFans.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), FansActivity.class));
        });

        // 退出登录
        binding.btnLogout.setOnClickListener(v -> {
            MVUtil.getInstance().put("Logined", false);
            MVUtil.getInstance().put("user_id", 0);
            MVUtil.getInstance().put("user_email", "");
            MVUtil.getInstance().put("username", "");
            MVUtil.getInstance().put("avatar_url", "");
            ARouter.getInstance().build("/login/main").navigation();
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Tab 切换
        binding.profileTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "Tab selected: position=" + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        showPoemContent();
                        break;
                    case 1:
                        showArtContent();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * 初始化诗集列表（直接在本 Fragment 中管理）
     */
    private void initPoemList() {
        Log.d(TAG, "initPoemList called");

        anthologyList = new ArrayList<>();
        anthologyAdapter = new AnthologyAdapter(anthologyList, new AnthologyAdapter.OnItemActionListener() {
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
        binding.profilePoemRecyclerView.setAdapter(anthologyAdapter);
        binding.profilePoemRecyclerView.setNestedScrollingEnabled(false);

        Log.d(TAG, "RecyclerView adapter set");

        // FAB 新增按钮
        binding.fabAdd.setOnClickListener(v -> showCreateDialog());

        // 默认显示文集 Tab
        showPoemContent();

        // 首次加载数据
        loadPoemData();
    }

    private void showPoemContent() {
        Log.d(TAG, "showPoemContent: showing poem, hiding art");
        binding.poemContentContainer.setVisibility(View.VISIBLE);
        binding.artContentContainer.setVisibility(View.GONE);
    }

    private void showArtContent() {
        Log.d(TAG, "showArtContent: showing art, hiding poem");
        binding.poemContentContainer.setVisibility(View.GONE);
        binding.artContentContainer.setVisibility(View.VISIBLE);
        loadGalleryData();
    }

    /**
     * 加载诗集数据
     */
    private void loadPoemData() {
        Log.d(TAG, "loadPoemData: userId=" + userId + ", username=" + username);
        if (userId == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            showPoemEmpty(true);
            return;
        }
        Log.d(TAG, "loadPoemData: calling AnthologyApi.getMyAnthology");
        AnthologyApi.getMyAnthology(userId, handler);
    }

    private void handleGetMyAnthology(String json) {
        Log.d(TAG, "handleGetMyAnthology: raw json=" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code", jsonObject.optInt("Code", 0));
            Log.d(TAG, "handleGetMyAnthology: code=" + code);
            if (code == 200) {
                org.json.JSONArray dataArray = jsonObject.optJSONArray("data");
                if (dataArray == null) {
                    String dataStr = jsonObject.optString("data", "[]");
                    Log.d(TAG, "handleGetMyAnthology: dataArray is null, trying string: " + dataStr);
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

                anthologyAdapter.notifyDataSetChanged();
                showPoemEmpty(anthologyList.isEmpty());

                Log.d(TAG, "adapter notified, anthologyList.size()=" + anthologyList.size()
                        + ", adapter.getItemCount()=" + anthologyAdapter.getItemCount());
            } else {
                String message = jsonObject.optString("message", jsonObject.optString("Message", "加载失败"));
                Log.w(TAG, "handleGetMyAnthology: code=" + code + ", msg=" + message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                showPoemEmpty(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleGetMyAnthology exception: ", e);
            Toast.makeText(getContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
            showPoemEmpty(true);
        }
    }

    private void showPoemEmpty(boolean empty) {
        if (binding != null && binding.emptyHint != null) {
            binding.emptyHint.setVisibility(empty ? View.VISIBLE : View.GONE);
        }
    }

    private void handleOperationResult(String json) {
        try {
            Log.d(TAG, "handleOperationResult: " + json);
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("Code", jsonObject.optInt("code", 0));
            String message = jsonObject.optString("Message", jsonObject.optString("message", ""));
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (code == 200) {
                loadPoemData(); // 操作成功后刷新列表
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

    private void initData() {
        // 从 MMKV 读取登录时存储的用户名
        String name = MVUtil.getString("username", "");
        if (name != null && !name.isEmpty()) {
            binding.profileUserName.setText(name);
        }
        loadAvatar();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAvatar();
        // 每次页面恢复时也刷新诗集
        if (userId > 0) {
            loadPoemData();
        }
    }

    private void loadAvatar() {
        String avatarUrl = MVUtil.getString("avatar_url", "");
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Glide.with(this)
                    .load(avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.wechat_icon)
                    .error(R.drawable.wechat_icon)
                    .into(binding.profileUserIcon);
        } else {
            binding.profileUserIcon.setImageResource(R.drawable.wechat_icon);
        }
    }

    // ==================== 画廊相关方法 ====================

    /**
     * 初始化画廊列表
     */
    private void initGalleryList() {
        Log.d(TAG, "initGalleryList called");

        galleryList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(galleryList, new GalleryAdapter.OnItemActionListener() {
            @Override
            public void onDelete(GalleryBean item, int position) {
                showGalleryDeleteDialog(item);
            }

            @Override
            public void onUpdate(GalleryBean item, int position) {
                showGalleryEditDialog(item);
            }
        });

        binding.profileArtRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profileArtRecyclerView.setAdapter(galleryAdapter);
        binding.profileArtRecyclerView.setNestedScrollingEnabled(false);

        // FAB 新增画作
        binding.fabAddGallery.setOnClickListener(v -> showGalleryCreateDialog());

        Log.d(TAG, "Gallery RecyclerView adapter set");
    }

    /**
     * 加载画廊数据
     */
    private void loadGalleryData() {
        Log.d(TAG, "loadGalleryData: userId=" + userId);
        if (userId == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            showArtEmpty(true);
            return;
        }
        GalleryApi.getMyGallery(userId, handler);
    }

    private void handleGetMyGallery(String json) {
        Log.d(TAG, "handleGetMyGallery: raw json=" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code", jsonObject.optInt("Code", 0));
            if (code == 200) {
                org.json.JSONArray dataArray = jsonObject.optJSONArray("data");
                if (dataArray == null) {
                    String dataStr = jsonObject.optString("data", "[]");
                    dataArray = new org.json.JSONArray(dataStr);
                }
                String dataStr = dataArray.toString();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<GalleryBean>>() {}.getType();
                List<GalleryBean> list = gson.fromJson(dataStr, listType);

                galleryList.clear();
                if (list != null && !list.isEmpty()) {
                    galleryList.addAll(list);
                    Log.d(TAG, "handleGetMyGallery: parsed " + list.size() + " items");
                }

                galleryAdapter.notifyDataSetChanged();
                showArtEmpty(galleryList.isEmpty());
            } else {
                String message = jsonObject.optString("message", jsonObject.optString("Message", "加载失败"));
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                showArtEmpty(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "handleGetMyGallery exception: ", e);
            Toast.makeText(getContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
            showArtEmpty(true);
        }
    }

    private void showArtEmpty(boolean empty) {
        if (binding != null && binding.artEmptyHint != null) {
            binding.artEmptyHint.setVisibility(empty ? View.VISIBLE : View.GONE);
        }
    }

    private void handleGalleryOperationResult(String json) {
        try {
            Log.d(TAG, "handleGalleryOperationResult: " + json);
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("Code", jsonObject.optInt("code", 0));
            String message = jsonObject.optString("Message", jsonObject.optString("message", ""));
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (code == 200) {
                loadGalleryData(); // 操作成功后刷新
            }
        } catch (Exception e) {
            Log.e(TAG, "handleGalleryOperationResult: ", e);
        }
    }

    /**
     * 新增画作对话框
     */
    private void showGalleryCreateDialog() {
        if (userId == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedImageData = null;
        selectedImageName = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("新增画作");

        ScrollView scrollView = new ScrollView(requireContext());
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 10);

        // 图片选择区域
        dialogImagePreview = new ImageView(requireContext());
        dialogImagePreview.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 400));
        dialogImagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        dialogImagePreview.setBackgroundColor(0xFFF0F0F0);
        dialogImagePreview.setImageResource(android.R.drawable.ic_menu_gallery);
        dialogImagePreview.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        layout.addView(dialogImagePreview);

        android.widget.TextView hint = new android.widget.TextView(requireContext());
        hint.setText("点击上方区域选择图片");
        hint.setGravity(android.view.Gravity.CENTER);
        hint.setTextColor(0xFF999999);
        hint.setPadding(0, 8, 0, 16);
        layout.addView(hint);

        EditText titleInput = new EditText(requireContext());
        titleInput.setHint("画作名称（必填）");
        titleInput.setSingleLine(true);
        layout.addView(titleInput);

        EditText creatorInput = new EditText(requireContext());
        creatorInput.setHint("创作者");
        creatorInput.setSingleLine(true);
        layout.addView(creatorInput);

        EditText yearInput = new EditText(requireContext());
        yearInput.setHint("年代");
        yearInput.setSingleLine(true);
        layout.addView(yearInput);

        EditText materialInput = new EditText(requireContext());
        materialInput.setHint("材质");
        materialInput.setSingleLine(true);
        layout.addView(materialInput);

        EditText sizeInput = new EditText(requireContext());
        sizeInput.setHint("尺寸");
        sizeInput.setSingleLine(true);
        layout.addView(sizeInput);

        EditText descInput = new EditText(requireContext());
        descInput.setHint("简介");
        descInput.setMinLines(3);
        layout.addView(descInput);

        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton("发布", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(getContext(), "请输入画作名称", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedImageData == null) {
                Toast.makeText(getContext(), "请选择画作图片", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "正在上传...", Toast.LENGTH_SHORT).show();
            GalleryApi.createGallery(userId, username, title,
                    selectedImageData, selectedImageName,
                    creatorInput.getText().toString().trim(),
                    yearInput.getText().toString().trim(),
                    materialInput.getText().toString().trim(),
                    sizeInput.getText().toString().trim(),
                    descInput.getText().toString().trim(),
                    handler);
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 编辑画作对话框
     */
    private void showGalleryEditDialog(GalleryBean item) {
        selectedImageData = null;
        selectedImageName = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("编辑画作");

        ScrollView scrollView = new ScrollView(requireContext());
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 10);

        // 当前图片预览
        dialogImagePreview = new ImageView(requireContext());
        dialogImagePreview.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 400));
        dialogImagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        dialogImagePreview.setBackgroundColor(0xFFF0F0F0);
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(this).load(item.getImageUrl()).centerCrop().into(dialogImagePreview);
        }
        dialogImagePreview.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        layout.addView(dialogImagePreview);

        android.widget.TextView hint = new android.widget.TextView(requireContext());
        hint.setText("点击上方图片可更换");
        hint.setGravity(android.view.Gravity.CENTER);
        hint.setTextColor(0xFF999999);
        hint.setPadding(0, 8, 0, 16);
        layout.addView(hint);

        EditText titleInput = new EditText(requireContext());
        titleInput.setHint("画作名称");
        titleInput.setText(item.getTitle());
        titleInput.setSingleLine(true);
        layout.addView(titleInput);

        EditText creatorInput = new EditText(requireContext());
        creatorInput.setHint("创作者");
        creatorInput.setText(item.getCreator());
        creatorInput.setSingleLine(true);
        layout.addView(creatorInput);

        EditText yearInput = new EditText(requireContext());
        yearInput.setHint("年代");
        yearInput.setText(item.getYear());
        yearInput.setSingleLine(true);
        layout.addView(yearInput);

        EditText materialInput = new EditText(requireContext());
        materialInput.setHint("材质");
        materialInput.setText(item.getMaterial());
        materialInput.setSingleLine(true);
        layout.addView(materialInput);

        EditText sizeInput = new EditText(requireContext());
        sizeInput.setHint("尺寸");
        sizeInput.setText(item.getSize());
        sizeInput.setSingleLine(true);
        layout.addView(sizeInput);

        EditText descInput = new EditText(requireContext());
        descInput.setHint("简介");
        descInput.setText(item.getDescription());
        descInput.setMinLines(3);
        layout.addView(descInput);

        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(getContext(), "请输入画作名称", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "正在保存...", Toast.LENGTH_SHORT).show();
            GalleryApi.updateGallery(item.getId(), userId, title,
                    selectedImageData, selectedImageName,
                    creatorInput.getText().toString().trim(),
                    yearInput.getText().toString().trim(),
                    materialInput.getText().toString().trim(),
                    sizeInput.getText().toString().trim(),
                    descInput.getText().toString().trim(),
                    handler);
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 删除画作确认对话框
     */
    private void showGalleryDeleteDialog(GalleryBean item) {
        new AlertDialog.Builder(requireContext())
                .setTitle("删除画作")
                .setMessage("确定要删除「" + item.getTitle() + "」吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    GalleryApi.deleteGallery(item.getId(), userId, handler);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 图片选择回调处理
     */
    private void onImagePicked(Uri uri) {
        try {
            ContentResolver resolver = requireContext().getContentResolver();
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream == null) return;

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            inputStream.close();

            selectedImageData = buffer.toByteArray();
            selectedImageName = getFileNameFromUri(uri);
            if (selectedImageName == null || selectedImageName.isEmpty()) {
                selectedImageName = "gallery_image.jpg";
            }

            // 更新对话框中的预览图
            if (dialogImagePreview != null) {
                Glide.with(this).load(uri).centerCrop().into(dialogImagePreview);
            }

            Log.d(TAG, "Image picked: " + selectedImageName + ", size=" + selectedImageData.length);
        } catch (Exception e) {
            Log.e(TAG, "onImagePicked error", e);
            Toast.makeText(getContext(), "读取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = requireContext().getContentResolver()
                    .query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
