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
import com.alibaba.android.arouter.facade.annotation.Route;

import com.xoliu.module_ai.R;
import com.xoliu.module_ai.databinding.FragmentPoetChatBinding;
import com.xoliu.module_ai.model.bean.ChatMessageEntity;
import com.xoliu.module_ai.model.bean.ChatMsg;
import com.xoliu.module_ai.model.chat.ChatAI;
import com.xoliu.module_ai.model.dao.ChatMessageDao;
import com.xoliu.module_ai.model.db.ChatDatabase;
import com.xoliu.module_ai.view.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;
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

        binding.rvMsgs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(messages, getContext());
        binding.rvMsgs.setAdapter(adapter);

        initData();
        initListener();
    }

    private void initData() {
        // 使用线程池执行耗时操作
        executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            // 先从数据库加载历史聊天记录
            List<ChatMessageEntity> history = chatMessageDao.getAllMessages();
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
                // 没有历史记录，第一次进入，发送初始化消息
                try {
                    showProgressDialogOnUI("加载诗人模型中");
                    String s = ai.addAndCall("现在你是" + poet + "，和我进行沟通");
                    if (isAdded()) {
                        // 保存 AI 的第一条回复到数据库
                        String cleanedMsg = removeBeforeFirstNewLine(s);
                        chatMessageDao.insert(new ChatMessageEntity("assistant", cleanedMsg, System.currentTimeMillis()));
                        getActivity().runOnUiThread(() -> {
                            messages.add(new ChatMsg("assistant", cleanedMsg));
                            adapter.notifyDataSetChanged();
                            dismissProgressDialog();
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initListener() {
        // 清除聊天记录按钮
        binding.btnClearChat.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("清除聊天记录")
                    .setMessage("确定要清除所有聊天记录吗？清除后将重新开始对话。")
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

                // 在后台线程中处理网络请求
                executorService.execute(() -> {
                    // 保存用户消息到数据库
                    chatMessageDao.insert(new ChatMessageEntity("user", msg, System.currentTimeMillis()));

                    try {
                        // 发送消息并等待响应
                        String answer = ai.addAndCall(msg);
                        if (answer != null && !answer.isEmpty()) {
                            // 保存 AI 回复到数据库
                            chatMessageDao.insert(new ChatMessageEntity("assistant", answer, System.currentTimeMillis()));
                            // 收到回复后更新RecyclerView
                            addMessageAndUpdate(new ChatMsg("assistant", answer));
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
        getActivity().runOnUiThread(() -> {
            messages.add(message);
            adapter.notifyItemInserted(messages.size() - 1);
            scrollToBottom(); // 滚动到新消息位置
        });
    }

    // 滚动RecyclerView到底部的方法
    private void scrollToBottom() {
        if (adapter.getItemCount() > 0) {
            binding.rvMsgs.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    /**
     * 清除所有聊天记录：清空数据库 + 界面 + AI上下文，然后重新初始化对话
     */
    private void clearChatHistory() {
        executorService.execute(() -> {
            // 1. 清空数据库
            chatMessageDao.deleteAll();

            if (isAdded()) {
                getActivity().runOnUiThread(() -> {
                    // 2. 清空界面
                    messages.clear();
                    adapter.notifyDataSetChanged();

                    // 3. 重置 AI 上下文（新建实例）
                    ai = new ChatAI();

                    // 4. 重新初始化对话
                    initData();
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