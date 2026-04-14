package com.xoliu.module_ai.view.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.xoliu.module_ai.R;
import com.xoliu.module_ai.databinding.FragmentPoetChatBinding;
import com.xoliu.common.utils.FontCache;
import com.xoliu.module_ai.model.bean.ChatMessageEntity;
import com.xoliu.module_ai.model.bean.ChatMsg;
import com.xoliu.module_ai.model.chat.ChatAI;
import com.xoliu.module_ai.model.dao.ChatMessageDao;
import com.xoliu.module_ai.model.db.ChatDatabase;
import com.xoliu.module_ai.view.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Route(path = "/ai/main")
public class PoetChatFragment extends Fragment {

    private FragmentPoetChatBinding binding;

    String poet = "李白";

    List<ChatMsg> messages = new ArrayList<>();

    ChatAI ai = new ChatAI();

    ChatAdapter adapter;

    ExecutorService executorService;
    //加载框
    private ProgressDialog progressDialog;

    // Room 数据库
    private ChatMessageDao chatMessageDao;

    // 标志位：屏蔽 Spinner 首次自动触发
    private boolean isSpinnerInitialized = false;

    // 诗人专属提示词
    private static final Map<String, String> POET_PROMPTS = new HashMap<>();
    static {
        POET_PROMPTS.put("李白", "你是李白，字太白，号青莲居士，唐代伟大的浪漫主义诗人，被后人誉为「诗仙」。请以李白豪放洒脱的口吻和风格与我对话，可以引用你的诗作如《将进酒》《静夜思》《蜀道难》等。");
        POET_PROMPTS.put("刘禹锡", "你是刘禹锡，字梦得，唐代文学家、哲学家，有「诗豪」之称。请以刘禹锡乐观豁达的口吻与我对话，可以引用你的诗作如《陋室铭》《乌衣巷》《秋词》等。");
        POET_PROMPTS.put("白居易", "你是白居易，字乐天，号香山居士，唐代伟大的现实主义诗人。请以白居易通俗平易的口吻与我对话，可以引用你的诗作如《长恨歌》《琵琶行》《赋得古原草送别》等。");
        POET_PROMPTS.put("苏轼", "你是苏轼，字子瞻，号东坡居士，北宋文学家、书法家、美食家。请以苏轼旷达乐观的口吻与我对话，可以引用你的诗词如《水调歌头》《念奴娇·赤壁怀古》《题西林壁》等。");
        POET_PROMPTS.put("陶渊明", "你是陶渊明，字元亮，号五柳先生，东晋田园诗人。请以陶渊明淡泊悠然的口吻与我对话，可以引用你的诗作如《饮酒》《归园田居》《桃花源记》等。");
        POET_PROMPTS.put("陆游", "你是陆游，字务观，号放翁，南宋爱国诗人。请以陆游忧国忧民的口吻与我对话，可以引用你的诗作如《示儿》《游山西村》《书愤》等。");
        POET_PROMPTS.put("韩愈", "你是韩愈，字退之，唐代文学家、思想家，唐宋八大家之首。请以韩愈雄健刚正的口吻与我对话，可以引用你的文章如《师说》《马说》《进学解》等。");
        POET_PROMPTS.put("李商隐", "你是李商隐，字义山，号玉谿生，晚唐著名诗人。请以李商隐含蓄深情的口吻与我对话，可以引用你的诗作如《锦瑟》《无题》《夜雨寄北》等。");
        POET_PROMPTS.put("龚自珍", "你是龚自珍，字璱人，号定庵，清代思想家、诗人。请以龚自珍忧国奋进的口吻与我对话，可以引用你的诗作如《己亥杂诗》等。");
        POET_PROMPTS.put("李清照", "你是李清照，号易安居士，宋代女词人，婉约词派代表。请以李清照细腻婉约的口吻与我对话，可以引用你的词作如《如梦令》《声声慢》《一剪梅》等。");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPoetChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化数据库
        chatMessageDao = ChatDatabase.getInstance(requireContext()).chatMessageDao();

        // 初始化线程池
        executorService = Executors.newCachedThreadPool();

        binding.rvMsgs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(messages, getContext());
        binding.rvMsgs.setAdapter(adapter);

        // 应用自定义字体
        FontCache.apply(binding.tvDescription, requireContext(), FontCache.FONT11);

        loadPoetChat(poet);
        initListener();
    }

    /**
     * 加载指定诗人的聊天记录（从数据库恢复 or 首次初始化）
     */
    private void loadPoetChat(String poetName) {
        executorService.execute(() -> {
            // 从数据库按诗人名称加载历史聊天记录
            List<ChatMessageEntity> history = chatMessageDao.getMessagesByPoet(poetName);
            if (history != null && !history.isEmpty()) {
                // 有历史记录，恢复到界面上
                if (isAdded()) {
                    getActivity().runOnUiThread(() -> {
                        for (ChatMessageEntity entity : history) {
                            messages.add(new ChatMsg(entity.getRole(), entity.getContent()));
                        }
                        adapter.notifyDataSetChanged();
                        scrollToBottom();
                    });
                }
                // 把历史记录也同步到 ChatAI 的 messages 里，保持上下文
                for (ChatMessageEntity entity : history) {
                    ai.addMsg(entity.getContent());
                }
            } else {
                // 没有历史记录，第一次进入该诗人，发送初始化消息
                try {
                    showProgressDialogOnUI("正在召唤「" + poetName + "」...");
                    String prompt = getPoetPrompt(poetName);
                    String s = ai.addAndCall(prompt);
                    if (isAdded()) {
                        // 保存 AI 的第一条回复到数据库（带诗人标记）
                        String cleanedMsg = removeBeforeFirstNewLine(s);
                        ChatMessageEntity entity = new ChatMessageEntity("assistant", cleanedMsg, System.currentTimeMillis());
                        entity.setPoetName(poetName);
                        chatMessageDao.insert(entity);
                        getActivity().runOnUiThread(() -> {
                            messages.add(new ChatMsg("assistant", cleanedMsg));
                            adapter.notifyDataSetChanged();
                            dismissProgressDialog();
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (isAdded()) {
                        getActivity().runOnUiThread(this::dismissProgressDialog);
                    }
                }
            }
        });
    }

    /**
     * 获取诗人的专属提示词
     */
    private String getPoetPrompt(String poetName) {
        String prompt = POET_PROMPTS.get(poetName);
        if (prompt != null) {
            return prompt;
        }
        // 兜底：没有配置专属提示词时使用通用模板
        return "现在你是" + poetName + "，和我进行沟通";
    }

    /**
     * 切换诗人：清空当前界面 → 重置 AI 上下文 → 加载目标诗人聊天记录
     */
    private void switchPoet(String newPoet) {
        poet = newPoet;

        // 1. 清空当前界面
        messages.clear();
        adapter.notifyDataSetChanged();

        // 2. 重置 AI 上下文
        ai.resetContext();

        // 3. 加载新诗人的聊天记录
        loadPoetChat(newPoet);
    }

    private void initListener() {
        // Spinner 诗人切换监听
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isSpinnerInitialized) {
                    // 首次触发（Spinner初始化），跳过
                    isSpinnerInitialized = true;
                    return;
                }
                String selectedPoet = parent.getItemAtPosition(position).toString();
                if (!selectedPoet.equals(poet)) {
                    switchPoet(selectedPoet);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 清除聊天记录按钮（只清当前诗人的）
        binding.btnClearChat.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("清除聊天记录")
                    .setMessage("确定要清除「" + poet + "」的聊天记录吗？清除后将重新开始对话。")
                    .setPositiveButton("确定", (dialog, which) -> clearChatHistory())
                    .setNegativeButton("取消", null)
                    .show();
        });

        binding.btnSend.setOnClickListener(v -> {
            String msg = binding.edText.getText().toString();
            if (!msg.isEmpty()) {
                // 清空输入框
                binding.edText.setText("");

                // 立即将用户消息添加到列表中并更新RecyclerView
                addMessageAndUpdate(new ChatMsg("user", msg));

                // 记住当前诗人（防止异步回调时诗人已切换）
                final String currentPoet = poet;

                // 在后台线程中处理网络请求
                executorService.execute(() -> {
                    // 保存用户消息到数据库（带诗人标记）
                    ChatMessageEntity userEntity = new ChatMessageEntity("user", msg, System.currentTimeMillis());
                    userEntity.setPoetName(currentPoet);
                    chatMessageDao.insert(userEntity);

                    try {
                        // 发送消息并等待响应
                        String answer = ai.addAndCall(msg);
                        if (answer != null && !answer.isEmpty()) {
                            // 保存 AI 回复到数据库（带诗人标记）
                            ChatMessageEntity aiEntity = new ChatMessageEntity("assistant", answer, System.currentTimeMillis());
                            aiEntity.setPoetName(currentPoet);
                            chatMessageDao.insert(aiEntity);

                            // 只有当前诗人没有切换时才更新 UI
                            if (currentPoet.equals(poet)) {
                                addMessageAndUpdate(new ChatMsg("assistant", answer));
                            }
                        }
                    } catch (InterruptedException e) {
                        // 异常处理...
                    }
                });
            }
        });
    }

    // 将新消息添加到messages列表并通知适配器更新的方法
    private void addMessageAndUpdate(ChatMsg message) {
        if (isAdded()) {
            getActivity().runOnUiThread(() -> {
                messages.add(message);
                adapter.notifyItemInserted(messages.size() - 1);
                scrollToBottom(); // 滚动到新消息位置
            });
        }
    }

    // 滚动RecyclerView到底部的方法
    private void scrollToBottom() {
        if (adapter.getItemCount() > 0) {
            binding.rvMsgs.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    /**
     * 清除当前诗人的聊天记录：清空数据库 + 界面 + AI上下文，然后重新初始化对话
     */
    private void clearChatHistory() {
        final String currentPoet = poet;
        executorService.execute(() -> {
            // 1. 清空当前诗人的数据库记录
            chatMessageDao.deleteByPoet(currentPoet);

            if (isAdded()) {
                getActivity().runOnUiThread(() -> {
                    // 2. 清空界面
                    messages.clear();
                    adapter.notifyDataSetChanged();

                    // 3. 重置 AI 上下文
                    ai.resetContext();

                    // 4. 重新初始化当前诗人对话
                    loadPoetChat(currentPoet);
                });
            }
        });
    }

//    public void sendMsg(String s) {
//        int positionStart = messages.size();
//        messages.add(new ChatMsg(times++, s));
//        executorService.execute(() -> {
//            try {
//                String answer = ai.addAndCall(s);
//                if (isAdded()) { // 检查Fragment是否仍然与Activity关联
//                    getActivity().runOnUiThread(() -> {
//                        messages.add(new ChatMsg(times++, answer));
//                        adapter.notifyItemRangeInserted(positionStart, 2);
//                    });
//                }
//            } catch (InterruptedException e) {
//                // 这里可以添加更合适的异常处理，比如更新UI提示用户错误信息
//                e.printStackTrace();
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow(); // 尝试立即关闭线程池
        }
        binding = null; // 释放对binding的引用
    }

    public String removeBeforeFirstNewLine(String input) {
        int index = input.indexOf("\n\n");
        if (index >= 0) {
            return input.substring(index + 2);
        }
        return input;
    }

    // 从子线程安全地显示加载框
    private void showProgressDialogOnUI(String text) {
        if (isAdded()) {
            getActivity().runOnUiThread(() -> showProgressDialog(getContext(), text));
        }
    }

    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);	//设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();


    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }


}