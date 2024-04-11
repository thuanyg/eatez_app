package com.thuanht.eatez.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static DateUtils instance;

    private DateUtils() {}

    public static DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    public String FormatDateStringToDayMonth(String date){
        String formattedDate = date;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        try {
            Date d = inputFormat.parse(date);
            assert d != null;
            formattedDate = outputFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
