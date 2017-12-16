package com.example.wxy.watertest10.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.R;

public class NewsDisplayActivity extends BaseActivity implements View.OnClickListener{

    private String newsUrl;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        back = (ImageView)findViewById(R.id.back) ;
        back.setOnClickListener(this);
        newsUrl = getIntent().getStringExtra("news_url");
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
