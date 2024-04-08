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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.base;
import com.xoliu.module_community.mModel.player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class personAdapter extends RecyclerView.Adapter<personAdapter.PoetryItem> {
    Context context;

    Integer integer;

    String string;
    List<base> list;


    @NonNull
    @Override
    public PoetryItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return new PoetryItem(inflater.inflate(R.layout.people,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryItem holder, int position) {
        String x = list.get(position).getPoem();
        String y = string;
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
        holder.textView.setText(spannable);
        Spannable spannable2 = Spannable.Factory.getInstance().newSpannable(x.substring(0,16) + "\n" + x.substring(16,x.length()));
        holder.textView2.setText(spannable2);
        holder.imageView2.setImageResource(integer);
        holder.textView3.setText("65");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.aBoolean) {
                    holder.imageView.setImageResource(R.drawable.unsupport);
                    holder.aBoolean = false;
                    holder.textView3.setText("66");
                } else {
                    holder.imageView.setImageResource(R.drawable.support);
                    holder.aBoolean = true;
                    holder.textView3.setText("65");
                }
            }
        });

    }

    public personAdapter(Context context, Integer integer, String string, List<base> list) {
        this.context = context;
        this.integer = integer;
        this.string = string;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class PoetryItem extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView imageView2;
        TextView textView;
        TextView  textView2;
        TextView textView3;
        Boolean aBoolean = true;

        public PoetryItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.figer);
            imageView2 = itemView.findViewById(R.id.picture);
            textView = itemView.findViewById(R.id.people);
            textView2 = itemView.findViewById(R.id.peoplePoem);
            textView3 = itemView.findViewById(R.id.messageK);
        }
    }
}
