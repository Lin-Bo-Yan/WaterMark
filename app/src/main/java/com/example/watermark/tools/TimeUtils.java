package com.example.watermark.tools;

import java.util.Calendar;

public class TimeUtils {
    public static String customTime(){
        // 獲取當前時間的 Calendar 實例
        Calendar calendar = Calendar.getInstance();
        // 獲取當前小時和分鐘
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //StringBuilder builder = new StringBuilder();
        //builder.append(hour);
        //builder.append(":");
        //builder.append(minute);
        String time = String.format("%s:%s", hour, minute);

        return time;
    }

    public static String customDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = String.format("%s年%s月%s日", year, month, day);
        return date;
    }
}
