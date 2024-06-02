package com.xoliu.module_community.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.xoliu.module_community.mModel.Topic;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopicViewModel extends ViewModel {

    private Gson gson = new Gson();
    //LiveData<Topic> TopicList = new MutableLiveData<>();


    public MutableLiveData<Topic> getTopicList(int id) {
        MutableLiveData<Topic> data = new MutableLiveData<>();
        String url = "http://10.0.2.2:6666/api/user/topic?page=" + id;
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
                    data.postValue(topic);
                    Log.d("TAG", "onResponse: " + topic.getData().toString());
                }
            }
        });
        return data;
    }


}
