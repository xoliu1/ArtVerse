package com.xoliu.module_community.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.xoliu.module_community.mModel.Topic;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopicViewModel extends ViewModel {

    private Gson gson = new Gson();
    // 将LiveData声明为成员变量，避免重复请求
    private MutableLiveData<Topic> topicLiveData;

    public LiveData<Topic> getTopicList(int id) {
        if (topicLiveData == null) {
            topicLiveData = new MutableLiveData<>();
            loadData(id);
        }
        return topicLiveData;
    }

    // 将网络请求逻辑移至单独的方法中
    private void loadData(int id) {
        id = id % 3;
        id++;
        String url = "http://1.92.123.214:16666/api/user/topic?page=" + id;
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();
                    Topic topic = gson.fromJson(jsonResponse, Topic.class);
                    topicLiveData.postValue(topic);
                    Log.d("TAG", "onResponse: " + topic.getData().toString());
                }
            }
        });

    }
    public void loadNewTopic(int id) {
        loadData(id);
    }
}

