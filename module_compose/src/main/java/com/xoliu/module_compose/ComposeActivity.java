package com.xoliu.module_compose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_compose.databinding.ActivityComposeBinding;

@Route(path = "/compose/acrostic")
public class ComposeActivity extends AppCompatActivity {

    private ActivityComposeBinding binding;
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



    private void initListener() {
        binding.yes.setOnClickListener(v -> {
            String keyWord = binding.edText.getText().toString();
            binding.edText.setText("");
            ComposePoem composePoem = new ComposePoem(keyWord);


            LiveData<String> poemLiveData = composePoem.getPoemLiveData();
            poemLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String poem) {
                    handlePoem(poem);
                }
            });

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    handlePoem(composePoem);
//                }
//            });

        });
    }

    private void handlePoem(String composePoem) {
            binding.result.setVisibility(View.VISIBLE);
            binding.resultStr.setText(composePoem);
    }

    private void initData() {
    }
}