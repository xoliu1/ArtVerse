package com.xoliu.module_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoliu.module_main.databinding.ActivityMainShellBinding;

import java.util.ArrayList;

@Route(path = "/main/shell")
public class main_shell extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainShellBinding binding;
    ArrayList<Fragment> fragmentList; //存放各组件Fragment的集合

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化ViewBinding
        binding = ActivityMainShellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //初始化EventBus
        // EventBus.getDefault().register(this);



        initClick();


        initAnim();

    }

    private void initAnim() {
        videoView = binding.bgVideoView;


        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.back_video1; // 替换为视频资源路径
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // 设置循环播放
        videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));

        // 开始播放视频
        videoView.start();
    }

    private void initPrePare(){
        fragmentList = new ArrayList<>();
        fragmentList.add((Fragment) ARouter.getInstance().build("/poem/fragment").navigation());
        fragmentList.add((Fragment) ARouter.getInstance().build("/art/main").navigation());
        fragmentList.add((Fragment) ARouter.getInstance().build("/profile/main").navigation());
        //为什么有的用碎片，因为能出动画。
    }

    private void initClick() {
        binding.imgAI.setOnClickListener(this);
        binding.imgPoem.setOnClickListener(this);
        binding.imgMusic.setOnClickListener(this);
        binding.imgCommunity.setOnClickListener(this);
        binding.imgProfile.setOnClickListener(this);
        binding.imgComposeA.setOnClickListener(this);
        binding.imgComposeB.setOnClickListener(this);
        binding.btnPoetsPYQ.setOnClickListener(this);
        binding.imgArt.setOnClickListener(this);
    }





    @Override
    protected void onResume() {
        super.onResume();
        // 检查视频是否停止播放，如果是，则重新开始播放
        if (videoView != null && !videoView.isPlaying()) {
            videoView.start();
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        // EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent1 = new Intent(this, AimActivity.class);

        // 获取点击的坐标
        int centerX = (v.getLeft() + v.getRight()) / 2;
        int centerY = (v.getTop() + v.getBottom()) / 2;
        // 创建 ActivityOptionsCompat 对象并设置动画
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(v, centerX, centerY, 0, 0);
        boolean temp = true;

        if(id == R.id.imgPoem) {
            // 诗句
            intent1.putExtra("fragmentType", "poem");
        }else if(id == R.id.imgAI) {
            // AI
            intent1.putExtra("fragmentType", "ai");
        }else if(id == R.id.imgArt) {
            // Art
            intent1.putExtra("fragmentType", "art");
        }else if (id == R.id.imgProfile){
            // 个人资料
            intent1.putExtra("fragmentType", "profile");
        } else{
            temp = false;
        }





        if (temp){
            // 启动目标活动
            ActivityCompat.startActivity(this, intent1, options.toBundle());
        }else{
            if(id == R.id.imgMusic) {
                //音乐
                ARouter.getInstance().build("/music/main").navigation();
            }else if(id == R.id.imgCommunity) {
                // 社区
                ARouter.getInstance().build("/community/main").navigation();
            }else if (id == R.id.img_compose_a){
                // 藏头诗
                ARouter.getInstance().build("/compose/acrostic").navigation();
            }else if (id == R.id.img_compose_b){
                // 关键词生成诗
                ARouter.getInstance().build("/compose/poems").navigation();
            }else{

            }
        }


    }

}