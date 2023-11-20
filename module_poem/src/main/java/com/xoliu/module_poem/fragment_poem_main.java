package com.xoliu.module_poem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_poem.viewpager.CardAdapter;
import com.xoliu.module_poem.viewpager.fragment_viewpager_item;

import java.util.ArrayList;
import java.util.List;


/***
 * 诗句界面的主界面
 * @author xoliu
 * @create 23-11-20
 **/

@Route(path = "/poem/fragment")
public class fragment_poem_main extends Fragment {

    //ViewPager的集合
    private List<fragment_viewpager_item> fragmentList;


    private ViewPager2 viewPager2;



    public fragment_poem_main() {
        // Required empty public constructor
    }

    public static fragment_poem_main newInstance() {
        fragment_poem_main fragment = new fragment_poem_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /***
     * 初始化ViewPager2
     *
     * @return void
     * @author xoliu
     * @create 23-11-20
     **/
    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new fragment_viewpager_item("我叫王旭钊，爱吃积碳稿","作者：王旭昭",11,3,749));
        fragmentList.add(new fragment_viewpager_item("我叫王旭钊，爱吃积碳稿","作者：王旭昭",11,3,749));
        fragmentList.add(new fragment_viewpager_item("我叫王旭钊，爱吃积碳稿","作者：王旭昭",11,3,749));
        fragmentList.add(new fragment_viewpager_item("我叫王旭钊，爱吃积碳稿","作者：王旭昭",11,3,749));

        viewPager2.setAdapter(new CardAdapter(this,fragmentList));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewpager);
        initViewPager();
    }
}