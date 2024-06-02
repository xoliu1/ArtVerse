package com.xoliu.module_music.view.fragment;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_music.R;
import com.xoliu.module_music.view.adapter.XPoemAdapter;
import com.xoliu.module_music.viewmodel.PoetryViewModel;

import java.util.ArrayList;


public class PoetryListFragment extends Fragment {
    private String url;
    PoetryViewModel viewModel;
    public PoetryListFragment(String url) {
        this.url = url;
    }
    XPoemAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new XPoemAdapter(new ArrayList<>());
        try {
            init();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RecyclerView recy = view.findViewById(R.id.recycleView);
        recy.setAdapter(adapter);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void init() throws InterruptedException {
        viewModel = new ViewModelProvider(this).get(PoetryViewModel.class);
        viewModel.poetryListLiveData.observe(getViewLifecycleOwner(), poetryList -> {
            // 更新RecyclerView的Adapter
            Log.d("TAG", "init: " + "更新RecyclerView的Adapter" );
            adapter.addPoems(poetryList);

        });
        viewModel.fetchPoetryList(url);
        new Thread(() -> {
            try {
                sleep(8000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            viewModel.fetchPoetryList(url);
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poetry_list, container, false);
    }
}