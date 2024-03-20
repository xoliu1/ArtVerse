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

public class conmer extends RecyclerView.Adapter<conmer.peopleItem> {

    private List<Integer> integerList;
    private List<String>  stringList;
    private Context context;

    public conmer(List<Integer> integerList, List<String> stringList, Context context) {
        this.integerList = integerList;
        this.stringList = stringList;
        this.context = context;
    }

    @NonNull
    @Override
    public peopleItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people,parent,false);
        peopleItem peopleItem1 = new peopleItem(view);
        return peopleItem1;
    }

    @Override
    public void onBindViewHolder(@NonNull peopleItem holder, int position) {
        holder.imageView.setImageResource(integerList.get(position));
        holder.imageView2.setImageResource(R.drawable.left);
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.aBoolean){
                    holder.imageView2.setImageResource(R.drawable.right);
                    holder.aBoolean = true;
                }else {
                    holder.imageView2.setImageResource(R.drawable.left);
                    holder.aBoolean = false;
                }
            }
        });
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(stringList.get(0));
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
        return integerList == null ? 0 : integerList.size();
    }

    class peopleItem extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        ImageView imageView2;
        Boolean aBoolean = false;
        public peopleItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.coster);
            textView = itemView.findViewById(R.id.intor);
            imageView2 = itemView.findViewById(R.id.con);
        }
    }
}
