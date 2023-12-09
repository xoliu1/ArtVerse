package com.xoliu.module_art;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public int getItemCount() {
        return artCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}

