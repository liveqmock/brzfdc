package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG03Res extends AbstractResMsg {

    /*
       金额	15
       交易流水号	19
     */
    private String amt;
    private String serialNo;

    @Override
    public void assembleBodyFields(byte[] buffer) {
        byte[] amtBytes = new byte[15];
        System.arraycopy(buffer, 0, amtBytes, 0, 15);
        String amtStr = new String(amtBytes);
        amt = String.format("%.2f", StringUtils.isEmpty(amtStr) ? "0.00" : amtStr.trim());

        byte[] serialBytes = new byte[19];
        System.arraycopy(buffer, 15, serialBytes, 0, 19);
        String serialStr = new String(serialBytes);
        serialNo = serialStr.trim();

        System.out.println("【交易金额】" + amt + "【交易流水号】" + serialNo);
    }

    // -----------------------------------------------

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
