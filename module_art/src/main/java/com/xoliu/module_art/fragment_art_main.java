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

import Transformer.viewpager1.VerticalStackTransformer;

@Route(path = "/art/main")
public class fragment_art_main extends Fragment {

    private ArtCard mArtCard;
    private List<String> mFragments;
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
        mFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mFragments.add(String.valueOf(i));
        }
        mArtCard = new ArtCard( mFragments,getContext());
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
        binding.artViewPager2.setAdapter(mArtCard);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}