package com.xoliu.module_music.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_music.R;

import java.util.List;

import global.XPoemB;

public class XPoemBAdapter extends RecyclerView.Adapter<XPoemBAdapter.PoemViewHolder>{
    private XPoemB xPoemB;

    public XPoemBAdapter(XPoemB poemList) {
        this.xPoemB = poemList;
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
    public XPoemBAdapter.PoemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poem, parent, false);
        return new XPoemBAdapter.PoemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(XPoemBAdapter.PoemViewHolder holder, int position) {
        XPoemB.Data poem = xPoemB.getData().get(position);
        holder.tvPoemOrigin.setText(poem.getOrigin());
        holder.tvPoemAuthor.setText(poem.getAuthor());
        holder.tvPoemCategory.setText(poem.getCategory());
        holder.tvPoemContent.setText(poem.getContent());
    }

    @Override
    public int getItemCount() {
        if (xPoemB == null || xPoemB.getData() == null){
            return 0;
        }
        return xPoemB.getData().size();
    }
    public void addPoems(List<XPoemB.Data> datas){
        xPoemB.setData(datas);
        notifyDataSetChanged();
    }
}
