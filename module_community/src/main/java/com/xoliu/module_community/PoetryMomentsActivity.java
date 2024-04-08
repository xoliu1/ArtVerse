package com.xoliu.module_community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xoliu.module_community.Adapter.PoetryAdapter;
import com.xoliu.module_community.Present.mid;
import com.xoliu.module_community.mModel.customer;

@Route(path = "/poetryMoments/main")
public class PoetryMomentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_moments);
        recyclerView = findViewById(R.id.pervous);
        mid kol = new mid();
        Handler handlerTwo = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 2) {
                    String fgh = (String) msg.obj;
                    Log.d("TAD", "handleMessage: " + fgh);
                    Gson gson = new Gson();
                    customer datelist = gson.fromJson(fgh, customer.class);
                    Log.d("10086", "handleMessage: " + datelist);
                    PoetryAdapter poetryAdapter = new PoetryAdapter(getApplicationContext(),datelist.getPlayerList());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(poetryAdapter);
                }
            }
        };
        kol.getPeople(handlerTwo);
    }
}