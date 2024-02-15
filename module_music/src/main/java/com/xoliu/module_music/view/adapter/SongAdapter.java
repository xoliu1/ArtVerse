package com.xoliu.module_music.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_music.R;
import com.xoliu.module_music.model.bean.Song;

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


    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView;
        TextView songFromTextView;


        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.song_name);
            songFromTextView = itemView.findViewById(R.id.song_from);

        }
    }
}

