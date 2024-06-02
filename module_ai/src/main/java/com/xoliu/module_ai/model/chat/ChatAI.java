package com.xoliu.module_ai.model.chat;

import android.util.Log;

import com.google.gson.Gson;
import global.Answer;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Constant;

/***
 * 获取文心一言的对话类
 *
 * @return
 * @author xoliu
 * @create 24-2-16
 **/

public class ChatAI {
    private  OkHttpClient HTTP_CLIENT;

    private final static String ACCESS_TOKEN = Constant.token;

    private final String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-8k-1222";
    private ArrayList<HashMap<String, String>> messages;

    private HashMap<String, Object> requestBody;

    Gson gson = new Gson();

    MediaType mediaType;
    RequestBody body;

    Request request;

    public ChatAI() {
        mediaType = MediaType.parse("application/json");
        this.HTTP_CLIENT = new OkHttpClient().newBuilder().build();
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

    public String chatCall() throws InterruptedException {
        requestBody.put("messages", messages);

        RequestBody body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());
        Request request = new Request.Builder()
                .url(url + "?access_token=" + ACCESS_TOKEN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute(); // 同步请求改为使用execute()
            if (response.isSuccessful() && response.body() != null) {
                final String responseBody = response.body().string();
                Answer answer = gson.fromJson(responseBody, Answer.class);
                addMsg(answer.getResult()); // 假设Answer类有getResult()方法获取答案
                return answer.getResult();
            } else {
                // 处理错误情况，如网络错误或服务器返回错误
                return "服务器响应失败";
            }
        } catch (IOException e) {
            // 处理网络请求异常
            return "网络请求出错";
        }
    }
}
