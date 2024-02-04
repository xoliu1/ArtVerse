package com.xoliu.module_profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import db.AppDatabase;
import db.bean.ArtContent;
import db.bean.PoemCard;

public class ArtContentAdapter extends RecyclerView.Adapter<ArtContentAdapter.ViewHolder> {

    private Context mContext;
    private List<ArtContent> mArtItemList;

    public ArtContentAdapter(Context context, List<ArtContent> artItemList) {
        mContext = context;
        mArtItemList = artItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.profile_art_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtContent artItem = mArtItemList.get(position);

        holder.imageView.setImageResource(artItem.getArtContentImg());
        holder.nameTextView.setText(artItem.getName());

        String s = artItem.getCreator();
        int tempInt = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ' || Character.isLetter(c)){
            }else{
                tempInt = i;
                break;
            }
        }
        holder.authorTextView.setText(s.substring(0, tempInt));

        holder.imageView.setOnLongClickListener(v -> {
            alertClick(v, artItem);
            return true;
        });

        holder.imageView.setOnClickListener(v -> {
            //进入到详情页面,传入当前对应的卡片
            int startX = (int) v.getX(); // 起始位置的X坐标
            int startY = (int) v.getY(); // 起始位置的Y坐标
            int startWidth = v.getWidth(); // 起始宽度
            int startHeight = v.getHeight(); // 起始高度
            Bundle bundle = ActivityOptionsCompat.makeScaleUpAnimation(holder.imageView, startX, startY, startWidth, startHeight).toBundle();
            mContext.startActivity(new Intent(mContext,ArtContentActivity.class).putExtra("theArtCardContentInfo",artItem),bundle);
        });

    }

    @Override
    public int getItemCount() {
        return mArtItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_art_item_img);
            nameTextView = itemView.findViewById(R.id.profile_art_item_name);
            authorTextView = itemView.findViewById(R.id.profile_art_item_author);
        }
    }
    public void alertClick(View v, ArtContent artContent) {
        //创建 一个提示对话框的构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("");//设置弹出对话框的标题
        builder.setMessage("确定不同感该词文吗？");//设置弹出对话框的内容
        builder.setCancelable(false);//能否被取消
        //正面的按钮（肯定）
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = Room.databaseBuilder(builder.getContext(), AppDatabase.class, "PoemCards").build();
                        db.artContentDao().deletePaintingsByArtistName(artContent.getName());
                    }
                }).start();
                mArtItemList.remove(artContent);
                notifyDataSetChanged();
                dialog.cancel();
            }
        });
        //反面的按钮（否定）
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}