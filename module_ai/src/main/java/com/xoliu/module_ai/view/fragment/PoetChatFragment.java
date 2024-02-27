package com.xoliu.module_ai.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        try {
            initData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        initListener();

        binding.rvMsgs.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(messages, getContext());
        binding.rvMsgs.setAdapter(adapter);

    }

    private void initData() throws InterruptedException {
//        messages.add(new ChatMsg(1, "“风调雨顺扬帆启，诗词之美在此地。”开启APP，如同踏进一个满载诗词韵味的仙境。您将邂逅李白、杜甫、李清照、辛弃疾、苏轼等诸多古代文人，他们在莲花阁、桃花源、断桥残雪等场景中，怡然自得地吟诵诗词。"));

        // 使用线程池执行耗时操作
        executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                String s = ai.addAndCall("现在你是" + poet + "，和我进行沟通");
                getActivity().runOnUiThread(() -> {
                    messages.add(new ChatMsg(times, removeBeforeFirstNewLine(s)));
                    adapter.notifyDataSetChanged();
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void initListener() {
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //更改诗人
                poet = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSend.setOnClickListener(v -> {
            String msg = binding.edText.getText().toString();
            binding.edText.setText("");
            executorService.execute(() -> {
                try {
                    sendMsg(msg);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public void sendMsg(String s) throws InterruptedException {
        int positionStart = messages.size();
        messages.add(new ChatMsg(times++, s));
        String answer = ai.addAndCall(s);
        messages.add(new ChatMsg(times++, answer));
        getActivity().runOnUiThread(() -> {
            adapter.notifyItemRangeInserted(positionStart, 2);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public String removeBeforeFirstNewLine(String input) {
        int index = input.indexOf("\n\n");
        if (index >= 0) {
            return input.substring(index + 2);
        }
        return input;
    }


}