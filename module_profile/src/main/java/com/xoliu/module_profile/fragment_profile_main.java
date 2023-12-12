package com.xoliu.module_profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.xoliu.module_profile.databinding.FragmentProfileMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.AppDatabase;
import db.bean.PoemCard;

@Route(path = "/profile/main")
public class fragment_profile_main extends Fragment {
    private FragmentProfileMainBinding binding;

    public fragment_profile_main() {
    }

    public static fragment_profile_main newInstance() {
        fragment_profile_main fragment = new fragment_profile_main();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentProfileMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }




    private void initView() {
        binding.profileUserIcon.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ImagePickerActivity.class));
        });

        List<PoemCard> poemCardList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "PoemCards").build();
                List<PoemCard> list = db.poemCardDao().getAllPoemCards();
                for (PoemCard card : list) {
                    poemCardList.add(card);
                }

            }
        }).start();
        binding.profileRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profileRecyclerView.setAdapter(new PoemCardAdapter(poemCardList));

    }



    private void initData() {
    }
}