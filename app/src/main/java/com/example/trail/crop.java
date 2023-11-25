package com.example.trail;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.webkit.WebView;
public class crop extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://education.nationalgeographic.org/resource/crop/");


    }
}
