package com.xoliu.module_community.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.Topic;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private Context context;
    private Topic topic;

    private String[] strings;

    public TopicAdapter(Context context, Topic dataItemList) {
        this.context = context;
        this.topic = dataItemList;
        this.strings = new String[6];
        addStr();
    }



    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_item, parent, false);
        return new TopicViewHolder(view);
    }

    public void updateData(Topic newTopic) {
        this.topic = newTopic;
        notifyDataSetChanged(); // 通知适配器数据已改变，刷新列表
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic.DataItem item = topic.getData().get(position);
        holder.topicTextView.setText(item.getTopic());
        holder.cardView.setOnClickListener(v -> {
            showDialog(strings[position]);
        });
    }

    @Override
    public int getItemCount() {
        if (topic == null || topic.getData() == null){
            return 0;
        }
        Log.d("TAG", "getItemCount: " + topic);
        return topic.getData().size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {

        TextView topicTextView;

        CardView cardView;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicTextView = itemView.findViewById(R.id.topic_text);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("详情");
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addStr() {
        strings[0] = "【4月春光诗会】绿茵诗社诚邀诗友和文艺爱好者参加4月春光诗会，让我们共赏花间一壶酒，感受诗香满园春。本次诗会定于4月10日在绿茵公园举行，届时将有诗歌朗诵、音乐表演和自由互动等精彩节目。欢迎各位诗人和文艺爱好者踊跃报名参加！\n" +
                "【活动详情】\n" +
                "日期：4月10日\n" +
                "时间：下午2点至5点\n" +
                "地点：绿茵公园\n" +
                "主办：绿茵诗社\n" +
                "【报名方式】请发送邮件至greenpoetry@sample.com，邮件主题注明“4月春光诗会报名”，并在正文中注明您的姓名、联系方式以及是否愿意参与诗歌朗诵或其他节目表演。报名截止日期为4月5日，名额有限，报名从速！\n" +
                "诚邀各位朋友一同共赴春日诗会，让我们在春风和煦、鲜花怒放的季节，共享诗意盎然的美好时光。";

        strings[1] = "【端午吟诗大赛】江城文学社诚邀诗人和文学爱好者共同参与端午吟诗大赛，让我们在龙舟竞渡的热闹场面中，感受诗韵飞扬的美妙氛围。本次大赛定于6月3日，在江边文化广场举行，届时将有诗歌朗诵、诗歌创作比赛以及文学交流等精彩活动。欢迎各位诗人和文学爱好者踊跃报名参加！\n" +
                "【活动详情】\n" +
                "日期：6月3日\n" +
                "时间：上午10点至下午4点\n" +
                "地点：江边文化广场\n" +
                "主办：江城文学社\n" +
                "【报名方式】请发送邮件至rivercityliterature@sample.com，邮件主题注明“端午吟诗大赛报名”，并在正文中注明您的姓名、联系方式以及是否愿意参与诗歌创作比赛或诗歌朗诵。报名截止日期为5月28日，名额有限，报名从速！\n" +
                "诚邀各位文学爱好者一同共度端午佳节，让我们在诗歌的海洋中畅游，感受文学的魅力。";

        strings[2] = "【中秋月夜诗会】明月阁文化中心热忱邀请各位诗人和文艺爱好者共度中秋佳节，让我们在月圆人团圆的美好时刻，共享诗语共此时的浪漫氛围。本次诗会将于9月12日晚在古城墙上举行，届时将有诗歌朗诵、音乐表演和赏月活动等精彩节目。欢迎各位踊跃报名，共同书写一段别具意义的中秋诗文篇章。\n" +
                "【活动详情】\n" +
                "日期：9月12日\n" +
                "时间：傍晚开始\n" +
                "地点：古城墙上\n" +
                "主办：明月阁文化中心\n" +
                "【报名方式】有意参加者请致电明月阁文化中心预留名额，联系电话：010-12345678。报名截止日期为9月8日，名额有限，敬请尽早报名！\n" +
                "盼望各位文友和诗人们齐聚一堂，共享中秋月夜的美好，用心畅谈，共赏月光，共话诗意悠悠。";

        strings[3] = "【寒露诗书会】文学爱好者联盟热情邀请您参加寒露诗书会，让我们在秋意浓郁的季节里，共享诗意的深沉与浓郁。本次诗书会将于10月8日在枫林山居举行，届时将有诗歌朗诵、书法展示、文学讨论等多种活动。欢迎所有对文学感兴趣的朋友加入我们，共同感受秋天的韵味，共话诗书情缘。\n" +
                "【活动详情】\n" +
                "日期：10月8日\n" +
                "时间：上午9点至下午5点\n" +
                "地点：枫林山居\n" +
                "主办：文学爱好者联盟\n" +
                "【报名方式】请发送邮件至literaryalliance@sample.com，邮件主题注明“寒露诗书会报名”，并在正文中注明您的姓名、联系方式以及是否愿意参与诗歌朗诵或其他活动。报名截止日期为10月5日，名额有限，报名从速！\n" +
                "期待与您共同领略秋天的诗意风情，共享文学的盛宴！";

        strings[4] = "【初雪诗韵沙龙】白雪文学社诚挚邀请您共赏初雪诗韵沙龙，让我们在雪落无声的寒冬里，以诗篇寄托情怀。本次活动定于12月1日在雪松图书馆举行，届时将有诗歌朗诵、文学讨论、读书分享等精彩内容。欢迎各界文学爱好者踊跃参与，共同感受初雪的美妙，共享文学的情怀。\n" +
                "【活动详情】\n" +
                "日期：12月1日\n" +
                "时间：下午2点至晚上8点\n" +
                "地点：雪松图书馆\n" +
                "主办：白雪文学社\n" +
                "【报名方式】请发送邮件至whitelit@sample.com，邮件主题注明“初雪诗韵沙龙报名”，并在正文中注明您的姓名、联系方式以及是否愿意参与诗歌朗诵或其他活动。报名截止日期为11月28日，名额有限，敬请尽早报名！\n" +
                "期待与您共度初雪之夜，共享诗意的温暖与美好。";


        strings[5] = "【春节古风诗咏】传统文艺复兴协会谨诚邀请您参加春节古风诗咏活动，让我们在岁末年初之际，共同颂扬华章，品味古风诗意。本次活动将于1月25日在古都大剧院举行，届时将有古风诗歌朗诵、传统音乐演奏、古典舞蹈表演等精彩节目。欢迎各界文艺爱好者踊跃参与，共同迎接新春的到来，共享古风诗韵的美好时光。\n" +
                "【活动详情】\n" +
                "日期：1月25日\n" +
                "时间：下午3点至晚上9点\n" +
                "地点：古都大剧院\n" +
                "主办：传统文艺复兴协会\n" +
                "【报名方式】请发送邮件至traditionalarts@sample.com，邮件主题注明“春节古风诗咏活动报名”，并在正文中注明您的姓名、联系方式以及是否愿意参与诗歌朗诵或其他表演形式。报名截止日期为1月20日，名额有限，敬请尽早报名！\n" +
                "期待与您共同颂扬春节的美好，共享古风诗咏的雅致氛围。";
    }



}
