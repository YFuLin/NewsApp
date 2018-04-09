package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ContentActivity extends AppCompatActivity {

    public static String LINK_NAME="CONTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        String LinkName=intent.getStringExtra(LINK_NAME).toString();
        webView.loadUrl(LinkName);
    }
}
