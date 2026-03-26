package com.xoliu.module_ai.model.chat;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

/*
 * ============== 原百度文心一言代码（已注释） ==============
 *
 * private final static String ACCESS_TOKEN = "24.c49cd01711ca87181af2036b438493e1.2592000.1715826132.282335-47847035";
 * private final String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-8k-1222";
 *
 * // chatCall() 中的请求构建：
 * // requestBody.put("messages", messages);
 * // RequestBody body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());
 * // Request request = new Request.Builder()
 * //         .url(url + "?access_token=" + ACCESS_TOKEN)
 * //         .post(body)
 * //         .addHeader("Content-Type", "application/json")
 * //         .addHeader("Accept", "application/json")
 * //         .build();
 *
 * // 响应解析：
 * // Answer answer = gson.fromJson(responseBody, Answer.class);
 * // addMsg(answer.getResult());
 * // return answer.getResult();
 *
 * ==================================================
 */

public class ChatAI {
    private OkHttpClient HTTP_CLIENT;

    // DeepSeek API Key
    private final static String API_KEY = "sk-c6c4e4c10a104cba83a241bccb0e8538";

    // DeepSeek 聊天接口地址
    private final String url = "https://api.deepseek.com/chat/completions";

    private ArrayList<HashMap<String, String>> messages;
    private HashMap<String, Object> requestBody;

    MediaType mediaType;

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

    int times = 1;

    public void addMsg(String content) {
        HashMap<String, String> msg = new HashMap<>();
        if (times++ % 2 == 1) {
            msg.put("role", "user");
        } else {
            msg.put("role", "assistant");
        }
        msg.put("content", content);
        messages.add(msg);
    }

    public String chatCall() throws InterruptedException {
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
                Log.d("ChatAI", "onResponse: " + responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                // 先检查 API 是否返回了错误
                if (jsonResponse.has("error")) {
                    String errMsg = jsonResponse.getJSONObject("error").optString("message", "未知错误");
                    Log.e("ChatAI", "API error: " + errMsg);
                    return "AI接口错误: " + errMsg;
                }
                if (!jsonResponse.has("choices")) {
                    Log.e("ChatAI", "No choices in response: " + responseBody);
                    return "AI返回异常，请稍后重试";
                }
                // 解析 DeepSeek 响应格式：choices[0].message.content
                String result = jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
                addMsg(result);
                return result;
            } else {
                return "服务器响应失败";
            }
        } catch (Exception e) {
            return "网络请求出错";
        }
    }
}
