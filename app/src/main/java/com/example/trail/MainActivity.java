package com.example.trail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        public void crop (View v){
        Intent i = new Intent(this, crop.class);
        startActivity(i);
    }
    public void newPage (View v){
        Intent i = new Intent(this, t2.class);
        startActivity(i);
    }
    public void tractor (View v){
        Intent i = new Intent(this, tractors.class);
        startActivity(i);
    }
    public void fertilizers (View v){
        Intent i = new Intent(this, fertilizers.class);
        startActivity(i);
    }
}