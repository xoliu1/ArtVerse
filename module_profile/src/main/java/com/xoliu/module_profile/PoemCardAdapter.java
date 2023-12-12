package com.xoliu.module_profile;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.exoplayer2.C;

import java.util.List;

import db.AppDatabase;
import db.bean.PoemCard;

public class PoemCardAdapter extends RecyclerView.Adapter<PoemCardAdapter.ViewHolder> {
    private List<PoemCard> poemCardList;

    public PoemCardAdapter(List<PoemCard> poemCardList) {
        this.poemCardList = poemCardList;
    }

    @Override
    public int getItemCount() {
        return poemCardList.size();
    }

    @NonNull
    @Override
    public PoemCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建一个新的 ViewHolder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.profile_poem_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemCardAdapter.ViewHolder holder, int position) {
        // 绑定数据到 ViewHolder
        PoemCard poemCard = poemCardList.get(position);
        holder.content.setText(poemCard.getPoemContext());
        holder.author.setText(poemCard.getPoemAuthor());
        holder.mainView.setOnLongClickListener(v -> {
            alertClick(v, poemCard);
            return true;
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        CardView mainView;
        TextView author;
        TextView content;
        public ViewHolder(@NonNull View v) {
            super(v);
            author = (TextView)v.findViewById(R.id.profile_poem_author);
            content = (TextView) v.findViewById(R.id.profile_poem_context);
            mainView = (CardView) v.findViewById(R.id.profile_poem_cardView);
        }
    }


    public void alertClick(View v,PoemCard poemCard) {
        //创建 一个提示对话框的构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("");//设置弹出对话框的标题
        builder.setMessage("确定不同感该词文吗？");//设置弹出对话框的内容
        builder.setCancelable(false);//能否被取消
        //正面的按钮（肯定）
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = Room.databaseBuilder(builder.getContext(), AppDatabase.class, "PoemCards").build();
                        db.poemCardDao().delete(poemCard);
                    }
                }).start();
                dialog.cancel();
            }
        });
        //反面的按钮（否定）
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
