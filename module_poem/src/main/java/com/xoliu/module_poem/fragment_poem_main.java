package com.xoliu.module_poem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/poem/fragment")
public class fragment_poem_main extends Fragment {



    public fragment_poem_main() {
        // Required empty public constructor
    }

    public static fragment_poem_main newInstance() {
        fragment_poem_main fragment = new fragment_poem_main();
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
        return inflater.inflate(R.layout.fragment_poem_main, container, false);
    }
}