package com.xoliu.module_poem.model.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * 评论的Bean类
 * @author xoliu
 * @create 23-11-21
 **/

public class commentItem {
    public String userIcon = "https://s2.loli.net/2024/05/27/9hqHXRxztVmTniQ.jpg";
    public String userName;
    public String commentTime;
    public String commentContent;
    List<String> userIcons = new ArrayList(){
        {
            add("https://s2.loli.net/2023/11/21/tNnmTBVdYS2lhZA.jpg");
            add("https://tvax1.sinaimg.cn/large/9bd9b167ly1g1p9b71tgdj20b40b4gmg.jpg");
            add("https://tvax3.sinaimg.cn/large/9bd9b167gy1g1p9ps0ruxj20b40b40t8.jpg");
            add("https://tvax4.sinaimg.cn/large/9bd9b167ly1g1p9q0dzdfj20b40b43z3.jpg");
            add("https://tvax2.sinaimg.cn/large/9bd9b167gy1fzjvs9a6h6j20b40b4t98.jpg");
            add("https://tvax2.sinaimg.cn/large/9bd9b167ly1fzjvsm8whcj20b40b4t9c.jpg");
            add("https://tvax2.sinaimg.cn/large/9bd9b167ly1fzjvt61vnxj20b40b4aaw.jpg");
            add("https://tvax1.sinaimg.cn/large/9bd9b167ly1g1p9vofvldj20b40b43yv.jpg");
        }
    };

    List<String> userNames = new ArrayList(){
        {
            add("风行云散");
            add("雨过天青");
            add("梦幻星辰");
            add("晨曦微露");
            add("夜幕低垂");
            add("烈火如歌");
            add("寒冰破晓");
            add("紫电青霜");
            add("碧海蓝天");
            add("红尘滚滚");
            add("剑指江湖");
            add("琴瑟和鸣");
            add("书墨飘香");
            add("画扇轻摆");
            add("飞雪连天");
            add("落花有意");
            add("流水无情");
            add("月落乌啼");
            add("繁星闪烁");
            add("风卷残云");
            add("雷霆万钧");
            add("四海为家");
            add("天涯海角");
            add("沧海桑田");
            add("心有灵犀");
            add("魂牵梦绕");
            add("一往情深");
            add("百炼成钢");
            add("千娇百媚");
            add("万里无云");
            add("岁月如歌");
            add("人生如梦");
            add("风华绝代");
            add("闭月羞花");
            add("沉鱼落雁");
            add("倾国倾城");
            add("侠肝义胆");
            add("铁血丹心");
            add("柔情蜜意");
            add("儿女情长");
            add("肝胆相照");
            add("志同道合");
            add("风雨无阻");
            add("雷厉风行");
            add("文质彬彬");
            add("武艺超群");
            add("温文尔雅");
            add("威震八方");
            add("幸福美满");
            add("吉祥如意");
        }
    };
    List<String> commentTimes = new ArrayList(){
        {
            add("2023-01-15");
            add("2022-12-31");
            add("2021-09-01");
            add("2023-11-08");
            add("2022-02-28");
            add("2024-08-12");
            add("2021-07-23");
            add("2023-04-19");
            add("2022-05-15");
            add("2023-07-06");
            add("2021-03-11");
            add("2022-11-22");
            add("2023-02-13");
            add("2021-06-18");
            add("2022-08-30");
            add("2023-10-16");
            add("2025-01-21");
            add("2024-07-09");
            add("2021-05-03");
            add("2022-12-10");
            add("2023-09-25");
            add("2024-11-05");
            add("2021-02-20");
            add("2023-08-12");
            add("2022-01-04");
            add("2021-10-28");
            add("2022-09-29");
        }
    };
    List<String> commentContents = new ArrayList(){
        {add("这首诗以其独特的意象和深邃的情感，展现了诗人对人生的独到见解。");
            add("诗歌中的每一个字句都充满了诗意，让人仿佛置身于一个美丽的梦境之中。");
            add("作者巧妙地运用了修辞手法，使得诗歌的表达更加生动有力。");
            add("这首诗的语言简练而富有内涵，读来令人回味无穷。");
            add("诗歌所传达的情感真挚而深沉，让人感受到了诗人的真挚情感。");
            add("这首诗的意象丰富多样，展现了诗人广阔的想象力和创造力。");
            add("作者对诗歌的节奏把控得恰到好处，使得整首诗读起来流畅而和谐。");
            add("诗歌中的描写细腻入微，让人能够深刻感受到诗人对细节的把握能力。");
            add("这首诗以其独特的视角和深刻的思考，给人留下了深刻的印象。");
            add("作者通过诗歌表达了对生活的热爱和对未来的憧憬，令人感到温暖而充满希望。");
            add("这首诗的构思巧妙，情感表达真挚，是一首不可多得的佳作。");
            add("诗歌中的意象和比喻贴切而生动，使得整首诗更加富有艺术感染力。");
            add("作者通过对自然景色的描绘，展现了对大自然的敬畏和热爱之情。");
            add("这首诗以其简洁明快的语言风格，吸引了读者的眼球和心灵。");
            add("诗歌中的情感表达含蓄而深沉，需要细细品味才能领略其中的韵味。");
            add("作者通过诗歌表达了对人性的思考和探索，引人深思。");
            add("这首诗的意境深远而辽阔，让人仿佛置身于一个广阔的天地之中。");
            add("诗歌中的每一个字句都充满了力量和激情，读来令人热血沸腾。");
            add("作者通过细腻的笔触，将情感表达得淋漓尽致，令人动容。");
            add("这首诗以其独特的风格和深刻的内涵，成为了诗坛上的一道亮丽风景线。");
            add("诗歌中的描写生动而形象，让人仿佛能够亲身感受到诗人的所见所感。");
            add("作者通过诗歌表达了对社会的关注和思考，体现了其社会责任感。");
            add("这首诗以其优美的语言和深刻的情感，赢得了读者的喜爱和赞誉。");
            add("诗歌中的情感表达真挚而动人，让人感受到了诗人的真诚和热情。");
            add("作者通过诗歌表达了对历史的回顾和思考，引人深思。");
            add("这首诗以其独特的构思和表达方式，让人眼前一亮。");
            add("诗歌中的意象新颖而独特，展现了诗人的创新精神和艺术追求。");
            add("作者通过诗歌表达了对友情的珍视和感恩之情，令人感动不已。");
            add("这首诗以其真挚的情感和深刻的思考，打动了读者的心灵。");
            add("诗歌中的描写细腻而生动，让人仿佛能够亲身感受到诗人的心境和情绪。");
            add("作者通过诗歌表达了对爱情的向往和追求，令人感到温馨而美好。");
            add("这首诗以其独特的视角和深刻的内涵，成为了人们心中的经典之作。");
            add("诗歌中的情感表达既热烈又深沉，展现了诗人的情感世界和内心世界。");
            add("作者通过诗歌表达了对生活的热爱和对未来的期待，充满了正能量和鼓舞人心的力量。");
            add("这首诗以其优美的语言和深刻的情感，成为了人们心中的瑰宝。");
            add("诗歌中的描写细腻而真实，让人仿佛能够身临其境地感受到诗人的所见所感。");
            add("作者通过诗歌表达了对自然的敬畏和赞美之情，展现了其深厚的生态意识。");
            add("这首诗以其真挚的情感和独特的表达方式，赢得了读者的广泛赞誉和喜爱。");
            add("诗歌中的情感表达既含蓄又深沉，需要细细品味才能领略其中的真谛。");
            add("作者通过诗歌表达了对人性的深刻洞察和理解，引人深思。");
            add("这首诗以其独特的魅力和深刻的内涵，成为了诗坛上的一股清流。");}
    };

    public commentItem(String userIcon, String userName, String commentTime, String commentContent) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
    }

    public commentItem(String userName, String commentTime, String commentContent) {
        this.userName = userName;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
    }

    public commentItem() {
    }



    public List<commentItem> getArray(int num){
        Random r = new Random();
        List<commentItem> c = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            c.add(new commentItem(userIcons.get(r.nextInt(userIcons.size())),userNames.get(r.nextInt(userNames.size())),commentTimes.get(r.nextInt(commentTimes.size())),commentContents.get(r.nextInt(commentTimes.size()))));
        }



        return c;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
