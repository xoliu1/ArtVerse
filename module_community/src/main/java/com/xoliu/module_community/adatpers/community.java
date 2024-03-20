package com.xoliu.module_community.adatpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class community extends FragmentStatePagerAdapter {
    List<Fragment>  fragmentList;
    List<String> stringList;

    public community(@NonNull FragmentManager fragmentManager, List<Fragment> fragments, List<String> stringList) {
        super(fragmentManager);
        this.fragmentList = fragments;
        this.stringList = stringList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList == null ? null : stringList.get(position);
    }
}
