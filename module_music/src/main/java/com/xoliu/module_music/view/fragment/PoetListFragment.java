package com.xoliu.module_music.view.fragment;

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
import com.xoliu.module_music.view.adapter.XPoemBAdapter;
import com.xoliu.module_music.viewmodel.PoetryViewModel;

import java.util.ArrayList;

import global.XPoemB;


public class PoetListFragment extends Fragment {

    private String url;
    PoetryViewModel viewModel;

    public PoetListFragment(String url) {
        this.url = url;
    }

    XPoemBAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        XPoemB xPoemB = new XPoemB();
        xPoemB.setCode(1);
        XPoemB.Data data = new XPoemB.Data();
        ArrayList<XPoemB.Data> datas = new ArrayList<>();
        datas.add(data);
        xPoemB.setData(datas);

        adapter = new XPoemBAdapter(xPoemB);

        init();

        RecyclerView recy = view.findViewById(R.id.recycleView);
        recy.setAdapter(adapter);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(PoetryViewModel.class);
        viewModel.poetListLiveData.observe(getViewLifecycleOwner(), poetryList -> {
            // 更新RecyclerView的Adapter
            Log.d("TAG", "init: " + "更新RecyclerView的Adapter");
            adapter.addPoems(poetryList);
        });
        viewModel.fetchPoetList(url);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poet_list, container, false);
    }
}