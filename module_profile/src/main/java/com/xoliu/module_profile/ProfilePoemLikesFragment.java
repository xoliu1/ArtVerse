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
                List<PoemCard> list = db.poemCardDao().getAllPoemCards();
                for (PoemCard card : list) {
                    poemCardList.add(card);
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