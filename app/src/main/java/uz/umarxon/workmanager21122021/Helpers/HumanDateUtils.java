package uz.umarxon.workmanager21122021.Helpers;

import java.util.Date;

public class HumanDateUtils {

    public static String durationFromNow(Date startDate) {

        long different = System.currentTimeMillis() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String output = "";
        if (elapsedDays > 0) output += elapsedDays + "days ";
        if (elapsedDays > 0 || elapsedHours > 0) output += elapsedHours + " hours ";
        if (elapsedHours > 0 || elapsedMinutes > 0) output += elapsedMinutes + " minutes ";
        if (elapsedMinutes > 0 || elapsedSeconds > 0) output += elapsedSeconds + " seconds";

        return output;
    }
}