package com.xoliu.module_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoliu.module_main.databinding.ActivityMainShellBinding;

import java.util.ArrayList;

@Route(path = "/main/shell")
public class main_shell extends AppCompatActivity {

    private ActivityMainShellBinding binding;

    ArrayList<Fragment> fragmentList; //存放各组件Fragment的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化ViewBinding
        binding = ActivityMainShellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //初始化EventBus
        // EventBus.getDefault().register(this);

        initClick();


    }

    private void initClick() {
        //定义底部导航栏点击事件，返回值是是否响应
        binding.btmnvView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 根据选中的子项进行相应的操作
                int id = item.getItemId();
                if(id == R.id.navigation_item_poem) {
                    // 诗句
                    changeMainUI((Fragment) ARouter.getInstance().build("/poem/fragment").navigation());
                    return true;
                }else if(id == R.id.navigation_item_art) {
                    // 艺术作品
                    return true;
                }else if(id == R.id.navigation_item_community) {
                    // 社区
                    return true;
                }else if(id == R.id.navigation_item_profile) {
                    // 个人资料
                    return true;
                }else{
                    return false;
                }
            }
        });
    }



    /***
     * 切换主要展示的模块
     * @param fragment：要切换的模块
     * @return void
     * @author xoliu
     * @create 23-11-19
     **/
    private void changeMainUI(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        // EventBus.getDefault().unregister(this);
    }
}