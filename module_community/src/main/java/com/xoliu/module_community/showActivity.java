package com.xoliu.module_community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xoliu.module_community.Adapter.personAdapter;
import com.xoliu.module_community.Present.Fromsign;
import com.xoliu.module_community.databinding.ActivityShowBinding;
import com.xoliu.module_community.mModel.date;

@Route(path = "/showActivity/main")
public class showActivity extends AppCompatActivity {
    private ActivityShowBinding binding;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView textView;
    public static Integer name;
    public static String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Fromsign fromsign = new Fromsign();
        recyclerView = binding.battle;
        textView = binding.jklove;
        imageView = binding.itemPeole;
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 3){
                    String fgh = (String) msg.obj;
                    date datelist;
                    Gson gson = new Gson();
                    datelist = gson.fromJson(fgh, date.class);
                    Log.d("10086", "handleMessage: " + datelist);
                    personAdapter frAdapter = new personAdapter(getApplicationContext(),name,string,datelist.getBase(),getSupportFragmentManager());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(frAdapter);
                    imageView.setImageResource(name);
                    Spannable spannable = Spannable.Factory.getInstance().newSpannable(string);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#46a2ff"));
                    StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
                    RelativeSizeSpan relativeSizeSpanBig = new RelativeSizeSpan(1.6f);
                    spannable.setSpan(foregroundColorSpan,0,string.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    spannable.setSpan(relativeSizeSpanBig,0,string.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    spannable.setSpan(styleSpan,0,string.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    textView.setText(spannable);
                }
            }
        };
        fromsign.getPeo(handler);
    }
}