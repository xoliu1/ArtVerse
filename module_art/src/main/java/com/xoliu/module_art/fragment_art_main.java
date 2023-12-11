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
        mFragments.add(new ArtCard(R.drawable.view_of_saint_mammes,"Alfred Sisley "," View of Saint-Mammes"," View of Saint-Mammes /圣马梅斯景观","Alfred Sisley / 阿尔弗莱德·西斯菜(英国1839-1899)",
                "c.1880","布面油画","54.61x73.98 cm",
                "一起看看印象派笔下的河滨风光吧。\n\n" +
                "这幅画取景于圣马梅斯，一座处于塞纳河及卢万河交汇处的港口城市。1880年，被誉为[最纯粹的印象派画家]的阿尔弗菜德·西斯莱移居此地，每天从相似的地点描绘不同时间的圣马梅斯风景变化。\n\n" +
                "这幅画便是西斯莱圣马梅斯系列作品之一。画面上半部分是广阔的天空，下半部分是波光熬熬的河面，河面上停泊着小船，河岸上排列着房屋。西斯莱用明亮的色调和密集的笔触，为我们展示这座港口城市的绚丽风景和生机活力。"));
        mFragments.add(new ArtCard(R.drawable.soap_bubbles,"Jean Siméon Chardin","肥皂泡","Soap Bubbles/肥皂泡"," Jean Siméon Chardin (法国, 巴黎 1699–1779)",
                "ca. 1733–34","布面油画","24 x 24 7/8 in. (61 x 63.2 cm)",
                "一个年轻人从一杯肥皂水中吹出一个气泡，其彩虹般半透明的表面被阳光照射。一个孩子急切地凝视着窗台，没有打破沉默的专注气氛。\n" +
                        "\n" +
                        "在早期北欧绘画中，这一主题传达了关于生命短暂性的道德信息；然而，夏丁的画布将语域从寓言转移到了日常。Chardin因其通过粗糙而精细的油漆应用实现的奇迹般的表面效果而闻名，如中心人物的夹克、头发、前额和手。他复制了他最成功的作品：《肥皂泡》的后期版本属于洛杉矶县艺术博物馆和华盛顿国家美术馆。这幅画于1944年被纳粹从巴黎的简·曼海姆手中没收，并被非法购买给元首博物馆。在与荷兰艺术财产基金会达成协议后，法国皇家美术馆将其归还法国，并于1948年或之后归还。" +
                        "\n" +
                        "\n" +
                        "尽管Chardin在整个职业生涯中都是皇家雕塑学院的成员，但他没有在皇家雕塑学院学习，也没有接受过从模型中绘画的传统学术训练。他没有为自己的画布做准备研究，根据当代的报道，他在画布上工作缓慢而努力。他早期的静物画主要包括无生命的物体：蔬菜和水果、死猎物、狩猎设备、餐桌和厨具。因此，可以理解的是，一开始他不确定是否要画人像，甚至不得不被他的朋友、肖像画家雅克·阿维德（1702-1766）取笑才这么做。" +
                        "\n" +
                        "\n" +
                        "据经销商兼收藏家Jean-Pierre Mariette在案发约十五年后写道，Chardin的第一张肖像画显示了一个吹泡泡的年轻人的头，这是从模型上研究的。18世纪的法国人对17世纪荷兰艺术的品味，其中充斥着肥皂泡和其他关于死亡的暗示，可能影响了夏丁的选择，或者他可能看到了荷兰艺术家卡斯帕·内舍尔（1639？-1684）的一幅同一主题的画，据报道这幅画属于阿维德。然而，假设1733年的日期读得正确，《封信的女人》（柏林夏洛滕堡城堡）和《洗衣女工》（斯德哥尔摩国家博物馆）可能更早。柏林照片中的仆人似乎是《大都会报》吹泡泡的年轻版。斯德哥尔摩照片中的一个小人物是一个小男孩，全身坐着，全神贯注地凝视着肥皂泡。" +
                        "\n" +
                        "\n" +
                        "1739年，夏丁在巴黎沙龙展出了一个版本的肥皂泡，但哪个版本呢？答案可能是大都会博物馆、洛杉矶县艺术博物馆和华盛顿国家美术馆这三个博物馆中没有一个幸存下来。两个是水平的，一个在国家美术馆，是垂直的，它们之间略有不同。没有注明日期。华盛顿的这幅画被放大了，而这幅画被剪掉了。尽管华盛顿的画是一幅直立的画，但它与Pierre Filloeul（1696年-1754年之后）在1739年展出的作品后肯定制作的版画并不完全匹配，而这幅作品被认为已经失踪。目前画布的粗笔表明，它是三幅幸存下来的画布中最早的一幅，所有这些画布都可能追溯到1733年或1734年。虽然很可能，但不能肯定大都会博物馆的画作最初有一个吊坠，也不能肯定它直到1779年才属于路易斯·弗朗索瓦·特劳德，因为所有权的历史即使不是不可能，也很难理清。"));

        mFragments.add(new ArtCard(R.drawable.the_virgin_adoring_the_host,"Jean Auguste Dominique Ingres","主持的圣女玛利亚"," The Virgin Adoring the Host/主持的圣女玛利亚","Jean Auguste Dominique Ingres (法国·蒙托邦 1780–1867 巴黎)",
                "1852","布面油画","15 7/8 x 12 7/8 in. (40.3 x 32.7 cm)",
                "英格尔斯制作了这幅小而珠宝般的虔诚之画，作为送给朋友路易丝·马科特的礼物；她把这位艺术家介绍给了戴尔芬·拉梅尔，他于1852年与她结婚。圣母出现在祭坛桌子后面，两侧是两位崇拜圣餐的圣徒。这幅作品向为宗教奉献而创作的亲密绘画的悠久传统致敬，尤其是拉斐尔从15世纪初开始的艺术。\n" +
                        "\n" +
                        "这幅《主持的圣母玛利亚》是画家线条美的代表作之一。安格尔说““线条就是一切，色彩是空虚的”，“色彩是绘画的装饰手段，线条为艺术的本质”，这是他在艺术创作中坚定不移的美学观。安格尔在艺术上不仅是西方正统学院的范本，对印象主义的德加、雷若阿，甚至对毕加索也有影响。他的艺术可通过拉菲尔上溯到古代希腊，可以说他是描绘女性美的集大成者，后人无人能望其项背。\n" +
                        "\n" +
                        "让·奥古斯特·多米尼克·安格尔，是继大卫之后法国新古典主义画派的最后代表。安格尔出生于法国西南部，从小就受到良好的艺术熏陶，在美术和音乐方面都颇有天赋。安格尔17岁到大卫画室学习，得到真传。两年后考入美术学院，获罗马大奖。5年后去罗马，把拉斐尔作为毕生崇拜的对象。其作品工整细致，用色鲜明和谐。曾两次去意大利长时间研究古典美术；在本国曾任巴黎艺术学院的教授多年。安格尔的声誉如日中天时，也正是古典主义面临终结，浪漫主义崛起的时代，他和新生的浪漫主义代表人德拉克洛瓦之间发生许多次辩论，浪漫主义强调色彩的运用，古典主义则强调轮廓的完整和构图的严谨，安格尔把持的美术学院对新生的各种画风嗤之以鼻，形成学院派风格。"));


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}