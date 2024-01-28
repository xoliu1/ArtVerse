package com.xoliu.module_poem.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xoliu.module_poem.R;

/***
 * 分享的下弹栏
 * @return
 * @author xoliu
 * @create 23-11-14
 **/

public class ShareDialog extends BottomSheetDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_poem_share_dialog, null);
        dialog.setContentView(view);
        initClick(view);
        return dialog;

    }

    private void initClick(View view) {
        Button cancel = (Button) view.findViewById(R.id.btn_cancel_dialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
