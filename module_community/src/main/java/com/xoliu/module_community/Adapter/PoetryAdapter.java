package com.xoliu.module_community.Adapter;

import android.content.Context;
import android.text.TextUtils;
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

    /**
     * 关注按钮点击回调接口
     */
    public interface OnFollowClickListener {
        void onFollowClick(player item, int position, boolean isCurrentlyFollowed);
    }

    List<Integer> integerList;
    Context context;
    List<player> playerList;
    OnFollowClickListener followClickListener;

    public PoetryAdapter(Context context, List<player> playerList) {
        this(context, playerList, null);
    }

    public PoetryAdapter(Context context, List<player> playerList, OnFollowClickListener listener) {
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
        this.followClickListener = listener;
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
        holder.textView.setText(pName);

        // 显示标题（如果有的话）
        if (!title.isEmpty()) {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText("「" + title + "」");
        } else {
            holder.tvTitle.setVisibility(View.GONE);
        }

        // 显示内容（XML中已设置 maxLines=3 + ellipsize=end，自动截断）
        holder.textViewT.setText(content);

        // 根据数据模型中的关注状态设置按钮样式
        updateFollowButton(holder, item.isFollowed());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followClickListener != null) {
                    // 回调给 Activity 处理真实的关注/取消关注逻辑
                    followClickListener.onFollowClick(item, holder.getAdapterPosition(), item.isFollowed());
                } else {
                    // 兜底：如果没设置回调，走本地切换（向后兼容）
                    item.setFollowed(!item.isFollowed());
                    updateFollowButton(holder, item.isFollowed());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList == null ? 0 : playerList.size();
    }

    /**
     * 更新关注按钮的视觉状态
     */
    private void updateFollowButton(PoetryItem holder, boolean isFollowed) {
        if (isFollowed) {
            holder.button.setText("已关注");
            holder.button.setTextColor(0xFFFFFFFF);
            holder.button.setBackgroundResource(R.drawable.bg_followed_btn);
        } else {
            holder.button.setText("+关注");
            holder.button.setTextColor(0xFF8B6B4A);
            holder.button.setBackgroundResource(R.drawable.bg_follow_btn);
        }
    }

    /**
     * 外部调用：更新指定位置的关注状态
     */
    public void updateFollowStatus(int position, boolean isFollowed) {
        if (position >= 0 && position < playerList.size()) {
            playerList.get(position).setFollowed(isFollowed);
            notifyItemChanged(position);
        }
    }

    class PoetryItem extends RecyclerView.ViewHolder{

        ImageView imageView;

        TextView textView;
        TextView tvTitle;
        TextView textViewT;

        Button button;

        public PoetryItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.laydy);
            textView = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.focus);
            tvTitle = itemView.findViewById(R.id.tv_title);
            textViewT = itemView.findViewById(R.id.about);
        }
    }
}
