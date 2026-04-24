package com.xoliu.module_music.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_music.R;
import com.xoliu.module_music.model.bean.Recitation;
import com.xoliu.module_music.view.activity.CDActivity;
import com.xoliu.common.utils.FontCache;

import java.util.List;

public class RecitationAdapter extends RecyclerView.Adapter<RecitationAdapter.MyViewHolder> {
    private List<Recitation> itemList;

    public RecitationAdapter(List<Recitation> itemList) {
        this.itemList = itemList;
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
        holder.oriName.setText(recitation.getName());

        // 应用自定义字体
        FontCache.apply(holder.oriName, holder.itemView.getContext(), FontCache.FONT11);

        // 红色三角形播放按钮点击事件：跳转到 CDActivity 播放对应音频
        holder.btnPlay.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CDActivity.class);
            intent.putExtra("audio_res_id", recitation.getAudioResId());
            intent.putExtra("recitation_name", recitation.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView btnPlay;
        TextView oriName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            oriName = itemView.findViewById(R.id.oriName);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }
}

