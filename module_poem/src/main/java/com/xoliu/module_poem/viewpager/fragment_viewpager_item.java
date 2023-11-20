package com.xoliu.module_poem.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xoliu.module_poem.R;
import com.xoliu.module_poem.dialog.ShareDialog;


/***
 * 诗句ViewPager2的主界面子项碎片
 * @author xoliu
 * @create 23-11-20
 **/

public class fragment_viewpager_item extends Fragment {
    String url = "https://s2.loli.net/2023/11/20/ZCp7vukNhGsoAfT.png";
    String contentStr;
    String authorStr;
    public String shareNumStr;
    String commentNumStr;
    String likeNumStr;



    public fragment_viewpager_item() {

    }


    public fragment_viewpager_item( String content, String authorStr, int shareNum, int commentNum, int likeNum) {
        this.contentStr = content;
        this.authorStr = authorStr;
        this.shareNumStr = String.valueOf(shareNum);
        this.commentNumStr = String.valueOf(commentNum);
        this.likeNumStr = String.valueOf(likeNum);

    }


    public static fragment_viewpager_item newInstance() {
        fragment_viewpager_item fragment = new fragment_viewpager_item();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_viewpager_item, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化变量
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        TextView author = (TextView) view.findViewById(R.id.tv_author);
        TextView share_num = (TextView) view.findViewById(R.id.tv_share_num);
        TextView comment_num = (TextView) view.findViewById(R.id.tv_comment_num);
        TextView like_num = (TextView) view.findViewById(R.id.tv_like_num);
        ImageView topImage = (ImageView) view.findViewById(R.id.top_image);
        ImageButton btn_share = (ImageButton) view.findViewById(R.id.btn_share);

        //设置参数
        Glide.with(this).load(url).into(topImage);
        content.setText(contentStr);
        author.setText(authorStr);
        share_num.setText(shareNumStr);
        comment_num.setText(commentNumStr);
        like_num.setText(likeNumStr);

        //
        final Integer[] num = {Integer.valueOf(likeNumStr)};

        //设置点击事件
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.btn_like);
        imageButton.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;

            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    imageButton.setImageResource(R.drawable.like_checked);
                    num[0]++;
                    like_num.setText(num[0].toString());
                    isClicked = true;
                } else {
                    imageButton.setImageResource(R.drawable.like);
                    num[0]--;
                    like_num.setText(num[0].toString());
                    isClicked = false;
                }
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog dialogFragment = new ShareDialog();
                dialogFragment.show(getFragmentManager(), "bottomSheetDialog");
            }
        });
    }
}