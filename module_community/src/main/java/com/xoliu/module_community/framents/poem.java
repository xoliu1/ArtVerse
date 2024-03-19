package com.xoliu.module_community.framents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;
import com.xoliu.module_community.adatpers.ItemDec;
import com.xoliu.module_community.adatpers.itemTwo;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class poem extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private Banner banner;

    private RecyclerView recyclerView;
    private List<Integer> data;

    private List<Integer> integerList;

    private List<String> stringList;

    private List<String> stringList2;


    public static poem newInstance(String param1, String param2) {
        poem fragment = new poem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner = view.findViewById(R.id.peomS);
        data = new ArrayList<>();
        stringList = new ArrayList<>();
        integerList = new ArrayList<>();
        stringList2 = new ArrayList<>();
        inDate(data);
        banner.setAdapter(new BannerImageAdapter<Integer>(data) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });
        banner.isAutoLoop(true);
        banner.setIndicator(new CircleIndicator(getActivity()));
        banner.setScrollBarFadeDuration(1000);
        banner.setIndicatorSelectedColor(Color.GREEN);
        banner.start();
        init(stringList);
        initop(integerList);
        recyclerView = view.findViewById(R.id.type);
        itemTwo pop = new itemTwo(getActivity(),integerList,stringList,stringList2);
        recyclerView.setAdapter(pop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new ItemDec());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider2));
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poem, container, false);
    }
    public void inDate(List<Integer> integerList){
        integerList.add(R.drawable.banner);
        integerList.add(R.drawable.banner1);
        integerList.add(R.drawable.banner2);
    }
    public void init(List<String> stringList){
        stringList.add("吴时兴\n生活点滴如诗画，岁月悠长映彩霞。\n晨起鸟鸣伴清风，夜来星辉映窗纱。");
        stringList.add("吴时兴\n亲朋相聚乐融融，知己相逢话无涯。\n生活平淡亦真趣，且将诗酒趁年华。");
        stringList.add("吴时兴\n花间漫步心自静，书海遨游意更佳。\n闲来品茗论古今，笑谈风云话桑麻。");
        stringList.add("吴时兴\n猫儿戏蝶过花丛，轻盈灵动步如风。\n碧眼炯炯似星辰，白毛如雪映日红");
        stringList.add("吴时兴\n有时娇憨惹人怜，有时独立显威风。\n猫儿百态皆可爱，伴我生活乐融融。");
        stringList.add("吴时兴\n秋风萧瑟夜微凉，月照孤窗影自长。\n远望星河思渺渺，近观花落叶纷扬。");
        stringList.add("吴时兴\n夜色渐深沉，孤灯照影单。\n思绪随云去，心事付月谈。");
        stringList.add("吴时兴\n心中感慨难言说，笔下诗情似水流。\n愿得此生无憾事，与君共度好时光。");
        stringList.add("吴时兴\n京口瓜洲一水间，钟山只隔数重山。\n春风又绿江南岸，明月何时照我还。");
        stringList.add("吴时兴\n对酒当歌，人生几何！\n譬如朝露，去日苦多。");
        stringList.add("吴时兴\n修奉贡献，臣节不隆。\n崇侯谗之，是以拘系。");
        stringList.add("吴时兴\n千锤万凿出深山，烈火焚烧若等闲。\n粉骨碎身浑不怕，要留清白在人间。");
        stringList.add("吴时兴\n春宵一刻值千金，花有清香月有阴。\n歌管楼台声细细，秋千院落夜沉沉");
        stringList.add("吴时兴\n月黑见渔灯，孤光一点萤。\n微微风簇浪，散作满河星");
    }
    public void initop(List<Integer> integerList){
        integerList.add(R.drawable.tx1);
        integerList.add(R.drawable.tx2);
        integerList.add(R.drawable.tx3);
        integerList.add(R.drawable.tx4);
        integerList.add(R.drawable.tx5);
        integerList.add(R.drawable.tx6);
        integerList.add(R.drawable.tx7);
        integerList.add(R.drawable.tx1);
        integerList.add(R.drawable.tx2);
        integerList.add(R.drawable.tx3);
        integerList.add(R.drawable.tx4);
        integerList.add(R.drawable.tx5);
        integerList.add(R.drawable.tx6);
        integerList.add(R.drawable.tx7);
    }
    public void initst(List<String> stringList){
       stringList.add("12345pooop67890");
    }
}