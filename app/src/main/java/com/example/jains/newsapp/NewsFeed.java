package com.example.jains.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFeed extends AppCompatActivity {

    WebView webView;
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        webView = (WebView)findViewById(R.id.webView);
        //textView = (TextView)findViewById(R.id.textView);

        Intent i = getIntent();
        String url=i.getStringExtra("url");

        //textView.setText(url);
        if(url!=null)
        {
            //Toast.makeText(NewsFeed.this, url, Toast.LENGTH_SHORT).show();

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }
}
