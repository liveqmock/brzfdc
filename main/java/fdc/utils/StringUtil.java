package fdc.utils;

import java.math.BigDecimal;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 获取两子串之间的字符串内容
     *
     * @param fromStr
     * @param startStr
     * @param endStr
     * @return
     */
    public static String getSubstrBetweenStrs(String fromStr, String startStr, String endStr) {
        int length = startStr.length();
        int start = fromStr.indexOf(startStr) + length;
        int end = fromStr.indexOf(endStr);
        return fromStr.substring(start, end);
    }

    /**
     * 将字符串中旧子串内容替换为新内容
     *
     * @param fromStr
     * @param oldSubstr
     * @param newSubstr
     * @return
     */
    public static String replaceOldstrToNewstr(String fromStr, String oldSubstr, String newSubstr) {
        String toStr = fromStr;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(oldSubstr);
        matcher = pattern.matcher(toStr);
        if (matcher.find()) {
            toStr = matcher.replaceAll(newSubstr);
        }
        return toStr;
    }

    public static String parseStr(String orString) {
        String aString = "";
        String reg = new String("\n");
        aString = orString.replaceAll(reg, "<br>");
        return aString;
    }

    public static String reversStr(String orString) {
        String aString = "";
        aString = orString.replaceAll("<br>", "\n");
        return aString;
    }

    public static double turnFloat(double orgValue) {
        NumberFormat f = new DecimalFormat();
        return orgValue;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param val   需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果 商业计算用string来构造bigdecimal Returns a BigDecimal whose
     */

    public static double round(double val, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(val));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}