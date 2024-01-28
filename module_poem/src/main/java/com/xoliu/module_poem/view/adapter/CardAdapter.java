package com.xoliu.module_poem.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xoliu.module_poem.view.fragment.fragment_viewpager_item;

import java.util.List;

/***
 * ViewPager2的适配器
 * @author xoliu
 * @create 23-11-20
 **/

public class CardAdapter extends FragmentStateAdapter {

    private List<fragment_viewpager_item> list;
    public CardAdapter(Fragment fragment, List<fragment_viewpager_item> list){
        super(fragment);
        this.list = list;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    
}
