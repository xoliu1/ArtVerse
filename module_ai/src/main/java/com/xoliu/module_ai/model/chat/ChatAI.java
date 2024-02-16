package com.xoliu.module_ai.model.chat;

import android.util.Log;

import com.google.gson.Gson;
import global.Answer;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * 获取文心一言的对话类
 *
 * @return
 * @author xoliu
 * @create 24-2-16
 **/

public class ChatAI {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    private final static String ACCESS_TOKEN = "24.ee4669072486b643b7131fdce0005b5d.2592000.1708670169.282335-47847035";

    private final String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie_bot_8k";
    private ArrayList<HashMap<String, String>> messages;

    private HashMap<String, Object> requestBody;

    Gson gson = new Gson();

    MediaType mediaType;
    RequestBody body;

    Request request;

    public ChatAI() {
        mediaType = MediaType.parse("application/json");
        this.requestBody = new HashMap<>();
        this.messages = new ArrayList<>();
    }

    public String addAndCall(String content) throws InterruptedException {
        addMsg(content);
        return chatCall();
    }
    int  times = 1;

    public void addMsg(String content){
        HashMap<String, String> msg = new HashMap<>();
        if (times++ % 2 == 1){
            msg.put("role", "user");
        } else {
            msg.put("role", "assistant");
        }
        msg.put("content", content);

        messages.add(msg);
    }

    String a;
    public String  chatCall() throws InterruptedException {
        requestBody = new HashMap<>();
        requestBody.put("messages", messages);

        Log.d("123",requestBody.toString());

        body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());
        request = new Request.Builder()
                .url(url + "?access_token=" + ACCESS_TOKEN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        final CountDownLatch latch = new CountDownLatch(1);
        final String[] answerStr = {null};

        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                // 在这里处理响应数据
                try {
                    Answer answer = gson.fromJson(responseBody, Answer.class);

                    answerStr[0] = answer.getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        });

        latch.await();
        if (answerStr[0] != null){
            addMsg(answerStr[0]);
        }
        return answerStr[0] == null ? "服务器响应失败" : answerStr[0];
    }
}
