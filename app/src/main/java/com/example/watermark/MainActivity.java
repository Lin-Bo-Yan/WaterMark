package com.example.watermark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        List<String> labels = new ArrayList<>();
        labels.add("周碧玉");
        labels.add("編號：1234");
        labels.add(TimeUtils.customDate());

        textView = findViewById(R.id.textView);
        textView.setBackground(new WeterMarkBgView(MainActivity.this,labels,-10,20));
    }
}