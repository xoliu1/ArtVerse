package com.xoliu.module_art;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xoliu.module_art.databinding.FragmentArtCardBinding;

import java.util.Random;


public class ArtCardFragment extends Fragment {

    private FragmentArtCardBinding binding;



    public static ArtCardFragment newInstance() {
        ArtCardFragment fragment = new ArtCardFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_art_card, container, false);
        binding = FragmentArtCardBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}