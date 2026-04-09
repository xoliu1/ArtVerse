package com.xoliu.module_poem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoliu.module_poem.R;
import com.xoliu.module_poem.bean.FeiHuaMsg;

import java.util.List;

public class FeiHuaMsgAdapter extends RecyclerView.Adapter<FeiHuaMsgAdapter.ViewHolder> {

    private List<FeiHuaMsg> messages;
    private Context context;

    public FeiHuaMsgAdapter(List<FeiHuaMsg> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feihua_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeiHuaMsg msg = messages.get(position);

        // 先全部隐藏
        holder.layoutAi.setVisibility(View.GONE);
        holder.layoutPlayer.setVisibility(View.GONE);
        holder.tvSystemMsg.setVisibility(View.GONE);

        switch (msg.getType()) {
            case FeiHuaMsg.TYPE_AI:
                holder.layoutAi.setVisibility(View.VISIBLE);
                holder.tvAiMsg.setText(msg.getContent());
                holder.tvAiRound.setText("第 " + msg.getRound() + " 回合");
                break;
            case FeiHuaMsg.TYPE_PLAYER:
                holder.layoutPlayer.setVisibility(View.VISIBLE);
                holder.tvPlayerMsg.setText(msg.getContent());
                holder.tvPlayerRound.setText("第 " + msg.getRound() + " 回合");
                break;
            case FeiHuaMsg.TYPE_SYSTEM:
                holder.tvSystemMsg.setVisibility(View.VISIBLE);
                holder.tvSystemMsg.setText(msg.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutAi;
        LinearLayout layoutPlayer;
        TextView tvAiMsg, tvAiRound;
        TextView tvPlayerMsg, tvPlayerRound;
        TextView tvSystemMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutAi = itemView.findViewById(R.id.layout_ai);
            layoutPlayer = itemView.findViewById(R.id.layout_player);
            tvAiMsg = itemView.findViewById(R.id.tv_ai_msg);
            tvAiRound = itemView.findViewById(R.id.tv_ai_round);
            tvPlayerMsg = itemView.findViewById(R.id.tv_player_msg);
            tvPlayerRound = itemView.findViewById(R.id.tv_player_round);
            tvSystemMsg = itemView.findViewById(R.id.tv_system_msg);
        }
    }
}
