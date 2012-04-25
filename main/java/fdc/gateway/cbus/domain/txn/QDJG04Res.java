package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG04Res extends AbstractResMsg {

    /*
       �㻮���	RMT-AMT-2	C(15)
       ֧���������	FM-TRNT-AMT-SQ-NO	C(8)
     */
    private String rmtAmt;
    private String fmTrntAmtSqNo;

    @Override
    public void assembleBodyFields(byte[] buffer) {
        byte[] amtBytes = new byte[15];
        System.arraycopy(buffer, 0, amtBytes, 0, 15);
        String amtStr = new String(amtBytes);
        rmtAmt = String.format("%.2f", StringUtils.isEmpty(amtStr) ? "0.00" : amtStr.trim());

        byte[] serialBytes = new byte[8];
        System.arraycopy(buffer, 15, serialBytes, 0, 8);
        String serialStr = new String(serialBytes);
        fmTrntAmtSqNo = serialStr.trim();

        System.out.println("������" + rmtAmt + "��������ˮ�š�" + fmTrntAmtSqNo);
    }

    // -----------------------------------------------


    public String getFmTrntAmtSqNo() {
        return fmTrntAmtSqNo;
    }

    public void setFmTrntAmtSqNo(String fmTrntAmtSqNo) {
        this.fmTrntAmtSqNo = fmTrntAmtSqNo;
    }

    public String getRmtAmt() {
        return rmtAmt;
    }

    public void setRmtAmt(String rmtAmt) {
        this.rmtAmt = rmtAmt;
    }
}
