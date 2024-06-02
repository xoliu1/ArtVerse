package com.xoliu.module_community;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xoliu.module_community.Adapter.FRAdapter;
import com.xoliu.module_community.Present.parenteral;
import com.xoliu.module_community.ViewModel.TopicAdapter;
import com.xoliu.module_community.ViewModel.TopicViewModel;
import com.xoliu.module_community.databinding.ActivityCommunityBinding;
import com.xoliu.module_community.mModel.Topic;
import com.xoliu.module_community.mModel.date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



@Route(path = "/community/main")
public class CommunityActivity extends AppCompatActivity  {

    private ActivityCommunityBinding binding;
    private RecyclerView recyclerView;
    date datelist;

    private TopicViewModel viewModel;
    TopicAdapter adapter;
    private MutableLiveData<Topic> topicM = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        parenteral present = new parenteral();
        recyclerView = binding.master;
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1) {
                    String fgh = (String) msg.obj;
                    Log.d("TAD", "handleMessage: " + fgh);
                    Gson gson = new Gson();
                    datelist = gson.fromJson(fgh, date.class);
                    Log.d("10086", "handleMessage: " + datelist);
                    FRAdapter frAdapter = new FRAdapter(datelist.getBase(), getApplicationContext(),getSupportFragmentManager());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(frAdapter);
                }
            }
        };
        present.gethodel(handler);

        viewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        binding.topics.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopicAdapter(this, new Topic());
        binding.topics.setAdapter(adapter);
        viewModel.getTopicList(1).observe(this, topic -> updateData(topic));

        binding.change.setOnClickListener(v -> {
            viewModel.getTopicList(2);
        });
    }

    private void updateData(Topic topic) {
        topicM.postValue(topic);
        binding.topics.setAdapter(new TopicAdapter(this, topicM.getValue()));
    }


}