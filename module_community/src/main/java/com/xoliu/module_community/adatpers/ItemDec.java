package com.xoliu.module_community.adatpers;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDec extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = 7;
        outRect.top = 7;
        outRect.left = 7;
        outRect.right = 7;
    }
}
