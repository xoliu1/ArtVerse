package com.xoliu.module_ai.view.fragment;

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
import com.xoliu.module_ai.model.bean.ChatMsg;
import com.xoliu.module_ai.model.chat.ChatAI;
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
    int times = 1;

    ChatAdapter adapter;

    ExecutorService executorService;
    //加载框
    private ProgressDialog progressDialog;


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

        initData();
        initListener();

        binding.rvMsgs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(messages, getContext());
        binding.rvMsgs.setAdapter(adapter);
        showProgressDialog(getContext(), "加载诗人模型中");
    }

    private void initData() {
        // 使用线程池执行耗时操作
        executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                String s = ai.addAndCall("现在你是" + poet + "，和我进行沟通");
                if (isAdded()) { // 检查Fragment是否仍然与Activity关联
                    getActivity().runOnUiThread(() -> {
                        messages.add(new ChatMsg(times, removeBeforeFirstNewLine(s)));
                        adapter.notifyDataSetChanged();
                        dismissProgressDialog();
                        //关闭加载框

                    });
                }
            } catch (InterruptedException e) {
                // 这里可以添加更合适的异常处理，比如更新UI提示用户错误信息
                e.printStackTrace();
            }
        });
    }

    private void initListener() {
        binding.btnSend.setOnClickListener(v -> {
            String msg = binding.edText.getText().toString();
            if (!msg.isEmpty()) {
                // 清空输入框
                binding.edText.setText("");

                // 立即将用户消息添加到列表中并更新RecyclerView
                addMessageAndUpdate(new ChatMsg(times++, msg));

                // 在后台线程中处理网络请求
                executorService.execute(() -> {
                    try {
                        // 发送消息并等待响应
                        String answer = ai.addAndCall(msg);
                        if (answer != null && !answer.isEmpty()) {
                            // 收到回复后更新RecyclerView
                            addMessageAndUpdate(new ChatMsg(times++, answer));
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