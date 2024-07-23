package com.xoliu.module_compose;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_compose.databinding.ActivityComposeBinding;

@Route(path = "/compose/acrostic")
public class ComposeActivity extends AppCompatActivity {

    private ActivityComposeBinding binding;
    private ComposePoem composePoem;

    //加载框变量
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
        initListener();
    }

    private void initView() {






    }

    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);	//设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();
    }
    public Boolean dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }

    private void initData() {
        composePoem = new ComposePoem();
    }

    private void initListener() {
        binding.yes.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View v1 = getWindow().peekDecorView();
            if (null != v1) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            String keyWord = binding.edText.getText().toString().trim();
            if (!keyWord.isEmpty()) {
                showProgressDialog(this, "正在快马加鞭创作中");
                binding.edText.setText("");
                composePoem.requestPoem("写一首藏头诗，附带赏析，句头的字是：" + keyWord);
                composePoem.getPoemLiveData().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String poem) {
                        handlePoem(poem);
                        dismissProgressDialog();
                    }
                });
            }
        });
    }

    private void handlePoem(String poem) {
        Log.d("xoliu", "handlePoem: " + poem);
        binding.result.setVisibility(View.VISIBLE);
        binding.resultStr.setText(poem);
    }
}