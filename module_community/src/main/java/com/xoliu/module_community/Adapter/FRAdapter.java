package com.xoliu.module_community.Adapter;

import android.content.Context;
import android.graphics.Color;

import android.graphics.Typeface;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xoliu.module_community.Present.MyBottomSheetFragment;
import com.xoliu.module_community.Present.commentNet;
import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.base;
import com.xoliu.module_community.mModel.date;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class FRAdapter extends RecyclerView.Adapter {

    List<base> list;

    List<Integer> integerList;

    Context context;

    private FragmentManager fragmentManager;

    private static final int  INT_TYPE = 1;
    private static final int  s_TYPE = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == INT_TYPE){
            return new top(inflater.inflate(R.layout.topic,parent,false));
        }else if(viewType == s_TYPE){
            return new headView(inflater.inflate(R.layout.head,parent,false));
        }
        return new PeoPoem(inflater.inflate(R.layout.people,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof top){
            top view = (top) holder;
            List<String> strings = new ArrayList<>();
            strings.add(list.get(0).getPoem());
            strings.add(list.get(1).getPoem());
            strings.add(list.get(2).getPoem());
            recycAdapter adapter = new recycAdapter(context,strings);
            view.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            view.recyclerView.setAdapter(adapter);
        }else if (holder instanceof PeoPoem) {
            PeoPoem viewHolder = (PeoPoem) holder;
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
            viewHolder.textView.setText(spannable);
            Spannable spannable2 = Spannable.Factory.getInstance().newSpannable(x.substring(0,16) + "\n" + x.substring(16,x.length()));
            viewHolder.textView2.setText(spannable2);
            viewHolder.imageView2.setImageResource(integerList.get(position % 7));
            viewHolder.textView3.setText("65");
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.aBoolean) {
                        viewHolder.imageView.setImageResource(R.drawable.unsupport);
                        viewHolder.aBoolean = false;
                        viewHolder.textView3.setText("66");
                    } else {
                        viewHolder.imageView.setImageResource(R.drawable.support);
                        viewHolder.aBoolean = true;
                        viewHolder.textView3.setText("65");
                    }
                }
            });
            viewHolder.imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentNet commentNet1 = new commentNet();
                    Handler handler = new Handler(Looper.myLooper()){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String fgh = (String) msg.obj;
                            Log.d("TAD", "handleMessage: " + fgh);
                            Gson gson = new Gson();
                            date datelist = gson.fromJson(fgh, date.class);
                            MyBottomSheetFragment myBottomSheetFragment = new MyBottomSheetFragment(integerList,datelist.getBase(),context);
                            myBottomSheetFragment.show(fragmentManager, "Sheet1");
                        }
                    };
                    commentNet1.getComment(handler);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list  == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int x = position % 7;
        return x;
    }

    public FRAdapter(List<base> list, Context context,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        integerList = new ArrayList<>();
        integerList.add(R.drawable.tx1);
        integerList.add(R.drawable.tx2);
        integerList.add(R.drawable.tx3);
        integerList.add(R.drawable.tx4);
        integerList.add(R.drawable.tx5);
        integerList.add(R.drawable.tx6);
        integerList.add(R.drawable.tx7);
        Log.d("TAD", "FRAdapter: " + integerList);
        this.fragmentManager = fragmentManager;
    }


    public class PeoPoem extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        TextView textView;
        TextView  textView2;
        TextView textView3;
        Boolean aBoolean = true;

        public PeoPoem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.figer);
            imageView3 = itemView.findViewById(R.id.messageP);
            imageView2 = itemView.findViewById(R.id.picture);
            textView = itemView.findViewById(R.id.people);
            textView2 = itemView.findViewById(R.id.peoplePoem);
            textView3 = itemView.findViewById(R.id.messageK);
        }

    }

    public class top extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public top(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler);
        }

    }

    public class headView extends RecyclerView.ViewHolder{
        Banner banner;
        public headView(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.head_View);
            List<Integer> integers = new ArrayList<>();
            integers.add(R.drawable.banner);
            integers.add(R.drawable.banner1);
            integers.add(R.drawable.banner2);
            banner.setAdapter(new BannerImageAdapter<Integer>(integers) {
                @Override
                public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                    holder.imageView.setImageResource(data);
                }
            });
            banner.isAutoLoop(true);
            banner.setIndicator(new CircleIndicator(context));
            banner.setScrollBarFadeDuration(1000);
            banner.setIndicatorSelectedColor(Color.GREEN);
            banner.start();
        }
    }
}
