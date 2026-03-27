package com.xoliu.module_profile;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xoliu.module_profile.databinding.FragmentProfilePoemLikesBinding;

import java.util.ArrayList;
import java.util.List;

import db.AppDatabase;
import db.bean.PoemCard;


public class ProfilePoemLikesFragment extends Fragment {
    private List<PoemCard> poemCardList;

    //更新UI
    private Handler handler = new Handler(Looper.getMainLooper());

    public ProfilePoemLikesFragment() {
        // Required empty public constructor
    }


    public static ProfilePoemLikesFragment newInstance() {
        ProfilePoemLikesFragment fragment = new ProfilePoemLikesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentProfilePoemLikesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePoemLikesBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();


    }



    private void initView() {



        binding.profilePoemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profilePoemRecyclerView.setAdapter(new PoemCardAdapter(poemCardList));

    }


    private void initData() {
        poemCardList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "PoemCards").build();
                // 先清理数据库中的空记录
                db.poemCardDao().deleteEmptyPoemCards();
                List<PoemCard> list = db.poemCardDao().getAllPoemCards();

                // 如果数据库为空，插入假数据
                if (list == null || list.isEmpty()) {
                    PoemCard p1 = new PoemCard("大漠孤烟直，长河落日圆。", "王维《使至塞上》");
                    PoemCard p2 = new PoemCard("落霞与孤鹜齐飞，秋水共长天一色。", "王勃《滕王阁序》");
                    PoemCard p3 = new PoemCard("人生若只如初见，何事秋风悲画扇。", "纳兰性德《木兰词》");
                    PoemCard p4 = new PoemCard("醉后不知天在水，满船清梦压星河。", "唐温如《题龙阳县青草湖》");
                    PoemCard p5 = new PoemCard("山有木兮木有枝，心悦君兮君不知。", "《越人歌》");
                    db.poemCardDao().insert(p1);
                    db.poemCardDao().insert(p2);
                    db.poemCardDao().insert(p3);
                    db.poemCardDao().insert(p4);
                    db.poemCardDao().insert(p5);
                    list = db.poemCardDao().getAllPoemCards();
                }

                for (PoemCard card : list) {
                    // 过滤掉空数据（poemContext为空的记录不显示）
                    if (card.getPoemContext() != null && !card.getPoemContext().trim().isEmpty()) {
                        poemCardList.add(card);
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }
        }).start();
    }

}