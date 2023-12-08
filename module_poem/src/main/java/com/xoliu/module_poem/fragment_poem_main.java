package com.xoliu.module_poem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_poem.viewpager.CardAdapter;
import com.xoliu.module_poem.viewpager.fragment_viewpager_item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TransAnim.DepthPageTransformer;
import TransAnim.FadeInOutPageTransformer;
import TransAnim.FlipPageTransformer;
import TransAnim.ZoomOutPageTransformer;


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewpager);
        initViewPager();
    }
}