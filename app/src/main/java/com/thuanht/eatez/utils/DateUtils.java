package com.thuanht.eatez.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertToRelativeTime(String timestamp) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        long seconds = ChronoUnit.SECONDS.between(dateTime, now);
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        long days = ChronoUnit.DAYS.between(dateTime, now);

        if (days > 0) {
            return days + " ngày trước";
        } else if (hours > 1) {
            return hours + " giờ trước";
        } else if (minutes > 1) {
            return minutes + " phút trước";
        } else if (seconds > 1) {
            return seconds + " giây trước";
        } else {
            return "vài giây trước";
        }
    }
}
