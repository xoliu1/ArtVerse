package com.xoliu.module_poem.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.xoliu.module_poem.R;
import com.xoliu.module_poem.viewmodel.CommentViewModel;
import com.xoliu.module_poem.model.bean.commentItem;
import com.xoliu.module_poem.view.adapter.commentAdapter;

import global.CardPic;
import global.LegalResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentsSheetDialog extends BottomSheetDialogFragment {

    private Context iContext;
    private List<commentItem> comments = new ArrayList<>();
    private commentAdapter adapter;
    private CommentViewModel commentViewModel;
    private commentItem commentItem = new commentItem();
    private int num;
    private EditText et;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        commentViewModel.getUserPic().observe(this, new Observer<CardPic>() {
            @Override
            public void onChanged(CardPic cardPicBean) {
                //comments.get(new Random().nextInt(9)).setUserIcon(cardPicBean.getImgurl());
                //adapter.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), getTheme());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_poem_comment_dialog, null);
        dialog.setContentView(view);

        // 配置列表
        initData();
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(iContext)); // 设置布局管理器，此处使用线性布局
        adapter = new commentAdapter(comments);
        recyclerView.setAdapter(adapter); // 设置适配器

        et = view.findViewById(R.id.et_comment);
        Button bt = view.findViewById(R.id.btn_add);
        bt.setOnClickListener(v -> {
            String content = et.getText().toString();
            if (content.length() == 0) {
                Toast.makeText(requireActivity(), "不能为空", Toast.LENGTH_SHORT).show();
            } else {
                checkContentLegality(content);
            }
        });

        return dialog;
    }

    private void checkContentLegality(String content) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            boolean result = false;
            try {
                result = isLegal(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean finalResult = result;
            mainHandler.post(() -> {
                if (finalResult) {
                    sendStr();
                    TastyToast.makeText(requireActivity(), "评论成功！", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                }
            });
        });
    }

    public boolean isLegal(String content) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://apis.tianapi.com/antispam/index?key=1636e5d81b0d376a1478ef658a3fd0ed&content=" + content) // 替换为实际的URL
                .build();

        Response response = client.newCall(request).execute(); // Synchronous call
        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            Gson gson = new Gson();
            LegalResult result = gson.fromJson(jsonResponse, LegalResult.class);
            Log.d("TAG", "onResponse: " + result.toString());
            if (result.getResult().getConType().equals(1)) {
                return true;
            } else {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> TastyToast.makeText(requireActivity(), result.getResult().getList().get(0).getMsg(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show());
                return false;
            }
        } else {
            System.out.println("Request failed: " + response.code());
            return false;
        }
    }

    private void sendStr() {
        if (et.getText().toString().length() != 0) {
            comments.add(0, new commentItem("xoliu", formattedTime, et.getText().toString()));
            adapter.notifyDataSetChanged();
            et.setText("");
        }
    }

    public CommentsSheetDialog(int i) {
        super();
        num = i;
    }

    /***
     * 配置评论区的数据
     *
     * @return void
     * @author xoliu
     * @create 23-11-21
     **/

    void initData() {
        Random random = new Random();
        comments = commentItem.getArray(num);
    }

    String formattedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
