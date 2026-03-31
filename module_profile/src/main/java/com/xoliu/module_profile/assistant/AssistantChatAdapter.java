package com.xoliu.module_profile.assistant;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_profile.R;

import java.util.List;

/**
 * AI助手聊天消息适配器
 */
public class AssistantChatAdapter extends RecyclerView.Adapter<AssistantChatAdapter.ViewHolder> {

    private List<AssistantChatMsg> messages;
    private Context context;
    private int maxBubbleWidth;

    public AssistantChatAdapter(List<AssistantChatMsg> messages, Context context) {
        this.messages = messages;
        this.context = context;
        // 气泡最大宽度为屏幕宽度的75%
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        maxBubbleWidth = (int) (dm.widthPixels * 0.75f);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assistant_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssistantChatMsg msg = messages.get(position);
        if ("user".equals(msg.getRole())) {
            // 用户消息：显示右侧，隐藏左侧
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
            // 限制最大宽度
            holder.rightMsg.setMaxWidth(maxBubbleWidth);
        } else {
            // AI助手消息：显示左侧，隐藏右侧
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            // 限制最大宽度
            holder.leftMsg.setMaxWidth(maxBubbleWidth);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView leftMsg;
        TextView rightMsg;
        LinearLayout leftLayout;
        LinearLayout rightLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftMsg = itemView.findViewById(R.id.assistant_left_msg);
            rightMsg = itemView.findViewById(R.id.assistant_right_msg);
            leftLayout = itemView.findViewById(R.id.assistant_left_layout);
            rightLayout = itemView.findViewById(R.id.assistant_right_layout);
        }
    }
}
