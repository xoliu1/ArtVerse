package com.xoliu.module_poem.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xoliu.module_poem.R;
import com.xoliu.module_poem.viewmodel.CommentViewModel;
import com.xoliu.module_poem.model.bean.commentItem;
import com.xoliu.module_poem.view.adapter.commentAdapter;

import global.CardPic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentsSheetDialog extends BottomSheetDialogFragment {

    private Context iContext;


    private List<commentItem> comments = new ArrayList<>();

    commentAdapter adapter;

    CommentViewModel commentViewModel;

    commentItem commentItem = new commentItem();

    int num;

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
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_poem_comment_dialog, null);
        dialog.setContentView(contentView);

        //Viewmodel


        //配置列表
        initData();
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(iContext)); // 设置布局管理器，此处使用线性布局
        adapter = new commentAdapter(comments);
        recyclerView.setAdapter(adapter); // 设置适配器

        return dialog;
    }

    public CommentsSheetDialog(int i){
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

    void initData(){
        Random random = new Random();
        comments = commentItem.getArray(num);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
