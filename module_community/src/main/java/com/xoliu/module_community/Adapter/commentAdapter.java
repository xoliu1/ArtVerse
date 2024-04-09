package com.xoliu.module_community.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.commentItem>{

    private List<base> list;

    private List<Integer> integers;

    Context context;

    public commentAdapter(List<base> list, List<Integer> integers, Context context) {
        this.list = list;
        this.integers = integers;
        this.context = context;
    }

    @NonNull
    @Override
    public commentItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new commentItem(LayoutInflater.from(context).inflate(R.layout.comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull commentItem holder, int position) {
        String x = list.get(position).getPoem();
        String y = list.get(position).getName();
        String currentTime = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        Log.d("TAD", "onBindViewHolder: " + x + y + currentTime);
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(y + "\n" + currentTime);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#46a2ff"));
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        RelativeSizeSpan relativeSizeSpanBig = new RelativeSizeSpan(1.0f);
        RelativeSizeSpan relativeSizeSpanSmall = new RelativeSizeSpan(0.6f);
        spannable.setSpan(foregroundColorSpan,0,y.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(relativeSizeSpanBig,0,y.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(relativeSizeSpanSmall,y.length() + 1,y.length() + 11,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(styleSpan,0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.textView2.setText(spannable);
        Spannable spannable2 = Spannable.Factory.getInstance().newSpannable(x.substring(0,10) + "\n" + x.substring(10,x.length()));
        holder.textView.setText(spannable2);
        holder.imageView.setImageResource(integers.get(position % 7));
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.aBoolean) {
                    holder.imageView2.setImageResource(R.drawable.unsupport);
                    holder.aBoolean = false;
                } else {
                    holder.imageView2.setImageResource(R.drawable.support);
                    holder.aBoolean = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class commentItem extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView imageView2;
        TextView textView;
        TextView textView2;
        boolean aBoolean = true;

        public commentItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.commentName);
            imageView2 = itemView.findViewById(R.id.commentButton);
            textView = itemView.findViewById(R.id.commentPoem);
            textView2 = itemView.findViewById(R.id.commentText);
        }
    }
}
