package com.xoliu.module_community.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.Topic;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private Context context;
    private Topic topic;

    public TopicAdapter(Context context, Topic dataItemList) {
        this.context = context;
        this.topic = dataItemList;
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

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicTextView = itemView.findViewById(R.id.topic_text);
        }
    }
}
