package fdc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    static private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    static private SimpleDateFormat sdfdatetime14 = new SimpleDateFormat("yyyyMMddHHmmss");
    static private SimpleDateFormat sdfdatetime18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate8() {
        return sdfdate8.format(new Date());
    }

    public static String getTime6() {
        return sdftime6.format(new Date());
    }

    public static String getDatetime14() {
        return sdfdatetime14.format(new Date());
    }

    public static String getDatetime18() {
        return sdfdatetime18.format(new Date());
    }
}
