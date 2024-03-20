package com.xoliu.module_community.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.xoliu.module_community.R;
import com.xoliu.module_community.adatpers.ItemDec;
import com.xoliu.module_community.adatpers.itemTwo;

import java.util.ArrayList;
import java.util.List;


public class question extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private List<Integer> integerList;

    private List<String> stringList;

    private Toolbar toolbar;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView recyclerView;


    public static question newInstance(String param1, String param2) {
        question fragment = new question();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.tool);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle("桂花树下桂花猫，_________?");
        int color = getResources().getColor(R.color.gold);
        collapsingToolbarLayout.setExpandedTitleColor(color);
        collapsingToolbarLayout.setCollapsedTitleTextColor(color);
        recyclerView = view.findViewById(R.id.battle);
        integerList = new ArrayList<>();
        stringList = new ArrayList<>();
        initop(integerList);
        init(stringList);
        itemTwo itemTwo = new itemTwo(getActivity(),integerList,stringList);
        recyclerView.setAdapter(itemTwo);
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
        return inflater.inflate(R.layout.fragment_question, container, false);
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
    public void init(List<String> stringList){
        stringList.add("吴时兴\n桂花树下桂花猫，桂花梦里桂花糕。\n银须玉爪香环绕，醒来犹是小花幺。");
        stringList.add("吴时兴\n桂花树下桂花猫，八月中秋桂香飘。\n嫦娥不识狸儿娇，错抱玉兔上九霄。");
        stringList.add("吴时兴\n桂花树下桂花猫，满地金花暗香飘。\n平生何须常年少，一壶清茗躲尘嚣。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花散落随风飘。\n金背银身闲处卧，不知桂花满背梢");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花飘落狸儿娇。\n桂花梦中桂花酒，桂花满身暖阳照。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花香飘十里闹。\n家家都喜桂花糕，孩儿更爱桂花猫。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花梦里桂花糕。\n桂花去来桂花落，来年仍做桂花猫。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花梦里桂花香。\n盛秋丰时自悠悠，来年有望今时朝。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花落满狸儿身。\n儿童不识桂花香，错把狸儿当桂香。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花梦里狸儿喵。\n晚风吹来金花飘，几朵金花满背梢。");
        stringList.add("吴时兴\n桂花树下桂花猫，满地金花暗香飘。\n平生何须常年少，一壶清茗躲尘嚣。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花散落随风飘。\n金背银身闲处卧，不知桂花满背梢.");
        stringList.add("吴时兴\n桂花树下桂花猫，八月中秋桂香飘。\n嫦娥不识狸儿娇，错抱玉兔上九霄。");
        stringList.add("吴时兴\n桂花树下桂花猫，桂花梦里桂花糕。\n银须玉爪香环绕，醒来犹是小花幺。");
    }
}