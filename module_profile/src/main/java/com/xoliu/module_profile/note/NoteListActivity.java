package com.xoliu.module_profile.note;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xoliu.module_profile.databinding.ActivityNoteListBinding;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import utils.MVUtil;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener {

    private static final String TAG = "NoteListActivity";
    private static final int REQUEST_EDIT = 1001;

    private ActivityNoteListBinding binding;
    private NoteAdapter adapter;
    private List<NoteBean> noteList = new ArrayList<>();
    private int userId;
    private String userEmail;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            binding.swipeRefresh.setRefreshing(false);
            String json = (String) msg.obj;
            Log.d(TAG, "handleMessage what=" + msg.what + " data=" + json);

            switch (msg.what) {
                case NoteApi.MSG_GET_NOTES:
                    handleGetNotes(json);
                    break;
                case NoteApi.MSG_DELETE_NOTE:
                    handleDeleteNote(json);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = MVUtil.getInt("user_id", 0);
        userEmail = MVUtil.getString("user_email", "");

        initView();
        loadNotes();
    }

    private void initView() {
        // Toolbar 返回
        binding.toolbarNoteList.setNavigationOnClickListener(v -> finish());

        // RecyclerView
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(noteList, this);
        binding.rvNotes.setAdapter(adapter);

        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener(this::loadNotes);

        // FAB 新建笔记
        binding.fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteEditActivity.class);
            intent.putExtra("mode", "create");
            startActivityForResult(intent, REQUEST_EDIT);
        });
    }

    private void loadNotes() {
        if (userId == 0) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            binding.swipeRefresh.setRefreshing(false);
            return;
        }
        binding.swipeRefresh.setRefreshing(true);
        NoteApi.getNotes(userId, handler);
    }

    private void handleGetNotes(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code", 0);
            if (code == 200) {
                String dataStr = jsonObject.optString("data", "[]");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<NoteBean>>() {}.getType();
                List<NoteBean> list = gson.fromJson(dataStr, listType);
                noteList.clear();
                if (list != null) {
                    noteList.addAll(list);
                }
                adapter.updateData(noteList);
                updateEmptyView();
            } else {
                String message = jsonObject.optString("Message", "获取笔记失败");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "handleGetNotes error", e);
            Toast.makeText(this, "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleDeleteNote(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("Code", 0);
            if (code == 200) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                loadNotes();
            } else {
                String message = jsonObject.optString("Message", "删除失败");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "handleDeleteNote error", e);
        }
    }

    private void updateEmptyView() {
        if (noteList.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.swipeRefresh.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.swipeRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNoteClick(NoteBean note, int position) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra("mode", "edit");
        intent.putExtra("note_id", note.getId());
        intent.putExtra("note_title", note.getTitle());
        intent.putExtra("note_content", note.getContent());
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    public void onNoteLongClick(NoteBean note, int position) {
        new AlertDialog.Builder(this)
                .setTitle("删除笔记")
                .setMessage("确定要删除「" + note.getTitle() + "」吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    NoteApi.deleteNote(note.getId(), userId, handler);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            loadNotes();
        }
    }
}
