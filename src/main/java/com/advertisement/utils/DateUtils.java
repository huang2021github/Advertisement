package com.advertisement.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
