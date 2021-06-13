package org.zust.interfaceapi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat format1 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");

    // 获取某日当日零点或结束时刻Date
    public static Date getToday0Date(Integer type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        getCalendarType(type,calendar);
        return calendar.getTime();
    }
    // 获取某日前K日零点或结束时刻Date
    public static Date getBeforeKDay0Date(Integer type,Date todayDate,Integer k) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - k);
        getCalendarType(type,calendar);
        return calendar.getTime();
    }
    
    private static Calendar getCalendarType(int type,Calendar calendar) {
        if(type==1) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        return calendar;
    }

//    public static void main(String[] args) {
//        getToday0Date(new Date());
//    }
}
