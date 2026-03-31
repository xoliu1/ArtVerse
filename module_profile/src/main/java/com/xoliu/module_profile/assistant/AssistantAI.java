package com.xoliu.module_profile.assistant;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * AI助手 - DeepSeek API 聊天类
 */
public class AssistantAI {
    private OkHttpClient HTTP_CLIENT;

    // DeepSeek API Key
    private final static String API_KEY = "sk-c6c4e4c10a104cba83a241bccb0e8538";

    // DeepSeek 聊天接口地址
    private final String url = "https://api.deepseek.com/chat/completions";

    private ArrayList<HashMap<String, String>> messages;
    private HashMap<String, Object> requestBody;

    MediaType mediaType;

    public AssistantAI() {
        mediaType = MediaType.parse("application/json");
        this.HTTP_CLIENT = new OkHttpClient().newBuilder().build();
        this.requestBody = new HashMap<>();
        this.messages = new ArrayList<>();
    }

    public void addUserMsg(String content) {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", content);
        messages.add(msg);
    }

    public void addAssistantMsg(String content) {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "assistant");
        msg.put("content", content);
        messages.add(msg);
    }

    /**
     * 发送用户消息并获取AI回复
     */
    public String sendAndGetReply(String userContent) {
        addUserMsg(userContent);
        return chatCall();
    }

    public String chatCall() {
        requestBody.put("model", "deepseek-chat");
        requestBody.put("messages", messages);

        RequestBody body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                final String responseBody = response.body().string();
                Log.d("AssistantAI", "onResponse: " + responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                // 检查 API 是否返回了错误
                if (jsonResponse.has("error")) {
                    String errMsg = jsonResponse.getJSONObject("error").optString("message", "未知错误");
                    Log.e("AssistantAI", "API error: " + errMsg);
                    return "AI接口错误: " + errMsg;
                }
                if (!jsonResponse.has("choices")) {
                    Log.e("AssistantAI", "No choices in response: " + responseBody);
                    return "AI返回异常，请稍后重试";
                }
                // 解析 DeepSeek 响应格式
                String result = jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
                addAssistantMsg(result);
                return result;
            } else {
                return "服务器响应失败";
            }
        } catch (Exception e) {
            Log.e("AssistantAI", "网络请求出错", e);
            return "网络请求出错";
        }
    }
}
