package com.xoliu.module_profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import db.bean.GalleryBean;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<GalleryBean> list;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onDelete(GalleryBean item, int position);
        void onUpdate(GalleryBean item, int position);
    }

    public GalleryAdapter(List<GalleryBean> list, OnItemActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryBean item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.creator.setText(item.getCreator() != null && !item.getCreator().isEmpty()
                ? item.getCreator() : "未知作者");
        holder.year.setText(item.getYear() != null && !item.getYear().isEmpty()
                ? item.getYear() : "");

        // 用 Glide 加载七牛云图片
        Context context = holder.itemView.getContext();
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.wechat_icon)
                    .error(R.drawable.wechat_icon)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.wechat_icon);
        }

        // 点击进入详情页
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GalleryDetailActivity.class);
            intent.putExtra("gallery_data", item);
            context.startActivity(intent);
        });

        // 长按弹出编辑/删除菜单
        holder.cardView.setOnLongClickListener(v -> {
            showPopupMenu(v, item, position);
            return true;
        });
    }

    private void showPopupMenu(View view, GalleryBean item, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenu().add(0, 1, 0, "编辑");
        popup.getMenu().add(0, 2, 1, "删除");
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case 1:
                    if (listener != null) listener.onUpdate(item, position);
                    return true;
                case 2:
                    if (listener != null) listener.onDelete(item, position);
                    return true;
            }
            return false;
        });
        popup.show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView title;
        TextView creator;
        TextView year;

        public ViewHolder(@NonNull View v) {
            super(v);
            cardView = v.findViewById(R.id.gallery_cardView);
            image = v.findViewById(R.id.gallery_image);
            title = v.findViewById(R.id.gallery_title);
            creator = v.findViewById(R.id.gallery_creator);
            year = v.findViewById(R.id.gallery_year);
        }
    }
}
