package com.xoliu.module_compose;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
 * ============== 原百度文心一言代码（已注释） ==============
 *
 * private static final String URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-8k-1222";
 * private static final String ACCESS_TOKEN = "24.c49cd01711ca87181af2036b438493e1.2592000.1715826132.282335-47847035";
 *
 * // fetchPoemAsync() 中的请求构建：
 * // requestBody.put("messages", messages);
 * // RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, new JSONObject(requestBody).toString());
 * // Request request = new Request.Builder()
 * //         .url(URL + "?access_token=" + ACCESS_TOKEN)
 * //         .post(body)
 * //         .addHeader("Content-Type", "application/json")
 * //         .addHeader("Accept", "application/json")
 * //         .build();
 *
 * // 响应解析：
 * // Answer answer = new Gson().fromJson(responseBodyString, Answer.class);
 * // poemLiveData.postValue(answer.getResult());
 *
 * ==================================================
 */

public class ComposePoem {
    // DeepSeek 聊天接口地址
    private static final String URL = "https://api.deepseek.com/chat/completions";
    // DeepSeek API Key
    private static final String API_KEY = "sk-c6c4e4c10a104cba83a241bccb0e8538";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    private final MutableLiveData<String> poemLiveData = new MutableLiveData<>();
    private final ArrayList<HashMap<String, String>> messages = new ArrayList<>();
    private final HashMap<String, Object> requestBody = new HashMap<>();

    public ComposePoem() {

    }

    public MutableLiveData<String> getPoemLiveData() {
        return poemLiveData;
    }

    public void requestPoem(String content) {
        clear();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", content);
        messages.add(msg);
        fetchPoemAsync();
    }

    private void clear() {
        messages.clear();
    }

    private void fetchPoemAsync() {
        requestBody.put("model", "deepseek-chat");
        requestBody.put("messages", messages);

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, new JSONObject(requestBody).toString());
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("ComposePoem", "Error fetching poem", e);
                poemLiveData.postValue("Error fetching poem: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (responseBody != null) {
                        String responseBodyString = responseBody.string();
                        Log.d("Compose", "onResponse: " + responseBodyString);
                        JSONObject json = new JSONObject(responseBodyString);
                        // 先检查 API 是否返回了错误
                        if (json.has("error")) {
                            String errMsg = json.getJSONObject("error").optString("message", "未知错误");
                            Log.e("Compose", "API error: " + errMsg);
                            poemLiveData.postValue("AI接口错误: " + errMsg);
                            return;
                        }
                        if (!json.has("choices")) {
                            Log.e("Compose", "No choices in response: " + responseBodyString);
                            poemLiveData.postValue("AI返回异常，请稍后重试");
                            return;
                        }
                        // 解析 DeepSeek 响应格式：choices[0].message.content
                        String result = json.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        poemLiveData.postValue(result);
                    } else {
                        Log.d("Compose", "responseBody == null");
                        poemLiveData.postValue("Error: Response body is null");
                    }
                } catch (Exception e) {
                    poemLiveData.postValue("Error processing response: " + e.getMessage());
                }
            }
        });
    }
}