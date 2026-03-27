package com.xoliu.module_profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.xoliu.module_profile.assistant.AssistantChatActivity;
import com.xoliu.module_profile.databinding.FragmentProfileMainBinding;
import com.xoliu.module_profile.note.NoteListActivity;

import utils.MVUtil;

@Route(path = "/profile/main")
public class fragment_profile_main extends Fragment {
    private FragmentProfileMainBinding binding;

    public fragment_profile_main() {
    }

    public static fragment_profile_main newInstance() {
        fragment_profile_main fragment = new fragment_profile_main();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentProfileMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }




    private void initView() {
        //设置"助手"点击事件，跳转到AI助手聊天页面
        binding.profileAssistant.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AssistantChatActivity.class));
        });

        //设置点击事件，进入选取图片
        binding.profileUserIcon.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ImagePickerActivity.class));
        });

        //设置"笔记"点击事件，跳转到笔记列表页
        binding.profileNote.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), NoteListActivity.class));
        });

        //设置"发现"点击事件，跳转到百度热搜（App 内 WebView）
        binding.profileDiscover.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, "https://sou-yun.cn/");
            intent.putExtra(WebViewActivity.EXTRA_TITLE, "发现");
            startActivity(intent);
        });

        //设置"周边"点击事件，跳转到携程景点页面（App 内 WebView）
        binding.profileNearby.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, "https://www.thechinajourney.com/zh_cn/%E6%99%AF%E7%82%B9/");
            intent.putExtra(WebViewActivity.EXTRA_TITLE, "周边");
            startActivity(intent);
        });

        // 退出登录
        binding.btnLogout.setOnClickListener(v -> {
            // 清除登录状态
            MVUtil.getInstance().put("Logined", false);
            MVUtil.getInstance().put("user_id", 0);
            MVUtil.getInstance().put("user_email", "");
            MVUtil.getInstance().put("username", "");
            MVUtil.getInstance().put("avatar_url", "");
            // 跳转到登录页面
            ARouter.getInstance().build("/login/main").navigation();
            // 关闭当前 Activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        showPoemTab();
        binding.profileTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showPoemTab();
                        break;
                    case 1:
                        showArtTab();
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


    private void showPoemTab() {
        // 创建Tab1的Fragment实例
        ProfilePoemLikesFragment fragment = ProfilePoemLikesFragment.newInstance();
        // 使用FragmentManager和FragmentTransaction切换页面
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.profile_tab_container, fragment);
        fragmentTransaction.commit();
    }
    private void showArtTab() {
        // 创建Tab1的Fragment实例
        ProfileArtLikesFragment fragment = ProfileArtLikesFragment.newInstance();
        // 使用FragmentManager和FragmentTransaction切换页面
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.profile_tab_container, fragment);
        fragmentTransaction.commit();
    }


    private void initData() {
        // 从 MMKV 读取登录时存储的用户名，动态显示
        String username = MVUtil.getString("username", "");
        if (username != null && !username.isEmpty()) {
            binding.profileUserName.setText(username);
        }
        // 加载头像
        loadAvatar();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次页面恢复时重新加载头像（从 ImagePickerActivity 返回时能及时刷新）
        loadAvatar();
    }

    private void loadAvatar() {
        String avatarUrl = MVUtil.getString("avatar_url", "");
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            // 后端返回的是网络 URL，用 Glide 加载
            Glide.with(this)
                    .load(avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.wechat_icon)
                    .error(R.drawable.wechat_icon)
                    .into(binding.profileUserIcon);
        } else {
            // 头像为空或 null，显示默认头像
            binding.profileUserIcon.setImageResource(R.drawable.wechat_icon);
        }
    }
}