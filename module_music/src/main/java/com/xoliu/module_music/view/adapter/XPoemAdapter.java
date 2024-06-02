package com.xoliu.module_music.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.xoliu.module_music.R;
import java.util.List;

import global.XPoemA;

public class XPoemAdapter extends RecyclerView.Adapter<XPoemAdapter.PoemViewHolder> {

    private List<XPoemA> poemList;

    public XPoemAdapter(List<XPoemA> poemList) {
        this.poemList = poemList;
    }

    public static class PoemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPoemOrigin;
        public TextView tvPoemAuthor;
        public TextView tvPoemCategory;
        public TextView tvPoemContent;

        public PoemViewHolder(View itemView) {
            super(itemView);
            tvPoemOrigin = itemView.findViewById(R.id.tvPoemOrigin);
            tvPoemAuthor = itemView.findViewById(R.id.tvPoemAuthor);
            tvPoemCategory = itemView.findViewById(R.id.tvPoemCategory);
            tvPoemContent = itemView.findViewById(R.id.tvPoemContent);
        }
    }

    @Override
    public PoemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poem, parent, false);
        return new PoemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PoemViewHolder holder, int position) {
        XPoemA poem = poemList.get(position);
        holder.tvPoemOrigin.setText(poem.getOrigin());
        holder.tvPoemAuthor.setText(poem.getAuthor());
        holder.tvPoemCategory.setText(poem.getCategory());
        holder.tvPoemContent.setText(poem.getContent());
    }

    @Override
    public int getItemCount() {
        return poemList.size();
    }
    public void addPoem(XPoemA poem) {
        poemList.add(poem);
        notifyItemInserted(poemList.size() - 1);
    }
    public void addPoems(List<XPoemA> poemAS){
        poemList.addAll(poemAS);
        notifyDataSetChanged();
    }
    public void updatePoems(List<XPoemA> newPoemList) {
        poemList = newPoemList;
        notifyDataSetChanged();
    }
}
