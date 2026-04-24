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
import com.xoliu.module_music.model.bean.Song;
import com.xoliu.module_music.view.activity.CDActivity;
import com.xoliu.common.utils.FontCache;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_popular_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);

        // 设置歌曲名字
        holder.songNameTextView.setText(song.getName());

        // 设置歌曲来源
        holder.songFromTextView.setText(song.getFrom());

        // 应用自定义字体
        FontCache.apply(holder.songNameTextView, holder.itemView.getContext(), FontCache.FONT11);

        // 黄色播放按钮点击事件：跳转到 CDActivity 播放对应音频
        holder.btnPlay.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CDActivity.class);
            intent.putExtra("audio_res_id", song.getAudioResId());
            intent.putExtra("recitation_name", song.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView;
        TextView songFromTextView;
        ImageView btnPlay;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.song_name);
            songFromTextView = itemView.findViewById(R.id.song_from);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }
}

