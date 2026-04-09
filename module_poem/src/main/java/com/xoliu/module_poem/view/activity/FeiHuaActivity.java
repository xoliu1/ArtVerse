package com.xoliu.module_poem.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_poem.R;
import com.xoliu.module_poem.adapter.FeiHuaMsgAdapter;
import com.xoliu.module_poem.bean.FeiHuaMsg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 飞花令游戏 Activity
 * 玩家与 AI 轮流说出包含指定关键字的古诗词
 *
 * @author ArtVerse
 */
@Route(path = "/poem/feihua")
public class FeiHuaActivity extends AppCompatActivity {

    private static final String TAG = "FeiHuaActivity";

    // ========== 关键字库 ==========
    private static final String[] KEYWORDS = {
            "月", "花", "春", "风", "雪", "酒", "山", "水",
            "云", "雨", "人", "夜", "秋", "天", "红", "绿",
            "江", "柳", "梦", "心", "日", "星", "寒", "鸟"
    };

    // ========== DeepSeek API ==========
    private static final String API_KEY = "sk-c6c4e4c10a104cba83a241bccb0e8538";
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private OkHttpClient httpClient;
    private MediaType mediaType;
    private ExecutorService executor;

    // ========== UI 控件 ==========
    // 设置区
    private LinearLayout setupArea;
    private TextView tvKeyword, btnChangeKeyword;
    private TextView btnModeNormal, btnModeHard;
    private TextView btnStart;

    // 游戏区
    private LinearLayout gameArea;
    private TextView tvGameKeyword, tvRound, tvCountdown, tvAiThinking;
    private View progressBar;
    private RecyclerView rvMessages;
    private LinearLayout inputArea;
    private EditText etInput;
    private TextView btnSend;

    // 结果区
    private LinearLayout resultArea;
    private TextView tvResultTitle, tvResultDetail;
    private TextView btnReplay, btnGoBack;

    // 返回
    private View btnBack;

    // ========== 游戏状态 ==========
    private String currentKeyword;
    private boolean isHardMode = false;
    private int timeLimit = 40; // 秒
    private int currentRound = 0;
    private boolean isPlayerTurn = false;
    private boolean isGameRunning = false;
    private boolean isVerifying = false; // 是否正在验证玩家诗句

    private List<FeiHuaMsg> messageList;
    private FeiHuaMsgAdapter adapter;
    private Set<String> usedPoems; // 已经使用过的诗句（去重）
    private List<String> allPoemsInOrder; // 按顺序记录所有诗句（给 AI 上下文）

    private CountDownTimer countDownTimer;
    private Handler mainHandler;
    private int progressMaxWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feihua);

        // 隐藏 ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mainHandler = new Handler(Looper.getMainLooper());
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        mediaType = MediaType.parse("application/json");
        executor = Executors.newSingleThreadExecutor();

        initViews();
        setupListeners();
        randomKeyword();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);

        // 设置区
        setupArea = findViewById(R.id.setup_area);
        tvKeyword = findViewById(R.id.tv_keyword);
        btnChangeKeyword = findViewById(R.id.btn_change_keyword);
        btnModeNormal = findViewById(R.id.btn_mode_normal);
        btnModeHard = findViewById(R.id.btn_mode_hard);
        btnStart = findViewById(R.id.btn_start);

        // 游戏区
        gameArea = findViewById(R.id.game_area);
        tvGameKeyword = findViewById(R.id.tv_game_keyword);
        tvRound = findViewById(R.id.tv_round);
        tvCountdown = findViewById(R.id.tv_countdown);
        tvAiThinking = findViewById(R.id.tv_ai_thinking);
        progressBar = findViewById(R.id.progress_bar);
        rvMessages = findViewById(R.id.rv_messages);
        inputArea = findViewById(R.id.input_area);
        etInput = findViewById(R.id.et_input);
        btnSend = findViewById(R.id.btn_send);

        // 结果区
        resultArea = findViewById(R.id.result_area);
        tvResultTitle = findViewById(R.id.tv_result_title);
        tvResultDetail = findViewById(R.id.tv_result_detail);
        btnReplay = findViewById(R.id.btn_replay);
        btnGoBack = findViewById(R.id.btn_go_back);

        // RecyclerView 初始化
        messageList = new ArrayList<>();
        adapter = new FeiHuaMsgAdapter(messageList, this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(false);
        rvMessages.setLayoutManager(lm);
        rvMessages.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnChangeKeyword.setOnClickListener(v -> randomKeyword());

        btnModeNormal.setOnClickListener(v -> selectMode(false));
        btnModeHard.setOnClickListener(v -> selectMode(true));

        btnStart.setOnClickListener(v -> startGame());

        btnSend.setOnClickListener(v -> onPlayerSend());

        etInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                onPlayerSend();
                return true;
            }
            return false;
        });

        btnReplay.setOnClickListener(v -> {
            showSetupArea();
            randomKeyword();
        });

        btnGoBack.setOnClickListener(v -> finish());
    }

    // ========== 关键字 ==========
    private void randomKeyword() {
        currentKeyword = KEYWORDS[new Random().nextInt(KEYWORDS.length)];
        tvKeyword.setText(currentKeyword);

        // 加一个缩放动画让切换有感觉
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvKeyword, "scaleX", 0.5f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvKeyword, "scaleY", 0.5f, 1.2f, 1f);
        scaleX.setDuration(400);
        scaleY.setDuration(400);
        scaleX.start();
        scaleY.start();
    }

    // ========== 难度选择 ==========
    private void selectMode(boolean hard) {
        isHardMode = hard;
        timeLimit = hard ? 20 : 40;

        if (hard) {
            btnModeHard.setBackgroundResource(R.drawable.feihua_mode_selected_bg);
            btnModeHard.setTextColor(Color.parseColor("#FFF8DC")); // cornsilk
            btnModeNormal.setBackgroundResource(R.drawable.feihua_mode_normal_bg);
            btnModeNormal.setTextColor(Color.parseColor("#DEB887")); // burlywood
        } else {
            btnModeNormal.setBackgroundResource(R.drawable.feihua_mode_selected_bg);
            btnModeNormal.setTextColor(Color.parseColor("#FFF8DC")); // cornsilk
            btnModeHard.setBackgroundResource(R.drawable.feihua_mode_normal_bg);
            btnModeHard.setTextColor(Color.parseColor("#DEB887")); // burlywood
        }
    }

    // ========== 页面切换 ==========
    private void showSetupArea() {
        setupArea.setVisibility(View.VISIBLE);
        gameArea.setVisibility(View.GONE);
        resultArea.setVisibility(View.GONE);
    }

    private void showGameArea() {
        setupArea.setVisibility(View.GONE);
        gameArea.setVisibility(View.VISIBLE);
        resultArea.setVisibility(View.GONE);
    }

    private void showResultArea() {
        setupArea.setVisibility(View.GONE);
        gameArea.setVisibility(View.GONE);
        resultArea.setVisibility(View.VISIBLE);
    }

    // ========== 游戏开始 ==========
    private void startGame() {
        currentRound = 0;
        isGameRunning = true;
        messageList.clear();
        adapter.notifyDataSetChanged();
        usedPoems = new HashSet<>();
        allPoemsInOrder = new ArrayList<>();

        tvGameKeyword.setText("关键字：" + currentKeyword);
        tvRound.setText("第 1 回合");
        etInput.setHint("请输入含「" + currentKeyword + "」的诗句...");

        showGameArea();

        // 添加系统消息
        addSystemMessage("飞花令开始！关键字为「" + currentKeyword + "」，" +
                (isHardMode ? "困难模式 · 20s" : "正常模式 · 40s"));

        // AI 先手
        aiTurn();
    }

    // ========== AI 回合 ==========
    private void aiTurn() {
        isPlayerTurn = false;
        inputArea.setVisibility(View.INVISIBLE);
        tvAiThinking.setVisibility(View.VISIBLE);
        cancelTimer();

        executor.execute(() -> {
            String aiPoem = callDeepSeekForPoem();
            mainHandler.post(() -> {
                tvAiThinking.setVisibility(View.GONE);

                if (!isGameRunning) return;

                if (aiPoem == null || aiPoem.isEmpty() || !aiPoem.contains(currentKeyword)) {
                    // AI 答不上来，玩家胜利
                    gameOver(true);
                    return;
                }

                // 检查 AI 是否重复
                String cleanPoem = cleanPoem(aiPoem);
                if (usedPoems.contains(cleanPoem)) {
                    // AI 重复了，玩家胜利
                    gameOver(true);
                    return;
                }

                currentRound++;
                usedPoems.add(cleanPoem);
                allPoemsInOrder.add(aiPoem);

                addMessage(new FeiHuaMsg(FeiHuaMsg.TYPE_AI, aiPoem, currentRound));
                tvRound.setText("第 " + currentRound + " 回合");

                // 轮到玩家
                playerTurn();
            });
        });
    }

    // ========== 玩家回合 ==========
    private void playerTurn() {
        isPlayerTurn = true;
        inputArea.setVisibility(View.VISIBLE);
        etInput.setText("");
        etInput.requestFocus();

        startCountdown();
    }

    // ========== 玩家提交 ==========
    private void onPlayerSend() {
        if (!isPlayerTurn || !isGameRunning || isVerifying) return;

        String input = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(input)) return;

        // 隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
        }

        // 基础校验：最少4个字（四言诗，如诗经）
        if (input.length() < 4) {
            addSystemMessage("诗句太短了，请输入完整的诗句~");
            return;
        }

        // 校验是否包含关键字
        if (!input.contains(currentKeyword)) {
            addSystemMessage("诗句中需包含「" + currentKeyword + "」字哦~");
            return;
        }

        // 校验是否重复
        String cleanInput = cleanPoem(input);
        if (usedPoems.contains(cleanInput)) {
            addSystemMessage("这句已经说过了，换一句吧~");
            return;
        }

        // 基础校验通过，开始 AI 验证（倒计时继续走）
        isVerifying = true;
        btnSend.setEnabled(false);
        etInput.setEnabled(false);
        tvAiThinking.setText("验证诗句中...");
        tvAiThinking.setVisibility(View.VISIBLE);

        final String finalInput = input;
        executor.execute(() -> {
            boolean isRealPoem = verifyPoemWithAI(finalInput);
            mainHandler.post(() -> {
                tvAiThinking.setVisibility(View.GONE);
                btnSend.setEnabled(true);
                etInput.setEnabled(true);
                isVerifying = false;

                if (!isGameRunning) return;

                if (!isRealPoem) {
                    // 验证失败，不是真实诗句
                    addSystemMessage("这似乎不是古诗词哦，请输入真实的诗句~");
                    return;
                }

                // 验证通过！
                usedPoems.add(cleanInput);
                allPoemsInOrder.add(finalInput);

                addMessage(new FeiHuaMsg(FeiHuaMsg.TYPE_PLAYER, finalInput, currentRound));

                // 轮到 AI
                aiTurn();
            });
        });
    }

    // ========== 倒计时 ==========
    private void startCountdown() {
        tvCountdown.setText(timeLimit + "s");

        // 等进度条宽度测量好
        progressBar.post(() -> {
            ViewGroup parent = (ViewGroup) progressBar.getParent();
            progressMaxWidth = parent.getWidth();

            ViewGroup.LayoutParams lp = progressBar.getLayoutParams();
            lp.width = progressMaxWidth;
            progressBar.setLayoutParams(lp);

            countDownTimer = new CountDownTimer(timeLimit * 1000L, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (!isGameRunning) {
                        cancel();
                        return;
                    }
                    int secondsLeft = (int) (millisUntilFinished / 1000);
                    tvCountdown.setText(secondsLeft + "s");

                    // 更新进度条宽度
                    float ratio = (float) millisUntilFinished / (timeLimit * 1000f);
                    ViewGroup.LayoutParams lp = progressBar.getLayoutParams();
                    lp.width = (int) (progressMaxWidth * ratio);
                    progressBar.setLayoutParams(lp);

                    // 根据剩余时间变色提醒
                    if (secondsLeft <= 5) {
                        tvCountdown.setTextColor(Color.parseColor("#FF0000")); // red
                    } else if (secondsLeft <= 10) {
                        tvCountdown.setTextColor(Color.parseColor("#FFA500")); // orange
                    } else {
                        tvCountdown.setTextColor(Color.parseColor("#FFDEAD")); // navajowhite
                    }
                }

                @Override
                public void onFinish() {
                    if (isGameRunning) {
                        // 超时，玩家失败
                        gameOver(false);
                    }
                }
            };
            countDownTimer.start();
        });
    }

    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    // ========== 游戏结束 ==========
    private void gameOver(boolean playerWin) {
        isGameRunning = false;
        cancelTimer();

        if (playerWin) {
            addSystemMessage("AI 词穷了，少侠获胜！");
            tvResultTitle.setText("少侠好文采！");
        } else {
            addSystemMessage("时间到，少侠惜败...");
            tvResultTitle.setText("惜败...");
        }

        String modeText = isHardMode ? "困难模式" : "正常模式";
        tvResultDetail.setText("关键字：" + currentKeyword + "\n"
                + modeText + "\n"
                + "共坚持了 " + currentRound + " 个回合");

        // 延迟一下再跳转到结果页，让玩家看到最后的消息
        mainHandler.postDelayed(this::showResultArea, 1500);
    }

    // ========== 消息操作 ==========
    private void addMessage(FeiHuaMsg msg) {
        messageList.add(msg);
        adapter.notifyItemInserted(messageList.size() - 1);
        rvMessages.smoothScrollToPosition(messageList.size() - 1);
    }

    private void addSystemMessage(String text) {
        addMessage(new FeiHuaMsg(FeiHuaMsg.TYPE_SYSTEM, text, currentRound));
    }

    // ========== 工具方法 ==========
    private String cleanPoem(String poem) {
        // 去掉标点符号和空格，用于去重比较
        return poem.replaceAll("[\\u3000-\\u303F\\uFF00-\\uFFEF\\s\\p{Punct}]", "");
    }

    // ========== AI 验证玩家输入是否是真实古诗词 ==========
    private boolean verifyPoemWithAI(String playerInput) {
        try {
            JSONArray messages = new JSONArray();

            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content",
                    "你是一个中国古诗词鉴定专家。用户会给你一句话，你需要判断它是否是真实存在的中国古诗词（包括古诗、词、曲、赋中的原句或名句）。\n" +
                    "判断标准：\n" +
                    "1. 必须是历史上真实存在的古诗词作品中的原句\n" +
                    "2. 现代人编造的、仿写的不算\n" +
                    "3. 只需要是诗句的一部分即可（如一联、一句），不要求完整的一首诗\n" +
                    "4. 允许有轻微的标点差异\n\n" +
                    "你只需要回复\"是\"或\"否\"，不要有任何其他内容。");
            messages.put(systemMsg);

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", "请判断以下是否为真实的古诗词原句：「" + playerInput + "」");
            messages.put(userMsg);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 10);
            requestBody.put("temperature", 0.1); // 低温度，让判断更确定

            RequestBody body = RequestBody.create(mediaType, requestBody.toString());
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .build();

            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "Verify response: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);
                if (jsonResponse.has("error") || !jsonResponse.has("choices")) {
                    // API 出错时宽容处理，让玩家通过
                    Log.e(TAG, "Verify API error, allowing player input");
                    return true;
                }

                String result = jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                Log.d(TAG, "Verify result: " + result);
                // 只有明确回复"否"才算不通过
                return !result.contains("否");
            }
        } catch (Exception e) {
            Log.e(TAG, "Verify call failed", e);
        }
        // 网络异常时宽容处理，让玩家通过
        return true;
    }

    // ========== DeepSeek API 调用 ==========
    private String callDeepSeekForPoem() {
        try {
            JSONArray messages = new JSONArray();

            // System prompt
            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content",
                    "你是一个精通中国古诗词的文人，正在和对方玩飞花令游戏。\n" +
                    "规则：每次只能回复一句包含指定关键字的古诗词原句（五言或七言均可，也可以是完整的一联）。\n" +
                    "要求：\n" +
                    "1. 只回复诗句本身，不要加任何解释、标点以外的内容\n" +
                    "2. 不能重复之前说过的诗句\n" +
                    "3. 必须是真实存在的古诗词名句\n" +
                    "4. 如果实在想不出来，就回复\"认输\"两个字");
            messages.put(systemMsg);

            // 构建上下文
            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("关键字是「").append(currentKeyword).append("」。\n");

            if (!allPoemsInOrder.isEmpty()) {
                contextBuilder.append("之前已经说过的诗句有：\n");
                for (int i = 0; i < allPoemsInOrder.size(); i++) {
                    contextBuilder.append(i + 1).append(". ").append(allPoemsInOrder.get(i)).append("\n");
                }
                contextBuilder.append("\n请说出一句新的包含「").append(currentKeyword).append("」字的古诗词，不要与以上重复。");
            } else {
                contextBuilder.append("请先说出一句包含「").append(currentKeyword).append("」字的古诗词。");
            }

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", contextBuilder.toString());
            messages.put(userMsg);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.8);

            RequestBody body = RequestBody.create(mediaType, requestBody.toString());
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .build();

            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "AI response: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);
                if (jsonResponse.has("error")) {
                    Log.e(TAG, "API error: " + jsonResponse.getJSONObject("error").optString("message"));
                    return null;
                }
                if (!jsonResponse.has("choices")) {
                    return null;
                }

                String result = jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                // 如果 AI 说认输
                if (result.contains("认输")) {
                    return null;
                }

                return result;
            }
        } catch (Exception e) {
            Log.e(TAG, "AI call failed", e);
        }
        return null;
    }

    // ========== 生命周期 ==========
    @Override
    public void onBackPressed() {
        if (isGameRunning) {
            // 游戏进行中，先结束游戏
            isGameRunning = false;
            cancelTimer();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isGameRunning = false;
        cancelTimer();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }
}
