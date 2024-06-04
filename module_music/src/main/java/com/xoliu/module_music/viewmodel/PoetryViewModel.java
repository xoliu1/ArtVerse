package com.xoliu.module_music.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import global.XPoemA;
import global.XPoemB;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PoetryViewModel extends ViewModel {

    public MutableLiveData<List<XPoemA>> poetryListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<XPoemB.Data>> poetListLiveData = new MutableLiveData<>();


    public LiveData<List<XPoemA>> getPoetryList() {
        return poetryListLiveData;
    }

    public void fetchPoetryList(String url) {
        final CountDownLatch latch = new CountDownLatch(4);
        List<XPoemA> poems = Collections.synchronizedList(new ArrayList<>());


        for (int i = 0; i < 4; i++) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    latch.countDown();
                    Log.d("TAG", "onFailure: " + e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        XPoemA poetry = new Gson().fromJson(responseData, XPoemA.class);
                        poems.add(poetry);
                        Log.d("TAG", "onSuccess: " + poetry);
                    }
                    latch.countDown();
                }
            });
        }

        new Thread(() -> {
            try {
                latch.await();
                poetryListLiveData.postValue(poems);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void fetchPoetList(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    XPoemB poetry = new Gson().fromJson(responseData, XPoemB.class);
                    Log.d("TAG", "onSuccess: " + poetry);
                    poetListLiveData.postValue(poetry.getData());
                }

            }
        });
    }


}
