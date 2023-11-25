package com.example.trail;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
public class tractors extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tractors);
    WebView webView = (WebView) findViewById(R.id.webview);
webView.loadUrl("https://education.nationalgeographic.org/resource/crop/");
}}