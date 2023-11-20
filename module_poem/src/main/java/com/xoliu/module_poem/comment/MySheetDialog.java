package com.xoliu.module_poem.comment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xoliu.module_poem.R;

import java.util.ArrayList;
import java.util.List;

public class MySheetDialog extends BottomSheetDialogFragment {

    private Context iContext;


    private List<commentItem> comments = new ArrayList<>();

    commentAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), getTheme());
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_poem_comment_dialog, null);
        dialog.setContentView(contentView);
        //配置列表
        initData();
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(iContext)); // 设置布局管理器，此处使用线性布局
        adapter = new commentAdapter(comments);
        recyclerView.setAdapter(adapter); // 设置适配器

        return dialog;
    }

    /***
     * 配置评论区的数据
     *
     * @return void
     * @author xoliu
     * @create 23-11-21
     **/

    void initData(){
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n                             ————泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));

    }


}
