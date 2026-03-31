package com.xoliu.module_profile.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_profile.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<NoteBean> noteList;
    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClick(NoteBean note, int position);
        void onNoteLongClick(NoteBean note, int position);
    }

    public NoteAdapter(List<NoteBean> noteList, OnNoteClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteBean note = noteList.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        String time = note.getUpdatedAt() != null && !note.getUpdatedAt().isEmpty()
                ? note.getUpdatedAt() : note.getCreatedAt();
        holder.tvTime.setText(formatTime(time));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoteClick(note, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onNoteLongClick(note, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return noteList == null ? 0 : noteList.size();
    }

    public void updateData(List<NoteBean> newList) {
        this.noteList = newList;
        notifyDataSetChanged();
    }

    /**
     * 将后端返回的时间字符串格式化为 yyyy-MM-dd HH:mm
     */
    private String formatTime(String rawTime) {
        if (rawTime == null || rawTime.isEmpty() || rawTime.startsWith("0001")) {
            return "";
        }
        try {
            // 后端 Go 返回的 time.Time JSON 格式，如 "2026-03-26T15:30:00+08:00" 或 "2026-03-26T07:30:00Z"
            SimpleDateFormat isoFormat;
            if (rawTime.contains("T") && rawTime.endsWith("Z")) {
                isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            } else if (rawTime.contains("T")) {
                isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
            } else {
                return rawTime;
            }
            Date date = isoFormat.parse(rawTime);
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            displayFormat.setTimeZone(TimeZone.getDefault());
            return displayFormat.format(date);
        } catch (Exception e) {
            // 解析失败则返回原始字符串
            return rawTime;
        }
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_note_title);
            tvContent = itemView.findViewById(R.id.tv_note_content);
            tvTime = itemView.findViewById(R.id.tv_note_time);
        }
    }
}
