package com.xoliu.module_art;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/***
 * 艺术主界面的ViewPager适配器
 * @return
 * @author xoliu
 * @create 23-12-8
 **/

public class ArtCardAdapter extends RecyclerView.Adapter<ArtCardAdapter.ViewHolder> {
    private List<ArtCard> artCards;
    private Context context;

    public ArtCardAdapter(List<ArtCard> artCards, Context context) {
        this.artCards = artCards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_art_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtCard artCard = artCards.get(position);
        //对各个控件进行初始化
        holder.mArtImg.setImageResource(artCard.getArtImgId());
        holder.mArtName.setText(artCard.getArtName());
        holder.mArtAuthor.setText(artCard.getArtAuthor());

        holder.mArtImg.setOnClickListener(v -> {
            //进入到详情页面,传入当前对应的卡片
            context.startActivity(new Intent(context,ArtContentActivity.class).putExtra("theArtCardContentInfo",artCard.getArtContent()));

        });
    }

    @Override
    public int getItemCount() {
        return artCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mArtImg;
        TextView mArtName;
        TextView mArtAuthor;

        public ViewHolder(@NonNull View v) {
            super(v);
            this.mArtImg = (ImageView) v.findViewById(R.id.artImg);
            this.mArtName = (TextView) v.findViewById(R.id.artName);
            this.mArtAuthor = (TextView) v.findViewById(R.id.artAuthor);
        }
    }
}

