package com.xoliu.module_community.Adapter;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;

import java.util.List;

public class recycAdapter extends RecyclerView.Adapter<recycAdapter.talk> {


    Context context;

    List<String> stringList;

    public recycAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public talk onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        talk jklove = new talk(view);
        return jklove;
    }

    @Override
    public void onBindViewHolder(@NonNull talk holder, int position) {
        String x = stringList.get(position);
        Spannable spannable2 = Spannable.Factory.getInstance().newSpannable("#" + x.substring(0,16) + "\n\n#" + x.substring(16,x.length()) + "\n");
        holder.textView.setText(spannable2);
    }

    @Override
    public int getItemCount() {
        return stringList == null ? 0 : stringList.size() ;
    }

    class talk extends RecyclerView.ViewHolder{
        TextView textView;
        public talk(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.discuss);
        }
    }
}
