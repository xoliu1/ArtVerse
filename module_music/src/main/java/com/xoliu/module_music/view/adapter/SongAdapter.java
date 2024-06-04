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
import com.xoliu.module_music.model.bean.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private Context mContext;
    MediaPlayer mediaPlayer;

    List<Integer> ids;

    public SongAdapter(Context context, List<Song> songList) {
        this.mContext = context;
        this.songList = songList;
        ids = new ArrayList<>();
        ids.add(R.raw.song1);
        ids.add(R.raw.song2);
        ids.add(R.raw.song3);
        ids.add(R.raw.song4);
        ids.add(R.raw.song5);
        ids.add(R.raw.song6);
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
        mediaPlayer = MediaPlayer.create(mContext, ids.get(position));
        // 设置歌曲名字
        holder.songNameTextView.setText(song.getName());

        // 设置歌曲来源
        holder.songFromTextView.setText(song.getFrom());

        holder.btn_on.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start(); // 开始播放音乐

                holder.btn_on.setImageResource(R.drawable.pause2);
            } else {
                mediaPlayer.pause(); // 暂停播放音乐
                holder.btn_on.setImageResource(R.drawable.btn_on);
            }
        });


    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView;
        TextView songFromTextView;

        ImageView btn_on;


        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.song_name);
            songFromTextView = itemView.findViewById(R.id.song_from);
            btn_on = itemView.findViewById(R.id.btn_bofang);

        }
    }
}

