package com.example.gm1.livescore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LiveScoreActivity extends AppCompatActivity {

    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);

        mWebView = (WebView)findViewById(R.id.live_score_webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://pavscore.tigercricket.com.bd:8080/");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
     public void onBackPressed() {
         if (mWebView.canGoBack()) {
             mWebView.goBack();
         } else {
             super.onBackPressed();
         }
     }
}
