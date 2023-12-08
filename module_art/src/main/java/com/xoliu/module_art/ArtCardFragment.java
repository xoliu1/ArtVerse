package com.xoliu.module_art;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class ArtCardFragment extends Fragment {
    private static final String INDEX_KEY = "index_key";


    int idx = new Random().nextInt(10);
    public static ArtCardFragment newInstance(int index) {
        ArtCardFragment fragment = new ArtCardFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_art_card, container, false);
        TextView artText = (TextView) v.findViewById(R.id.artText);
//        final Bundle bundle = getArguments();
//        if (bundle != null) {
            artText.setText( idx + "");
//        }
        artText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击了" + idx + "", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}