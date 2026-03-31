package com.xoliu.module_profile.assistant;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xoliu.module_profile.databinding.ActivityAssistantChatBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AI 助手聊天页面
 * - 接入 DeepSeek AI
 * - Room 本地化存储聊天记录
 * - 支持删除历史聊天记录
 * - AI 初始语："你好，有什么能够帮到你吗"
 */
public class AssistantChatActivity extends AppCompatActivity {

    private ActivityAssistantChatBinding binding;

    private List<AssistantChatMsg> messages = new ArrayList<>();
    private AssistantChatAdapter adapter;

    private AssistantAI ai;
    private AssistantMessageDao messageDao;
    private ExecutorService executorService;

    // AI 初始欢迎语
    private static final String AI_WELCOME_MSG = "你好，有什么能够帮到你吗";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssistantChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化 AI
        ai = new AssistantAI();
        // 初始化数据库
        messageDao = AssistantDatabase.getInstance(this).assistantMessageDao();
        // 初始化线程池
        executorService = Executors.newCachedThreadPool();

        // 初始化 RecyclerView
        binding.assistantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssistantChatAdapter(messages, this);
        binding.assistantRecyclerView.setAdapter(adapter);

        // 加载历史记录
        loadHistory();

        // 设置监听
        initListener();
    }

    /**
     * 从 Room 数据库加载历史聊天记录
     */
    private void loadHistory() {
        executorService.execute(() -> {
            List<AssistantMessageEntity> history = messageDao.getAllMessages();
            if (history != null && !history.isEmpty()) {
                // 有历史记录，恢复到界面上
                runOnUiThread(() -> {
                    for (AssistantMessageEntity entity : history) {
                        messages.add(new AssistantChatMsg(entity.getRole(), entity.getContent()));
                    }
                    adapter.notifyDataSetChanged();
                    scrollToBottom();
                });
                // 将历史记录同步到 AI 上下文中，保持连续对话
                for (AssistantMessageEntity entity : history) {
                    if ("user".equals(entity.getRole())) {
                        ai.addUserMsg(entity.getContent());
                    } else {
                        ai.addAssistantMsg(entity.getContent());
                    }
                }
            } else {
                // 没有历史记录，显示 AI 初始欢迎语
                messageDao.insert(new AssistantMessageEntity("assistant", AI_WELCOME_MSG, System.currentTimeMillis()));
                runOnUiThread(() -> {
                    messages.add(new AssistantChatMsg("assistant", AI_WELCOME_MSG));
                    adapter.notifyItemInserted(0);
                    scrollToBottom();
                });
                // 把欢迎语也加入 AI 上下文
                ai.addAssistantMsg(AI_WELCOME_MSG);
            }
        });
    }

    private void initListener() {
        // 返回按钮
        binding.assistantBack.setOnClickListener(v -> finish());

        // 删除聊天记录按钮
        binding.assistantClearChat.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("清除聊天记录")
                    .setMessage("确定要清除所有聊天记录吗？清除后将重新开始对话。")
                    .setPositiveButton("确定", (dialog, which) -> clearChatHistory())
                    .setNegativeButton("取消", null)
                    .show();
        });

        // 发送按钮
        binding.assistantBtnSend.setOnClickListener(v -> {
            String text = binding.assistantEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                sendMessage(text);
            }
        });
    }

    /**
     * 发送消息
     */
    private void sendMessage(String text) {
        // 清空输入框
        binding.assistantEditText.setText("");

        // 立即显示用户消息
        addMessageToUI(new AssistantChatMsg("user", text));

        // 后台发送并获取回复
        executorService.execute(() -> {
            // 保存用户消息到数据库
            messageDao.insert(new AssistantMessageEntity("user", text, System.currentTimeMillis()));

            // 调用 AI 获取回复
            String reply = ai.sendAndGetReply(text);
            if (reply != null && !reply.isEmpty()) {
                // 保存 AI 回复到数据库
                messageDao.insert(new AssistantMessageEntity("assistant", reply, System.currentTimeMillis()));
                // 更新 UI
                addMessageToUI(new AssistantChatMsg("assistant", reply));
            }
        });
    }

    /**
     * 将消息添加到 UI 列表
     */
    private void addMessageToUI(AssistantChatMsg msg) {
        runOnUiThread(() -> {
            messages.add(msg);
            adapter.notifyItemInserted(messages.size() - 1);
            scrollToBottom();
        });
    }

    /**
     * 滚动到底部
     */
    private void scrollToBottom() {
        if (adapter.getItemCount() > 0) {
            binding.assistantRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    /**
     * 清除所有聊天记录
     */
    private void clearChatHistory() {
        executorService.execute(() -> {
            // 1. 清空数据库
            messageDao.deleteAll();

            runOnUiThread(() -> {
                // 2. 清空界面
                messages.clear();
                adapter.notifyDataSetChanged();

                // 3. 重置 AI 上下文
                ai = new AssistantAI();

                // 4. 重新显示欢迎语
                executorService.execute(() -> {
                    messageDao.insert(new AssistantMessageEntity("assistant", AI_WELCOME_MSG, System.currentTimeMillis()));
                    ai.addAssistantMsg(AI_WELCOME_MSG);
                    runOnUiThread(() -> {
                        messages.add(new AssistantChatMsg("assistant", AI_WELCOME_MSG));
                        adapter.notifyItemInserted(0);
                        scrollToBottom();
                    });
                });
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        binding = null;
    }
}
