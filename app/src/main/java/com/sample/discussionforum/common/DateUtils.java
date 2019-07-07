package com.sample.discussionforum.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    private static final DateFormat FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd MMM yyyy");
    private static final DateFormat FORMAT_HHMM = new SimpleDateFormat("HH:mm");

    public static DateFormat getFormatDdMmYyyy() {
        FORMAT_DD_MM_YYYY.setTimeZone(TimeZone.getDefault());
        return FORMAT_DD_MM_YYYY;
    }

    public static DateFormat getFormathhmm() {
        FORMAT_HHMM.setTimeZone(TimeZone.getDefault());
        return FORMAT_HHMM;
    }

    public static String dateToString(Date date) {
        return DateUtils.getFormatDdMmYyyy().format(date);
    }

    public static String timeToString(long milies) {
        return DateUtils.getFormathhmm().format(new Date(milies));
    }

    /**
     * Returns true if the given millis is in today's range
     */
    public static boolean isItToday(long millis) {
        long nextDayStart = getNextDayStartMillis();
        long todayStart = nextDayStart - android.text.format.DateUtils.DAY_IN_MILLIS;

        return millis >= todayStart && millis <= nextDayStart;
    }

    /**
     * Returns the start millis of next day.
     */
    public static long getNextDayStartMillis() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        cal.clear();
        //Set next day beginning to the calendar instance.
        cal.set(year, month, date + 1);

        return cal.getTimeInMillis();
    }

}
