package com.xoliu.module_community.model;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xoliu.module_community.adatpers.community;
import com.xoliu.module_community.framents.disCover;
import com.xoliu.module_community.framents.poem;
import com.xoliu.module_community.framents.question;

import java.util.ArrayList;
import java.util.List;

public class mModel implements Model{

    private List<Fragment> fragments;

    private List<String> stringList;
    @Override
    public void listen(TabLayout mTabLayout, ViewPager mViewPager) {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("发现")){
                    mViewPager.setCurrentItem(0,false);
                } else if (tab.getText().equals("诗文榜")) {
                    mViewPager.setCurrentItem(1,false);
                } else{
                    mViewPager.setCurrentItem(2,false);
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

    @Override
    public void jia(ViewPager mViewPager, FragmentManager supportFragmentManager) {
        fragments = new ArrayList<>();
        stringList = new ArrayList<>();
        fragments.add(new disCover());
        fragments.add(new poem());
        fragments.add(new question());
        stringList.add("发现");
        stringList.add("诗文榜");
        stringList.add("诗文争霸");
        community community1 = new community(supportFragmentManager,fragments,stringList);
        mViewPager.setAdapter(community1);
    }
}
