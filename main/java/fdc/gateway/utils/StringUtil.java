package fdc.gateway.utils;

import java.math.BigDecimal;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * ��ȡ���Ӵ�֮����ַ�������
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
     * ��amt��λԪת��Ϊ�֣�������λ���磺0.01Ԫ������001
     *
     * @param amt
     * @return
     */
    public static String toBiformatAmt(BigDecimal amt) {
        double dblAmt = amt.doubleValue();
        String rtnStrAmt = "";
        if (dblAmt < 1) {
            if (dblAmt > 0.1) {
                rtnStrAmt = "0" + String.valueOf(dblAmt * 100);
            } else {
                rtnStrAmt = "00" + String.valueOf(dblAmt * 100);
            }
        } else {
            rtnStrAmt = String.valueOf(dblAmt * 100);
        }
        return rtnStrAmt.split("\\.")[0];
    }

    /**
     * ���ַ����о��Ӵ������滻Ϊ������
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

}