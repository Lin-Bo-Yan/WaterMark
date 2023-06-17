package com.example.watermark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.watermark.tools.FileUtils;
import com.example.watermark.tools.ScreenShot;

public class ScreenshotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenshot_activity);

        Button btShot = findViewById(R.id.button_Shot);
        Button btRes = findViewById(R.id.button_Result);
        ImageView igShow = findViewById(R.id.imageView_Show);
        btShot.setOnClickListener(v->{
            //igShow.setImageBitmap(ScreenShot.takeScreenShot(ScreenshotActivity.this));
            ScreenShot.shoot(ScreenshotActivity.this);
        });
        btRes.setOnClickListener(v -> {
            igShow.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
        });
    }
}