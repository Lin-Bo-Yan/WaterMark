package com.example.watermark.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ThumbnailUtils {
    public static String resizeAndConvertToBase64(String imagePath, int thumbnailSize) {
        // 加載原始圖像
        Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);
        // 計算縮放比例
        float scaleFactor = calculateScaleFactor(originalBitmap.getWidth(), originalBitmap.getHeight(), thumbnailSize);
        // 計算縮放後的尺寸
        int targetWidth = Math.round(originalBitmap.getWidth() * scaleFactor);
        int targetHeight = Math.round(originalBitmap.getHeight() * scaleFactor);
        // 縮放圖像為縮略圖
        Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, false);
        // 釋放原始圖像資源
        originalBitmap.recycle();
        // 將縮略圖轉換為Base64字符串
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbnailBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    // 計算縮放比例
    private static float calculateScaleFactor(int originalWidth, int originalHeight, int targetSize) {
        float scale;
        if (originalWidth > originalHeight) {
            scale = (float) targetSize / originalWidth;
        } else {
            scale = (float) targetSize / originalHeight;
        }
        return scale;
    }
}
