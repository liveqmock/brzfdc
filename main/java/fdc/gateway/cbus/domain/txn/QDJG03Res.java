package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG03Res extends AbstractResMsg {

    /*
       ���	15
       ������ˮ��	19
     */
    public String amt;
    public String serialNo;

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

        System.out.println("�����׽�" + amt + "��������ˮ�š�" + serialNo);
    }
}
