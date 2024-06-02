package com.xoliu.module_music.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xoliu.module_music.databinding.ActivityFenLeiBinding;
import com.xoliu.module_music.view.adapter.PoetryPagerAdapter;
import com.xoliu.module_music.view.fragment.PoetryListFragment;
@Route(path="/fenlei/main")
public class FenLeiActivity extends AppCompatActivity {


    private ActivityFenLeiBinding binding;
    private PoetryPagerAdapter poetryPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFenLeiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }


    private void init() {
        poetryPagerAdapter = new PoetryPagerAdapter(this);
        // 添加Fragment到Adapter
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/all"), "全部");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/shuqing"), "抒情");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/shuqing/aiguo"), "爱国");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/shuqing/libie"), "离别");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/shuqing/huaigu"), "怀古");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/shuqing/sixiang"), "思乡");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/siji"), "四季");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/rensheng"), "人生");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/jieri"), "节日");
        poetryPagerAdapter.addFragment(new PoetryListFragment("https://v1.jinrishici.com/zhiwu"), "植物");

        // ...更多分类

        binding.viewpagerPoetry.setAdapter(poetryPagerAdapter);
        new TabLayoutMediator(binding.tabsPoetry, binding.viewpagerPoetry,
                (tab, position) -> tab.setText(poetryPagerAdapter.getPageTitle(position))
        ).attach();
    }

}