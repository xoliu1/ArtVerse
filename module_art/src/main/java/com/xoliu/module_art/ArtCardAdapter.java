package com.xoliu.module_art;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


/***
 * ViewPager adapter (supports masterpiece and personal work card types)
 * @author xoliu
 * @create 23-12-8
 **/

public class ArtCardAdapter extends RecyclerView.Adapter<ArtCardAdapter.CardViewHolder> {
    private List<ArtCard> artCards;
    private Context context;

    public ArtCardAdapter(List<ArtCard> artCards, Context context) {
        this.artCards = artCards;
        this.context = context;
    }

    /**
     * Replace data source and refresh
     */
    public void setData(List<ArtCard> newData) {
        this.artCards = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_art_card, parent, false);
        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ArtCard artCard = artCards.get(position);

        holder.mArtName.setText(artCard.getArtName());
        holder.mArtAuthor.setText(artCard.getArtAuthor());

        // Load image based on type
        if (artCard.getCardType() == ArtCard.TYPE_PERSONAL
                && artCard.getImageUrl() != null
                && !artCard.getImageUrl().isEmpty()) {
            // Personal work: load with Glide
            Glide.with(context)
                    .load(artCard.getImageUrl())
                    .centerCrop()
                    .into(holder.mArtImg);
        } else {
            // Masterpiece: local drawable
            holder.mArtImg.setImageResource(artCard.getArtImgId());
        }

        holder.mArtImg.setOnClickListener(v -> {
            if (artCard.getArtContent() != null) {
                int startX = (int) v.getX();
                int startY = (int) v.getY();
                int startWidth = v.getWidth();
                int startHeight = v.getHeight();
                Bundle bundle = ActivityOptionsCompat.makeScaleUpAnimation(
                        holder.mArtImg, startX, startY, startWidth, startHeight).toBundle();

                if (artCard.getCardType() == ArtCard.TYPE_PERSONAL) {
                    Intent intent = new Intent(context, ArtPersonalDetailActivity.class);
                    intent.putExtra("gallery_data", artCard.getGalleryBean());
                    context.startActivity(intent, bundle);
                } else {
                    context.startActivity(new Intent(context, ArtContentActivity.class)
                            .putExtra("theArtCardContentInfo", artCard.getArtContent()), bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artCards == null ? 0 : artCards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView mArtImg;
        TextView mArtName;
        TextView mArtAuthor;

        public CardViewHolder(@NonNull View v) {
            super(v);
            this.mArtImg = (ImageView) v.findViewById(R.id.artImg);
            this.mArtName = (TextView) v.findViewById(R.id.artName);
            this.mArtAuthor = (TextView) v.findViewById(R.id.artAuthor);
        }
    }
}
