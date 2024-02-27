package com.xoliu.module_compose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_compose.databinding.ActivityCompose2Binding;
@Route(path = "/compose/poems")
public class Compose2Activity extends AppCompatActivity {
    private ActivityCompose2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompose2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListener();

    }

    private void initListener() {
        binding.yes.setOnClickListener(v -> {
            String keyWord = binding.edText.getText().toString();
            binding.edText.setText("");
            ComposePoem composePoem = new ComposePoem("写一首古诗，附带赏析，诗的相关主旨/意境是：" + keyWord);


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
}