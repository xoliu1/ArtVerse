package com.xoliu.module_profile;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webview);
        TextView titleView = findViewById(R.id.webview_title);

        // 获取传入的 URL 和标题
        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if (title != null && !title.isEmpty()) {
            titleView.setText(title);
        }

        // 配置 WebView
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        // 在 App 内打开链接，不跳转外部浏览器
        webView.setWebViewClient(new WebViewClient());

        // 动态更新标题
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String pageTitle) {
                super.onReceivedTitle(view, pageTitle);
                if (title == null || title.isEmpty()) {
                    titleView.setText(pageTitle);
                }
            }
        });

        // 返回按钮
        findViewById(R.id.webview_back).setOnClickListener(v -> finish());

        // 加载网页
        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        // 如果 WebView 有历史记录，先回退网页
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
