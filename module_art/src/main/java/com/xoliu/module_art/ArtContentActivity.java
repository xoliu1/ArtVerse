package com.xoliu.module_art;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.xoliu.module_art.databinding.ActivityArtContentBinding;

import java.util.List;

import db.AppDatabase;
import db.bean.ArtContent;
import db.bean.PoemCard;
import db.dao.ArtContentDao;

/***
 * 点击卡片进入的卡片详情界面
 * @author xoliu
 * @create 23-12-11
 **/

public class ArtContentActivity extends AppCompatActivity {

    private ActivityArtContentBinding binding;

    ArtContent artContent = null;
    boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initView();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private void initView() {
        //设置数据
        binding.artContentImg.setImageResource(artContent.getArtContentImg());
        binding.artContentName.setText(artContent.getName());
        binding.artContentAuthor.setText(artContent.getCreator());
        binding.artContentYear.setText(artContent.getYear());
        binding.artContentMaterial.setText(artContent.getMaterial());
        binding.artContentSize.setText(artContent.getSize());
        binding.artContentInfo.setText(artContent.getContent());
        ViewCompat.setTransitionName(binding.artContentImg, "name");

        //设置返回
        binding.btnClose.setOnClickListener(v -> {
            finish();
        });


        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "PoemCards").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "PoemCards").build();
                int check = db.artContentDao().checkNameExists(artContent.getName());
                if(check != 0){
                    Log.d("TAG1", "run:");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.artContentLike.setImageResource(R.drawable.art_liked);
                            isClicked = true;
                        }
                    });
                }
            }
        }).start();



        //设置收藏
        binding.artContentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    //点击了喜欢
                    binding.artContentLike.setImageResource(R.drawable.art_liked);
                    isClicked = true;
                    //塞进数据库
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "PoemCards").build();
                            db.artContentDao().insert(artContent);
                        }
                    }).start();
                } else {
                    //取消喜欢
                    binding.artContentLike.setImageResource(R.drawable.art_like);
                    isClicked = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "PoemCards").build();
                            db.artContentDao().deletePaintingsByArtistName(artContent.getName());
                        }
                    }).start();
                }
            }
        });




    }

    private void initData() {
        //初始化信息
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            artContent = getIntent().getParcelableExtra("theArtCardContentInfo", ArtContent.class);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.xoliu.common.R.anim.anim_enter, com.xoliu.common.R.anim.anim_exit);
    }
}