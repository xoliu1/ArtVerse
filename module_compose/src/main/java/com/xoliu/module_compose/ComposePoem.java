package com.xoliu.module_compose;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import global.Answer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComposePoem {
    String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie_bot_8k";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    private static final String ACCESS_TOKEN = "24.858343a6678e8c7f4ad36aa1d77b8d3a.2592000.1711351810.282335-47847035";


    private MutableLiveData<String> poemLiveData = new MutableLiveData<>();



    public MutableLiveData<String> getPoemLiveData() {
        return poemLiveData;
    }


    public ComposePoem(String content){
        clear();
        msg.put("role", "user");
        msg.put("content", content);
        messages.add(msg);
        getPoem();
    }
    public void clear(){
        msg.clear();
        messages.clear();
    }

    HashMap<String, String> msg = new HashMap<>();



    ArrayList<HashMap<String, String>> messages = new ArrayList<>();


    HashMap<String, Object> requestBody = new HashMap<>();


    public void getPoem(){
        requestBody.put("messages", messages);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());
        Request request = new Request.Builder()
                .url(url + "?access_token=" + ACCESS_TOKEN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                Answer answer = new Gson().fromJson(responseBody, Answer.class);

                poemLiveData.postValue(answer.getResult());

            }
        });
    }





}
