package bo.com.jvargas.veterinaria.utils;


import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {
    public static final String FORMAT_DATE_PARAM_URL = "dd-MM-yyyy HH:mm:ss";
    public static Date formatToStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date formatToEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String toString(String format, Date valor) {
        try {
            if (valor == null) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(valor);
        } catch (Exception ex) {
            log.error("Error ", ex);
            return null;
        }
    }

    public static boolean equals(Date date1, Date date2, String format) {
        if (date1 == null && date2 == null) {
            return true;
        } else if (date1 != null && date2 != null) {
            String date1Str = toString(format, date1);
            String date2Str = toString(format, date2);
            if (date1Str != null && date2Str != null) {
                return date1Str.equals(date2Str);
            }
            return false;
        }
        return false;
    }

    public static Date plus(Date date, int hoursToAdd, int minutesToAdd, int secondsToAdd) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.HOUR, hoursToAdd);
        calDateStart.add(Calendar.MINUTE, minutesToAdd);
        calDateStart.add(Calendar.SECOND, secondsToAdd);

        return calDateStart.getTime();
    }

    public static Date plus(Date date, int days) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.DAY_OF_MONTH, days);
        return calDateStart.getTime();
    }


}
