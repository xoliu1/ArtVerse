package com.xoliu.module_community.Present;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.xoliu.module_community.Adapter.commentAdapter;
import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.base;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class  MyBottomSheetFragment extends BottomSheetDialogFragment {

    private List<Integer> list;

    private List<base> strings;
    private RecyclerView recyclerView;

    private Context context;

    private FloatingActionButton btnAdd;

    Gson gson = new Gson();


    public MyBottomSheetFragment(List<Integer> list, List<base> strings,Context context) {

        this.list = list;
        this.strings = strings;
        this.context = context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog,container,false);
        initDate(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void initDate(View view){

        recyclerView = view.findViewById(R.id.remark);
        btnAdd = view.findViewById(R.id.btn_add);

        commentAdapter commentAdapter1 = new commentAdapter(strings,list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(commentAdapter1);
        btnAdd.setOnClickListener(v -> {
            base base1 = new base("测试用户","测试用户发出的评论");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        String json = gson.toJson(base1);
                        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
                        Request request = new Request.Builder()
                                .url("http://8.130.118.185:6666/api/user/put/comment")
                                .post(body)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("TAG", "onFailure: 增加评论失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.d("TAG", "onResponse: " + response.body().string());
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}
