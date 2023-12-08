package com.xoliu.module_art;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_art.databinding.FragmentArtMainBinding;

@Route(path = "/art/main")
public class fragment_art_main extends Fragment {



    public fragment_art_main() {

    }


    public static fragment_art_main newInstance(String param1, String param2) {
        fragment_art_main fragment = new fragment_art_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentArtMainBinding binding = FragmentArtMainBinding.inflate(inflater);
        return binding.getRoot();
    }
}