package com.xoliu.module_poem.comment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xoliu.module_poem.R;

import java.util.List;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {

    List<commentItem> comments;
    private Context mContext;

    public commentAdapter(List<commentItem> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_poem_comment_dialog_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        commentItem comment = comments.get(position);
        Glide.with(mContext).load(comment.userIcon).into(holder.userIcon);
        holder.commentTime.setText(comment.commentTime);
        holder.userName.setText(comment.userName);
        holder.commentContent.setText(comment.commentContent);

        //设置点击事件
        holder.dianzan.setOnClickListener(new View.OnClickListener() {
            boolean IsClicked = false;
            @Override
            public void onClick(View v) {
                if (!IsClicked) {
                    holder.dianzan.setForeground(ContextCompat.getDrawable(mContext, R.drawable.dianzan_checked));
                    IsClicked = true;
                } else {
                    holder.dianzan.setForeground(ContextCompat.getDrawable(mContext, R.drawable.dianzan_unchecked));
                    IsClicked = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageFilterView userIcon;
        TextView userName;
        TextView commentTime;
        TextView commentContent;

        ImageButton dianzan;


        public ViewHolder(@NonNull View v) {
            super(v);
            this.userIcon = v.findViewById(R.id.comment_userIcon);
            this.userName = v.findViewById(R.id.comment_username);
            this.commentTime = v.findViewById(R.id.comment_time);
            this.commentContent = v.findViewById(R.id.comment_content);
            this.dianzan = v.findViewById(R.id.btn_dianzan);
        }
    }
}
