package com.xoliu.module_profile;

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

import com.xoliu.module_profile.databinding.FragmentProfileArtLikesBinding;

import java.util.ArrayList;
import java.util.List;

import db.bean.ArtContent;
import db.AppDatabase;
import db.bean.PoemCard;

public class ProfileArtLikesFragment extends Fragment {
    private FragmentProfileArtLikesBinding binding;

    List<ArtContent> artContents;
    private Handler handler = new Handler(Looper.getMainLooper());

    public ProfileArtLikesFragment() {
        // Required empty public constructor
    }

    public static ProfileArtLikesFragment newInstance() {
        ProfileArtLikesFragment fragment = new ProfileArtLikesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentProfileArtLikesBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

    }

    private void initView() {
        binding.profileArtRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profileArtRecyclerView.setAdapter(new ArtContentAdapter(getContext(),artContents));

    }

    private void initData() {
        artContents = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "PoemCards").build();
                List<ArtContent> allArtContents = db.artContentDao().getAllArtContents();
                for (ArtContent artContent : allArtContents) {
                    artContents.add(artContent);
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