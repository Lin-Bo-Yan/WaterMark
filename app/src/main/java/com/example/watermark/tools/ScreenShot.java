package com.example.watermark.tools;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.watermark.tools.FileUtils;
import com.example.watermark.tools.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenShot {
    public static void shoot(Activity activity) {
        File folderPath = FileUtils.saveFile(activity.getApplication(),"sdcard.JPEG");
        if (PermissionUtils.checkPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) || (Build.VERSION.SDK_INT > Build.VERSION_CODES.R)) {
            savePic(takeScreenShot(activity), folderPath);
        } else {
            PermissionUtils.requestPermission(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, null, "該功能需要下載權限");
        }

    }
    // 保存到sdcard
    private static void savePic(Bitmap bitmap, File strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);// 放File路徑
            if (null != fos) {
                //調用Bitmap的compress()方法將Bitmap對象壓縮為PNG格式的圖片，並將壓縮後的數據寫入fos中，使用壓縮質量90，表示圖片質量為90%。
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 獲取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截圖的View
        View view = activity.getWindow().getDecorView(); //獲取當前窗口的根視圖。
        view.setDrawingCacheEnabled(true);  // 將View的繪製緩存功能打開
        view.buildDrawingCache();           // 將View的繪製緩存功能打開
        Bitmap bitmap1 = view.getDrawingCache(); //獲取View的繪製緩存，得到一個Bitmap對象bitmap1，即屏幕的截圖。
        // 獲取狀態欄高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);    // 獲取窗口可見區域的尺寸
        int statusBarHeight = frame.top;                                            //frame.top即為狀態欄的高度。
        StringUtils.HaoLog("takeScreenShot= "+statusBarHeight);
        // 獲取Window屏幕長和高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // 去掉標題欄
        // Bitmap bitmap = Bitmap.createBitmap(bitmap1, 0, 25, 1080, 2195);
        // createBitmap方法，從原始截圖bitmap1中裁剪出去掉狀態欄的部分，得到屏幕內容的截圖。

        Bitmap bitmap = Bitmap.createBitmap(bitmap1,0,statusBarHeight,width,height-statusBarHeight);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();         //銷毀View的繪製緩存
        return bitmap;
    }
}
