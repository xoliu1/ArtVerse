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

        mFragments.add(new ArtCard(R.drawable.thedispatchofthe_messenger,"François Boucher","信使的派遣","The Dispatch of the Messenger / 信使的派遣","François Boucher (法国, 巴黎 1703–1770 巴黎)",
                " 1765","布面油画","Oval, 12 5/8 x 10 1/2 in. (32.1 x 26.7 cm)",
                "这幅画《The Dispatch of the Messenger》展现了鸽子将情书送给牧羊女的情形。\n" +
                        "\n" +
                        "画中的牧羊人以优美的姿态坐在林中的池塘边，与之为伴的是姿态优美的小动物们。画面左上方则是栖息在废墟中的鸽子。布歇对周围郁郁葱葱的树木也进行了精心设计，使其与牧羊人的姿势相呼应，同时使画幅的椭圆形更加突出。\n" +
                        "\n" +
                        "在1765年的沙龙上，这幅小画布与其他三幅作品一起展出。在四个系列的开场白中，牧羊人用一条蓝色的长丝带将他的情书系在一只白鸽身上。第二个椭圆形描绘了鸽子的到来，鸽子把信带给牧羊女，牧羊女靠在树上；雅克·菲尔敏·博瓦莱特（1731-1797）在1769年的沙龙上展出了两个椭圆形之后的黑色粉笔画，这两个椭圆形是为雕刻做准备的。在第三幅和第四幅长方形的画布上，牧羊女给她的心腹读了这封信，这对恋人相遇了。\n" +
                        "\n" +
                        "显然，其他三幅画都消失了，只有该系列的当前作品幸存下来。尽管总的来说，作家兼评论家丹尼斯·狄德罗（1713-1784）谴责田园风格的虚伪是他的一贯做法，但他称赞这幅画及其同伴是“一首迷人的小诗”。树叶蓬松的树木被精心安排，以呼应牧羊人的姿势，并强调设计的椭圆形。他的葫芦形水瓶和左上角背景中的一对鸽子是典型的暗示。"));


        mFragments.add(new ArtCard(R.drawable.view_of_saint_mammes,"Jacob Vosmaer","花与花瓶"," A Vase with Flowers / 花与花瓶"," Jacob Vosmaer (荷兰·代尔夫特理工大学 ca. 1584–1641 Delft)",
                "probably 1613","木板油画","33 1/2 x 24 5/8 in. (85.1 x 62.5 cm)",
                "在荷兰的这幅早期花卉画中，一束稀有的花朵被安排在一个陶罐中，陶罐被设置在一个大的石头壁龛中。面板的两侧都经过了修剪，顶部被削减了近四分之一的原始高度（见Mahon 1993/94）。这张照片的设计最初与Vosmaer的《石头壁龛里有贝母的花朵静物》（私人收藏，阿姆斯特丹）非常相似，它显示了花盆周围有充足的空间，壁龛的弯曲墙壁在阳光下延伸到右侧墙壁的缺口边缘。这种正面元素，底部破旧的石门槛，以及花盆上的花朵和背景上的花束投射出的强烈阴影，营造出一种错觉空间感，这种错觉空间感在当代荷兰或佛兰德花卉图片中罕见。\n" +
                        "\n" +
                        "相比之下，在大都会艺术博物馆的画作中，花朵几乎占据了画面的上半部分，顶部的皇家王冠（参照阿姆斯特丹的画作修复）突然被边框切割，所有这些都倾向于强调面板的表面，而不是空间的凹陷。为了解决这个问题，当照片上有一个17世纪的红色龟壳框架时，这是一种必要的美德，它将五颜六色的整体作为一个装饰整体。但在正常观看距离下的效果肯定与艺术家的意图大不相同。在这一时期的国内室内，阿姆斯特丹的面板，其真人大小的图案被持续的光线淹没，一定给人留下了一种真正的花束填充墙壁壁龛的印象。\n" +
                        "\n" +
                        "尽管阿姆斯特丹和纽约的画作中的排列很难完全相同，但许多花朵在相同的位置重复出现，例如花束底部的五朵最大的花朵，以及顶部中心的火焰郁金香和皇冠（一种贝母）。在这张照片中，一只蜥蜴占据了右下角，一片不同的花瓣在门槛边缘弯曲，左边一根倒下的树枝取代了阿姆斯特丹画中的老鼠。\n" +
                        "\n" +
                        "这两张照片的木支架比它们所承载的图像更紧密地联系在一起。X射线照片显示，热带木材的中心板——约24英寸（61厘米）宽的垂直木板——在纹理和结节上精确匹配。这意味着将一块约1/2英寸（1.27厘米）厚的单板分割成两块薄板。在中央板的侧面添加了窄橡木条，制成每一块面板。在后来的某个日期（也许是在18世纪），纽约的面板几乎被切割到右侧橡木条和中央面板的连接处，左侧稍微被切割到热带木材中，顶部被切割了10英寸（25.4厘米）。\n" +
                        "\n" +
                        "这两幅密切相关的作品是在同时制作的木板上绘制的，这表明这些图片一定是同时或连续绘制的。在最近的保护之前，阿姆斯特丹小组的日期据说是1618年，但现在最有可能的读数（根据负责保护的人的说法）似乎是1613年。纽约画作上日期的最后一位数字过去读作5（从1872年的德坎普斯开始，他把支架误认为是铜）。这个数字现在难以辨认。数字3和5刻在17世纪的荷兰画上，经常被现代观众误读。无论如何，这两张照片很可能都是在1613年左右完成的。\n" +
                        "\n" +
                        "这两幅画并排悬挂在2001年威猛和代尔夫特学校展览的纽约会场。它们在风格和质量上看起来完全一致，考虑到它们的保存状态非常不同。Liedtke（2001）认为，这些画可能是作为吊坠制作的，考虑到它们的虚拟壁龛在建筑整体中会相互补充，而且特制的面板可能是为特定的赞助人设计的。然而，在没有文献的情况下，Vosmaer和那个时期的其他静物画家一样，只是以有效的方式创作了一个成功设计的版本，这似乎同样或更合理。如果面板被视为一对，那么花束的种类以及重锅（可能是德国的瓷器）的类型或外观都会有更大的变化。\n" +
                        "\n" +
                        "画家可能从未见过这里描绘的一些花朵，但会依赖于印刷品来源，如伦伯特·多登斯的Stirpium historiae pemptades sex（安特卫普，1583年）或其他艺术家的作品。Dodoens展示了一种贝母，并描述了七年前他在维也纳皇帝马克西米利安的花园里看到的另一种（从“东部”进口）。其他的花是在荷兰种植的，或者说，条纹和火焰郁金香和粉色双蜀葵（部分保存在左上角）是最近才从国外引进的。Vosmaer还包括玫瑰、鸢尾花、一种红色海葵（就在底部中心火焰郁金香上蝴蝶的左边），以及在它的左边，一种通常被称为蛇头的贝母（见图1）。一些花的颜色反映在花盆闪亮的表面上，白玫瑰在那里投下了强烈的阴影。\n" +
                        "\n" +
                        "在Vosmaer之前，其他艺术家也画出了这种构图。Roelant Savery（1576–1639），从1603年开始，在石龛中放置类似的花束，在窗台上放置蜥蜴、昆虫和掉落的花瓣，到1615年（如果不是更早的话），在更宽敞的环境中产生了更宽松的布置，而不是像《大都会博物馆》照片的原始状态那样。老雅克·德·盖恩也绘制了类似的作品。沃斯梅尔回忆起德盖恩相对流畅的处决方式。这两位艺术家分别住在代尔夫特和海牙，相距不超过一个小时（乘船或步行）。根据亨德里克·洪迪乌斯1599年设计的版画（维也纳阿尔贝蒂纳）判断，代尔夫特花卉画家埃利亚斯·弗胡斯特（死于1601年）可能对沃斯梅尔也很重要。花朵的多样性和开放式拱形构图的明显独创性（这显然预示着老Ambrosius Bosschaert[1573-1621]的设计）表明，Verhulst是代尔夫特和海牙地区的一位有影响力的人物。\n" +
                        "\n" +
                        "剪花和凿石让所有的东西都消失了，无论多么新鲜或耐用，但沃斯梅尔的绘画对同时代人来说，主要的兴趣不是象征意义，而是植物学，更广泛地说，是大自然的奇迹，上帝在那里创造了人类只能编目和模仿的形式。"));




        mFragments.add(new ArtCard(R.drawable.view_of_saint_mammes,"Alfred Sisley "," View of Saint-Mammes"," View of Saint-Mammes /圣马梅斯景观","Alfred Sisley / 阿尔弗莱德·西斯菜(英国1839-1899)",
                "c.1880","布面油画","54.61x73.98 cm",
                "一起看看印象派笔下的河滨风光吧。\n\n" +
                        "这幅画取景于圣马梅斯，一座处于塞纳河及卢万河交汇处的港口城市。1880年，被誉为[最纯粹的印象派画家]的阿尔弗菜德·西斯莱移居此地，每天从相似的地点描绘不同时间的圣马梅斯风景变化。\n\n" +
                        "这幅画便是西斯莱圣马梅斯系列作品之一。画面上半部分是广阔的天空，下半部分是波光熬熬的河面，河面上停泊着小船，河岸上排列着房屋。西斯莱用明亮的色调和密集的笔触，为我们展示这座港口城市的绚丽风景和生机活力。"));























    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}