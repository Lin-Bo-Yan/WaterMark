package com.example.watermark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WeterMarkBgView extends Drawable {
    private Paint paint = new Paint();
    private List<String> labels;
    private Context context;
    private int degress;   //角度
    private int fontSize;  // 字體大小，單位sp
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
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //getBounds().right 和 getBounds().bottom 獲取繪製區域的寬度和高度。
        int width = getBounds().right;
        int height = getBounds().bottom;

        //畫布背景色
        canvas.drawColor(Color.parseColor("#40F3F5F9"));
        //水印文字顏色
        paint.setColor(Color.parseColor("#50AEAEAE"));
        //設置抗鋸齒
        paint.setAntiAlias(true);
        // setTextSize方法設置水印文字的大小，使用 sp2px 方法將傳入的字體大小轉換為像素值。
        paint.setTextSize(sp2px(context,fontSize));
        // 保存當前的繪製狀態
        canvas.save();
        // rotate 方法將整個畫布旋轉指定的角度（degress）。
        canvas.rotate(degress);
        // measureText 方法測量第一個水印文字（labels.get(0)）的寬度，將結果存儲在 textWidth 變量中
        float textWidth = paint.measureText(labels.get(0));
        int index = 0;

        /*
         * 進行水印的繪製，從 height/10 的位置開始向下遞增 10 個像素，並使用雙層循環進行水印的繪製。
         * 外層循環遍歷垂直方向的位置（postionY），從上到下每隔 10 個像素繪製一行水印。
         * 內層循環遍歷水平方向的位置（postionX），從左到右每隔 textWidth * 2 像素繪製一個水印。
         * 在每個位置上，根據 labels 列表中的每個文本進行繪製，並根據 spacing 參數控制每行文字的垂直間距。
         * */
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
        return PixelFormat.UNKNOWN;
    }

    public static int sp2px(Context context,float spValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
