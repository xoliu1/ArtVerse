package com.xoliu.module_community.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoliu.module_community.R;

import com.xoliu.module_community.mModel.player;
import com.xoliu.module_community.showActivity;

import java.util.ArrayList;
import java.util.List;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryItem>{

    List<Integer> integerList;
    Context context;
    List<player> playerList;

    public PoetryAdapter(Context context, List<player> playerList) {
        integerList = new ArrayList<>();
        integerList.add(R.drawable.tx1);
        integerList.add(R.drawable.tx2);
        integerList.add(R.drawable.tx3);
        integerList.add(R.drawable.tx4);
        integerList.add(R.drawable.tx5);
        integerList.add(R.drawable.tx6);
        integerList.add(R.drawable.tx7);
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PoetryItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.peotryitem,parent,false);
        PoetryItem peotryItem = new PoetryItem(view);
        return peotryItem;
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryItem holder, int position) {
        player item = playerList.get(position);
        String pName = item.getPName() != null ? item.getPName() : "";
        String content = item.getSigner() != null ? item.getSigner() : "";
        String title = item.getTitle() != null ? item.getTitle() : "";
        String avatarUrl = item.getAvatarUrl();

        // 加载头像：有URL用Glide加载，否则用本地随机头像
        int fallbackRes = integerList.get(position % 7);
        if (!TextUtils.isEmpty(avatarUrl)) {
            Glide.with(context)
                    .load(avatarUrl)
                    .apply(new RequestOptions()
                            .placeholder(fallbackRes)
                            .error(fallbackRes)
                            .circleCrop())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(fallbackRes);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/showActivity/main").navigation();
                showActivity.string = pName;
                showActivity.name = integerList.get(position % 7);
            }
        });

        // 显示用户名
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(pName);
        if (pName.length() > 0) {
            RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.6f);
            StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
            spannable.setSpan(styleSpan, 0, pName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(relativeSizeSpan, 0, pName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.textView.setText(spannable);

        // 显示标题+内容（如果有标题则显示"标题：内容"，否则只显示内容）
        String displayText = title.isEmpty() ? content : "「" + title + "」" + content;
        Spannable spannable1 = Spannable.Factory.getInstance().newSpannable(displayText);
        if (displayText.length() > 0) {
            RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(1.4f);
            spannable1.setSpan(relativeSizeSpan1, 0, displayText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.textViewT.setText(spannable1);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.pop){
                    holder.button.setText("+关注");
                    holder.pop = false;
                }else {
                    holder.button.setText("✓关注");
                    holder.pop = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList == null ? 0 : playerList.size();
    }

    class PoetryItem extends RecyclerView.ViewHolder{

        ImageView imageView;

        TextView textView;
        TextView textViewT;

        Button button;

        Boolean pop = false;

        public PoetryItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.laydy);
            textView = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.focus);
            textViewT = itemView.findViewById(R.id.about);
        }
    }
}
