package com.example.watermark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> labels = new ArrayList<>();
        labels.add("他");

        textView = findViewById(R.id.textView);
        textView.setBackground(new WeterMarkBgView(MainActivity.this,labels,0,30));
    }
}