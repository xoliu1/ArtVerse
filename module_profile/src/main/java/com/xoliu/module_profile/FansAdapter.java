package com.xoliu.module_profile;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FansAdapter extends RecyclerView.Adapter<FansAdapter.FanViewHolder> {

    public interface OnFollowActionListener {
        void onFollowClick(FanItem item, int position, boolean isCurrentlyFollowed);
    }

    private List<FanItem> fanList;
    private Context context;
    private OnFollowActionListener listener;
    private boolean isFollowingTab; // true=关注列表, false=粉丝列表

    public FansAdapter(Context context, List<FanItem> fanList, boolean isFollowingTab, OnFollowActionListener listener) {
        this.context = context;
        this.fanList = fanList;
        this.isFollowingTab = isFollowingTab;
        this.listener = listener;
    }

    public void setFollowingTab(boolean followingTab) {
        this.isFollowingTab = followingTab;
    }

    @NonNull
    @Override
    public FanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fan, parent, false);
        return new FanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FanViewHolder holder, int position) {
        FanItem item = fanList.get(position);

        // 设置用户名
        holder.tvUsername.setText(item.getUsername());

        // 加载头像
        if (!TextUtils.isEmpty(item.getAvatarUrl())) {
            Glide.with(context)
                    .load(item.getAvatarUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.wechat_icon)
                            .error(R.drawable.wechat_icon)
                            .circleCrop())
                    .into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.wechat_icon);
        }

        // 设置按钮状态
        updateButtonState(holder.btnAction, item.isFollowed());

        // 按钮点击事件
        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFollowClick(item, position, item.isFollowed());
            }
        });
    }

    private void updateButtonState(Button btn, boolean isFollowed) {
        if (isFollowed) {
            btn.setText("已关注");
            btn.setTextColor(0xFFFFFFFF);
            btn.setBackgroundResource(R.drawable.bg_followed_btn);
        } else {
            if (isFollowingTab) {
                btn.setText("+关注");
            } else {
                btn.setText("回关");
            }
            btn.setTextColor(0xFF8B6B4A);
            btn.setBackgroundResource(R.drawable.bg_follow_btn);
        }
    }

    @Override
    public int getItemCount() {
        return fanList == null ? 0 : fanList.size();
    }

    public void updateItem(int position, boolean isFollowed) {
        if (position >= 0 && position < fanList.size()) {
            fanList.get(position).setFollowed(isFollowed);
            notifyItemChanged(position);
        }
    }

    static class FanViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUsername;
        Button btnAction;

        public FanViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
