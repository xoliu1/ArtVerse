package com.xoliu.module_music.view.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_music.R;
import com.xoliu.module_music.model.bean.Recitation;

import java.util.ArrayList;
import java.util.List;

public class RecitationAdapter extends RecyclerView.Adapter<RecitationAdapter.MyViewHolder> {
    private List<Recitation> itemList;
    private Context mContext;
    MediaPlayer mediaPlayer;

    List<Integer> ids;

    public RecitationAdapter(Context context, List<Recitation> itemList) {
        this.mContext = context;
        this.itemList = itemList;
        ids = new ArrayList<>();
        ids.add(R.raw.lang1);
        ids.add(R.raw.lang2);
        ids.add(R.raw.lang3);
        ids.add(R.raw.lang4);
        ids.add(R.raw.lang5);
        ids.add(R.raw.lang6);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_origin_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recitation recitation = itemList.get(position);
        holder.img.setImageResource(recitation.getBgImgId());
        mediaPlayer = MediaPlayer.create(mContext, ids.get(position));
        holder.oriName.setText(recitation.getName());

        holder.btn_on.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start(); // 开始播放音乐

                holder.btn_on.setImageResource(R.drawable.pause2);
            } else {
                mediaPlayer.pause(); // 暂停播放音乐
                holder.btn_on.setImageResource(R.drawable.btn_on2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView oriName;
        ImageView btn_on;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            oriName = itemView.findViewById(R.id.oriName);
            btn_on = itemView.findViewById(R.id.btn_start);

        }
    }
}

