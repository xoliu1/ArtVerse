package com.xoliu.module_community.adatpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;

import java.util.List;

public class itemTwo extends RecyclerView.Adapter<itemTwo.part> {

    private Context context;

    List<Integer> list;

    List<String> stringList;

    List<String> stringList2;

    public itemTwo(Context context, List<Integer> list, List<String> stringList, List<String> stringList2) {
        this.context = context;
        this.list = list;
        this.stringList = stringList;
        this.stringList2 = stringList2;
    }
    public itemTwo(Context context, List<Integer> list, List<String> stringList) {
        this.context = context;
        this.list = list;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public part onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poembattle,parent,false);
        part part2 = new part(view);
        return part2;
    }

    @Override
    public void onBindViewHolder(@NonNull part holder, int position) {
        holder.imageView.setImageResource(list.get(position));
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(stringList.get(position));
        RelativeSizeSpan relativeSizeSpanBig = new RelativeSizeSpan(1.2f);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#35c0f1"));
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(relativeSizeSpanBig,0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(foregroundColorSpan,0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(styleSpan,0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.textView.setText(spannable);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class part extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView1;
        public part(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poemEr);
            textView = itemView.findViewById(R.id.poemjk);
            textView1 = itemView.findViewById(R.id.bottom);
        }
    }
}
