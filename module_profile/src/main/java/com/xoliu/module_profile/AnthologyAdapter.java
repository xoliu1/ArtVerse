package com.xoliu.module_profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_profile.anthology.AnthologyBean;

import java.util.List;

public class AnthologyAdapter extends RecyclerView.Adapter<AnthologyAdapter.ViewHolder> {
    private List<AnthologyBean> list;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onDelete(AnthologyBean item, int position);
        void onUpdate(AnthologyBean item, int position);
    }

    public AnthologyAdapter(List<AnthologyBean> list, OnItemActionListener listener) {
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.profile_poem_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnthologyBean item = list.get(position);
        holder.content.setText(item.getContent());
        holder.author.setText(item.getTitle());

        holder.mainView.setOnLongClickListener(v -> {
            showPopupMenu(v, item, position);
            return true;
        });
    }

    private void showPopupMenu(View view, AnthologyBean item, int position) {
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
        CardView mainView;
        TextView author;
        TextView content;

        public ViewHolder(@NonNull View v) {
            super(v);
            author = v.findViewById(R.id.profile_poem_author);
            content = v.findViewById(R.id.profile_poem_context);
            mainView = v.findViewById(R.id.profile_poem_cardView);
        }
    }
}
