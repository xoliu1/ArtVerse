package com.xoliu.module_art;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ArtContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_content);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ArtCard obj = getIntent().getParcelableExtra("obj", ArtCard.class);
            Log.d("TAG", "onCreate: "+ obj.getClass().toString());
        }
    }
}