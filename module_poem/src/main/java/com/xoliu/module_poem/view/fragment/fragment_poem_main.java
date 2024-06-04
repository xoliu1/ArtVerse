package com.xoliu.module_poem.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.sdsmdg.tastytoast.TastyToast;
import com.xoliu.module_poem.R;
import com.xoliu.module_poem.databinding.FragmentPoemMainBinding;
import com.xoliu.module_poem.view.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import Transformer.FadeInOutPageTransformer;


/***
 * 诗句界面的主界面
 * @author xoliu
 * @create 23-11-20
 **/

@Route(path = "/poem/fragment")
public class fragment_poem_main extends Fragment {

    //ViewPager的集合
    private List<fragment_viewpager_item> fragmentList;


    private ViewPager2 viewPager2;
    private int currentItemPosition = 0;


    public fragment_poem_main() {
        // Required empty public constructor
    }

    public static fragment_poem_main newInstance() {
        fragment_poem_main fragment = new fragment_poem_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    /***
     * 初始化ViewPager2
     *
     * @return void
     * @author xoliu
     * @create 23-11-20
     **/
    private void initViewPager() {
        fragmentList = new ArrayList<>();
        Random r = new Random();
        fragmentList.add(new fragment_viewpager_item(
                "人生只似风前絮，欢也零星，悲也零星，都做连江点点萍。",
                " - 王国维《采桑子》-",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));
        fragmentList.add(new fragment_viewpager_item(
                "应是天仙狂醉，乱把白云揉碎。",
                " - 李白 《清平乐·画堂晨起》 -",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));
        fragmentList.add(new fragment_viewpager_item(
                "劝君莫惜金缕衣，劝君惜取少年时",
                " - 无名氏 《金缕衣》 -",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));
        fragmentList.add(new fragment_viewpager_item(
                "惊觉相思不露，原来只因已入骨。    ",
                " - 汤显祖《牡丹亭》 -",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));
        fragmentList.add(new fragment_viewpager_item(
                "我醉欲眠卿且去，明朝有意抱琴来。",
                " - 李白 《山中与幽人对酌》-",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));
        fragmentList.add(new fragment_viewpager_item(
                "当时年少春衫薄。骑马倚斜桥，满楼红袖招。",
                " - 韦庄 《菩萨蛮》-",
                r.nextInt(20),
                r.nextInt(70),
                r.nextInt(300)));

        //关键-设置缓存数量
        viewPager2.setOffscreenPageLimit(5);

        //报错原因解决：
        viewPager2.setSaveEnabled(false);
        //设置动画切换效果
        viewPager2.setPageTransformer(new FadeInOutPageTransformer());

        viewPager2.setAdapter(new CardAdapter(this, fragmentList));
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentPoemMainBinding binding = FragmentPoemMainBinding.inflate(inflater);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);

        // Enable the Up button
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set the toolbar navigation click listener
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button press
                getActivity().onBackPressed();
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewpager);
        initViewPager();

        view.findViewById(R.id.poemCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/fenlei/main").navigation();
            }
        });


        //设置刷新头
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.poemRefreshLayout);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()));
        //在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            Random rand = new Random();
            for (int i = 0; i < 5; i++) {
                fragmentList.add(new fragment_viewpager_item(
                        "1",
                        " 1",
                        rand.nextInt(20),
                        rand.nextInt(70),
                        rand.nextInt(300)));
            }

            Objects.requireNonNull(viewPager2.getAdapter()).notifyDataSetChanged();
            viewPager2.setCurrentItem(fragmentList.size() - 5,true);
            refreshLayout1.finishRefresh();//执行完毕进行结束
            TastyToast.makeText(getContext(), "已为您加载出新的字句！", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
        });

        Glide.with(this).load(R.drawable.temp5).into((ImageView) view.findViewById(R.id.ImageView1));
        Glide.with(this).load(R.drawable.temp1).into((ImageView) view.findViewById(R.id.ImageView2));
        Glide.with(this).load(R.drawable.temp7).into((ImageView) view.findViewById(R.id.ImageView3));

    }
}