package br.com.mm.adcertproj.poplposters.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static final SimpleDateFormat US_SHORT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static Date tryFromString(String sDate) {
        Date dDate = null;
        try {
            dDate = US_SHORT.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dDate;
    }
}
