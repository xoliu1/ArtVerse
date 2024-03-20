package com.xoliu.module_community.model;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public interface Model {
    public void listen(TabLayout tab, ViewPager m);

    public void jia(ViewPager m, FragmentManager supportFragmentManager);
}
