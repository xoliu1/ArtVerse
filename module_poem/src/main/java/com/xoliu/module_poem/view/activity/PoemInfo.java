package com.xoliu.module_poem.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xoliu.module_poem.R;

@Route(path = "/poem/web")
public class PoemInfo extends AppCompatActivity {

    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_main);
        String webUrl = getIntent().getStringExtra("webUrl");



        LinearLayout mLayout = (LinearLayout) findViewById(R.id.mLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);

        mLayout.addView(mWebView);

        mWebView.setWebViewClient(new WebViewClient()); // 设置WebViewClient以在WebView中打开链接，而不是使用外部浏览器

        mWebView.loadUrl(webUrl);
    }
}