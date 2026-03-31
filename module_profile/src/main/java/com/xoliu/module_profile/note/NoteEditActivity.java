package com.xoliu.module_profile.note;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xoliu.module_profile.databinding.ActivityNoteEditBinding;

import org.json.JSONObject;

import utils.MVUtil;

public class NoteEditActivity extends AppCompatActivity {

    private static final String TAG = "NoteEditActivity";

    private ActivityNoteEditBinding binding;
    private String mode; // "create" or "edit"
    private int noteId;
    private int userId;
    private String userEmail;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Log.d(TAG, "handleMessage what=" + msg.what + " data=" + json);

            switch (msg.what) {
                case NoteApi.MSG_CREATE_NOTE:
                case NoteApi.MSG_UPDATE_NOTE:
                    handleSaveResult(json);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = MVUtil.getInt("user_id", 0);
        userEmail = MVUtil.getString("user_email", "");

        mode = getIntent().getStringExtra("mode");
        if ("edit".equals(mode)) {
            noteId = getIntent().getIntExtra("note_id", 0);
            String title = getIntent().getStringExtra("note_title");
            String content = getIntent().getStringExtra("note_content");
            binding.etNoteTitle.setText(title);
            binding.etNoteContent.setText(content);
            binding.toolbarNoteEdit.setTitle("编辑笔记");
        } else {
            binding.toolbarNoteEdit.setTitle("新建笔记");
        }

        // 返回按钮
        binding.toolbarNoteEdit.setNavigationOnClickListener(v -> finish());

        // 保存按钮
        binding.btnSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = binding.etNoteTitle.getText().toString().trim();
        String content = binding.etNoteContent.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("edit".equals(mode)) {
            NoteApi.updateNote(noteId, userId, title, content, handler);
        } else {
            NoteApi.createNote(userId, userEmail, title, content, handler);
        }
    }

    private void handleSaveResult(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("Code", 0);
            if (code == 200) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                String message = jsonObject.optString("Message", "保存失败");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "handleSaveResult error", e);
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}
