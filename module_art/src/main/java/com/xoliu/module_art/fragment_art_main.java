package com.xoliu.module_art;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_art.databinding.FragmentArtMainBinding;

import java.util.ArrayList;
import java.util.List;

import Transformer.VerticalStackTransformer;

@Route(path = "/art/main")
public class fragment_art_main extends Fragment {

    private ArtCardAdapter mArtCardAdapter;
    private List<ArtCard> mFragments;
    FragmentArtMainBinding binding;

    public fragment_art_main() {

    }


    public static fragment_art_main newInstance() {
        fragment_art_main fragment = new fragment_art_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArtMainBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initData();
        initViewPager();



    }



    private void initViewPager(){
        mArtCardAdapter = new ArtCardAdapter( mFragments,getContext());
        //设置viewpager的方向为竖直
        binding.artViewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        //设置limit
        binding.artViewPager2.setOffscreenPageLimit(6);
        //设置transformer
        binding.artViewPager2.setPageTransformer(new VerticalStackTransformer(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                // 如果设置了reverseDrawingOrder为true，则将绘制顺序反转
                if (true) {
                    // 获取当前item在列表中的位置
                    int position = parent.getChildAdapterPosition(view);

                    // 计算反转后的绘制顺序
                    int reverseOrder = parent.getAdapter().getItemCount() - position - 1;

                    // 将计算后的绘制顺序应用到item的绘制顺序中
                    ViewCompat.setZ(view, reverseOrder);
                }
            }
        };
        binding.artViewPager2.addItemDecoration(itemDecoration);
        binding.artViewPager2.setAdapter(mArtCardAdapter);

    }

    private void initData(){
        mFragments = new ArrayList<>();
        //手动添加死数据
        mFragments.add(new ArtCard(R.drawable.view_of_saint_mammes," View of Saint-Mammes","Alfred Sisley "," View of Saint-Mammes /圣马梅斯景观","Alfred Sisley / 阿尔弗莱德·西斯菜(英国1839-1899)","c.1880","布面油画","54.61x73.98 cm","一起看看印象派笔下的河滨风光吧。\n\n" +
                "这幅画取景于圣马梅斯，一座处于塞纳河及卢万河交汇处的港口城市。1880年，被誉为[最纯粹的印象派画家]的阿尔弗菜德·西斯莱移居此地，每天从相似的地点描绘不同时间的圣马梅斯风景变化。\n\n" +
                "这幅画便是西斯莱圣马梅斯系列作品之一。画面上半部分是广阔的天空，下半部分是波光熬熬的河面，河面上停泊着小船，河岸上排列着房屋。西斯莱用明亮的色调和密集的笔触，为我们展示这座港口城市的绚丽风景和生机活力。"));
        mFragments.add(new ArtCard());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}