package com.xoliu.module_poem.comment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.xoliu.module_poem.viewpager.MainRepository;
import com.xoliu.module_poem.viewpager.card_picBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySheetDialog extends BottomSheetDialogFragment {

    private Context iContext;


    private List<commentItem> comments = new ArrayList<>();

    commentAdapter adapter;

    CommentViewModel commentViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        commentViewModel.getUserPic().observe(this, new Observer<card_picBean>() {
            @Override
            public void onChanged(card_picBean cardPicBean) {
                comments.get(new Random().nextInt(9)).setUserIcon(cardPicBean.getImgurl());
                adapter.notifyDataSetChanged();
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

    /***
     * 配置评论区的数据
     *
     * @return void
     * @author xoliu
     * @create 23-11-21
     **/

    void initData(){
        comments.add(new commentItem("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n                             ————泰戈尔"));
        comments.add(new commentItem("https://tvax1.sinaimg.cn/large/9bd9b167ly1g1p9b71tgdj20b40b4gmg.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax3.sinaimg.cn/large/9bd9b167gy1g1p9ps0ruxj20b40b40t8.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax4.sinaimg.cn/large/9bd9b167ly1g1p9q0dzdfj20b40b43z3.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax2.sinaimg.cn/large/9bd9b167gy1fzjvs9a6h6j20b40b4t98.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax2.sinaimg.cn/large/9bd9b167ly1fzjvsm8whcj20b40b4t9c.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax2.sinaimg.cn/large/9bd9b167ly1fzjvt61vnxj20b40b4aaw.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));
        comments.add(new commentItem("https://tvax1.sinaimg.cn/large/9bd9b167ly1g1p9vofvldj20b40b43yv.jpg", "樊光辉","11-21","蝴蝶所计算的，并非年月，而是刹那，因此蝴蝶拥有充足的时间。\n泰戈尔"));




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
