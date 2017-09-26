package io.github.rahulrvp.hackernews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.github.rahulrvp.hackernews.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra(URL);
        if (!TextUtils.isEmpty(url)) {

            WebView webView = (WebView) findViewById(R.id.hn_web_view);
            if (webView != null) {
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setAppCacheEnabled(true);
                webView.loadUrl(url);
            }
        }
    }
}
