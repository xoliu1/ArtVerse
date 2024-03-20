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
import com.google.android.material.tabs.TabLayout;
import com.xoliu.module_profile.databinding.FragmentProfileMainBinding;

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
        //设置点击事件，进入选取图片
        binding.profileUserIcon.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ImagePickerActivity.class));
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
    }
}