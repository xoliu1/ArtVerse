package com.xoliu.module_community.presenter;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xoliu.module_community.model.mModel;

public class mPresenter{
    mModel model;
    public mPresenter() {
        model = new mModel();
    }
    public void createUp(FragmentManager supportFragmentManager, ViewPager mViewPager, TabLayout mTabLayout) {
        model.jia(mViewPager,supportFragmentManager);
        model.listen(mTabLayout,mViewPager);
    }
}
