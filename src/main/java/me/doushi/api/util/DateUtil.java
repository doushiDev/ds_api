package me.doushi.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by songlijun on 15/11/5.
 */
public class DateUtil {
    public static String countTime(String begin) throws ParseException {
        int hour = 0;
        int minute = 0;
        long total_minute = 0;
        StringBuffer sb = new StringBuffer();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin_date = df.parse(begin);
            Date end_date = new Date();

            total_minute = (end_date.getTime() - begin_date.getTime()) / (1000 * 60);

            hour = (int) total_minute / 60;
            minute = (int) total_minute % 60;

        } catch (ParseException e) {
            System.out.println("传入的时间格式不符合规定");
        }

        if (hour != 0 && hour <= 2) {
            sb.append(hour).append("小时").append("前");
        } else if (hour >= 3) {
            //判断是否为当天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String day = simpleDateFormat.format(new Date());
            String beginDay = simpleDateFormat.format(simpleDateFormat.parse(begin));
            String dayStr = "";
            if (day.equals(beginDay)) { //当天
                dayStr = df.format(df.parse(begin)).substring(11, 16);
                sb.append("今天 " + dayStr);
            }else{
                dayStr = df.format(df.parse(begin)).substring(0, 10);
                sb.append(dayStr);
            }

        } else {
            if (minute == 0 && minute < 5) {
                sb.append("刚刚");
            } else {
                sb.append(minute).append("分钟").append("前");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(countTime("2015-11-07 20:00:23"));

    }
}
