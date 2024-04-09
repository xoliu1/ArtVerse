package com.xoliu.module_community.Present;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xoliu.module_community.Adapter.commentAdapter;
import com.xoliu.module_community.R;
import com.xoliu.module_community.mModel.base;

import java.util.List;

public class MyBottomSheetFragment extends BottomSheetDialogFragment {

    private List<Integer> list;

    private List<base> strings;
    private RecyclerView recyclerView;

    private Context context;


    public MyBottomSheetFragment(List<Integer> list, List<base> strings,Context context) {

        this.list = list;
        this.strings = strings;
        this.context = context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog,container,false);
        initDate(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void initDate(View view){

        recyclerView = view.findViewById(R.id.remark);

        commentAdapter commentAdapter1 = new commentAdapter(strings,list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(commentAdapter1);
    }
}
