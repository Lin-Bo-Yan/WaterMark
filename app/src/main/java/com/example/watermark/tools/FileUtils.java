package com.example.watermark.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;

import com.example.watermark.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    public static boolean limitFileSize(File file){
        long fileSize = file.length();
        long kiloBytes = fileSize/1024;
        long kilobytes = 52428800L;
        //限制檔案大小50 MB，如果檔案大於50 MB
        if(kiloBytes > kilobytes){
            return true;
        }
        return false;
    }

    public static void saveWebViewVersion(String data, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file,false); // 在保存文件之前需要先删除文件的内容，設置追加參數為 false
            fos.write(data.getBytes());
            fos.close();
            StringUtils.HaoLog("getWebVersion= "+"文件保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            StringUtils.HaoLog("getWebVersion= "+"保存文件失敗"+e);
        }
    }

    public static String readTextFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            StringUtils.HaoLog("readTextFromFile= "+e);
        }
        return stringBuilder.toString();
    }

    public static JSONArray getFileInfo(File[] files){
        JSONArray jArray = new JSONArray();
        try {
            for(File file : files){
                JSONObject jsonObject = new JSONObject();
                Uri url = Uri.fromFile(file);
                jsonObject.put("name",file.getName());
                jsonObject.put("mimeType","mimeType");
                jsonObject.put("url",url.toString());
                if(".jpg".equals(".jpg")){
                    String pic = ThumbnailUtils.resizeAndConvertToBase64(file.getPath(),50);
                    jsonObject.put("thumbnail",pic);
                }
                if(limitFileSize(file)){
                    jsonObject.put("errorMsg","檔案大小超過50MB，無法上傳");
                } else {
                    //如果是圖片就取得圖片長寬，否則取得影片長寬
                    if(".jpg".equals(".jpg")){
                        //取得圖片長高
                        BitmapFactory.Options options = getBitmapFactory(file);
                        jsonObject.put("Width",options.outWidth);
                        jsonObject.put("Height",options.outHeight);
                    } else {
                        //取得影片長高
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        retriever.setDataSource(file.getPath());
                        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                        jsonObject.put("Width",width);
                        jsonObject.put("Height",height);
                    }
                }
                jArray.put(jsonObject);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jArray;
    }

    public static BitmapFactory.Options getBitmapFactory(File file){
        // 使用 BitmapFactory 讀取圖片檔案
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        return options;
    }

    //在PICTURES目錄下建立app_name目錄
    public static File saveFile(Context context, String fileName){
        Resources resources = context.getResources();
        String app_name = resources.getString(R.string.app_name);
        String tableOfContents = Environment.DIRECTORY_PICTURES + File.separator + app_name;
        File folder = new File(Environment.getExternalStoragePublicDirectory(tableOfContents), fileName);

        File tableOfContentsDir = new File(Environment.getExternalStoragePublicDirectory(tableOfContents),"");
        if (!tableOfContentsDir.exists()) {
            tableOfContentsDir.mkdirs();
        }
        // 如果 fileName 文件不存在，则创建空的文件
        if (!folder.exists()) {
            try {
                folder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return folder;
    }

}
