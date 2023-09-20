package com.example.watermark.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.watermark.R;

import java.util.List;

public class WeterMarkBgView extends Drawable {
    private Paint paint = new Paint();
    private Paint bitmapPaint = new Paint();
    private List<String> labels;
    private Context context;
    private int degress;   //角度
    private int fontSize;  // 字體大小，單位sp
    private Typeface customTypeface;
    /*
    context  上下文
    labels   水印文字列表
    degress  水印角度
    fontSize 水印文字大小
    */

    public WeterMarkBgView(Context context, List<String> labels, int degress, int fontSize) {
        this.labels = labels;
        this.context = context;
        this.degress = degress;
        this.fontSize = fontSize;
        customTypeface = Typeface.createFromAsset(context.getAssets(), "SentyDew.ttf");
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 計算畫布的寬度和高度
        int canvasWidth = getBounds().width();
        int canvasHeight = getBounds().height();

        //畫布背景色
        canvas.drawColor(Color.parseColor("#40F3F5F9"));
        //水印文字顏色
        paint.setColor(Color.parseColor("#50AEAEAE"));
        //設置抗鋸齒
        paint.setAntiAlias(true);
        // setTextSize方法設置水印文字的大小，使用 sp2px 方法將傳入的字體大小轉換為像素值。
        paint.setTextSize(sp2px(context,fontSize));
        // 設置字體
        paint.setTypeface(customTypeface);
        // 設置透明度為半透明
        paint.setAlpha(255);
        // 設置圖片透明度
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.call_cancel_icon);
        bitmapPaint.setAlpha(255);

        // 計算圖片的左上角位置，使它們都位於畫布中心
        int imageLeft = (canvasWidth - bitmap.getWidth()) / 2;
        int imageTop = (canvasHeight - bitmap.getHeight()) / 2;
        canvas.drawBitmap(bitmap, imageLeft, imageTop, bitmapPaint);

        // 保存當前的繪製狀態
        canvas.save();

        // 獲取水印文字的寬度和高度
        // measureText 方法測量第一個水印文字（labels.get(0)）的寬度，將結果存儲在 textWidth 變量中
        float textWidth = paint.measureText(labels.get(0));
        float textHeight = paint.getTextSize();
        // 計算文字的左上角位置，使它們都位於畫布中心
        int textLeft = (canvasWidth - (int)textWidth) / 2;
        int textTop = (canvasHeight + (int)textHeight) / 2;

        // rotate 方法將整個畫布旋轉指定的角度（degress），以下改成旋轉文字角度
        canvas.rotate(degress, textLeft + textWidth / 2, textTop - textHeight / 2);

        int spacing = 0; // 垂直間距，drawText(要繪製的文本內容,x軸起始位置畫布左上角,y軸起始位置畫布左上角,Paint對象設置文本的顏色)
        for (String label : labels) {
            canvas.drawText(label, textLeft, textTop  + spacing, paint);
            spacing = spacing + 100;
        }

        // 使用 canvas.restore() 恢復到 canvas.save() 之前的繪製狀態
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        //表示不確定畫筆的不透明度
        return PixelFormat.UNKNOWN;
    }

    /*
    PixelFormat.UNKNOWN: 表示不確定的像素格式，即畫筆的不透明度未知。
    PixelFormat.TRANSLUCENT: 表示畫筆具有半透明的像素格式。
    PixelFormat.TRANSPARENT: 表示畫筆具有完全透明的像素格式。
    PixelFormat.OPAQUE: 表示畫筆具有完全不透明的像素格式。
    */

    public static int sp2px(Context context,float spValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /*
        進行水印的繪製，從 height/10 的位置開始向下遞增 10 個像素，並使用雙層循環進行水印的繪製。
        外層循環遍歷垂直方向的位置（postionY），從上到下每隔 10 個像素繪製一行水印。
        內層循環遍歷水平方向的位置（postionX），從左到右每隔 textWidth * 2 像素繪製一個水印。
        在每個位置上，根據 labels 列表中的每個文本進行繪製，並根據 spacing 參數控制每行文字的垂直間距。
        int index = 0;
        for(int postionY = height/10; postionY <= height; postionY += 10){
            float fromX = -width + (index++ % 2) * textWidth;
            for(float postionX = fromX; postionX < width; postionX += textWidth *2){
                int spacing = 0; //間距
                for(String label :labels){
                    canvas.drawText(label, postionX,postionY + spacing, paint);
                    spacing = spacing + 50;
                }
            }
        }

        for (String label : labels) {
            for (float postionX = 1; postionX < width; postionX += textWidth * 3) {
                canvas.drawText(label, postionX, 100 + spacing, paint);
            }
            spacing = spacing + 100;
        }
    */
}
