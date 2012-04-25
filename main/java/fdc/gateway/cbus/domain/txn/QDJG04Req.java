package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG04Req extends AbstractReqMsg {

    /*
    �ձ���	SND-TO-BK-NO	C(12)
    ���������	RMTR-NAME-FL	C(64)
    ������ʺ�	RMTR-ACCT-NO	C(32)
    �տ�������	PAYEE-NAME-FL	C(64)
    �տ����ʺ�	PAYEE-FL-ACCT-NO	C(32)
    �����	RMT-AMT	N(13��2)
    �����;	RMT-PURP	C(64)
     */

    private String sndToBkNo;         //  �ձ���  12
    private String rmtrNameFl;        //  ���������  64
    private String rmtrAcctNo;        //  ������ʺ� 32
    private String payeeNameFl;       //  �տ�������  64
    private String payeeFlAcctNo;     //  �տ����ʺ�  32
    private String rmtAmt;            //  �����  16
    private String rmtPurp;           //  �����;  64


    public String bodyToString() {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(StringUtils.rightPad(sndToBkNo, 12, ' '));
        strBuilder.append(StringUtils.rightPad(rmtrNameFl, 64, ' '));
        strBuilder.append(StringUtils.rightPad(rmtrAcctNo, 32, ' '));
        strBuilder.append(StringUtils.rightPad(payeeNameFl, 64, ' '));
        strBuilder.append(StringUtils.rightPad(payeeFlAcctNo, 32, ' '));
        strBuilder.append(StringUtils.rightPad(rmtAmt, 16, ' '));
        strBuilder.append(StringUtils.rightPad(rmtPurp, 64, ' '));

        return strBuilder.toString();
    }

    // -----------------------------------------------

    public String getPayeeFlAcctNo() {
        return payeeFlAcctNo;
    }

    public void setPayeeFlAcctNo(String payeeFlAcctNo) {
        this.payeeFlAcctNo = payeeFlAcctNo;
    }

    public String getPayeeNameFl() {
        return payeeNameFl;
    }

    public void setPayeeNameFl(String payeeNameFl) {
        this.payeeNameFl = payeeNameFl;
    }

    public String getRmtAmt() {
        return rmtAmt;
    }

    public void setRmtAmt(String rmtAmt) {
        this.rmtAmt = rmtAmt;
    }

    public String getRmtPurp() {
        return rmtPurp;
    }

    public void setRmtPurp(String rmtPurp) {
        this.rmtPurp = rmtPurp;
    }

    public String getRmtrAcctNo() {
        return rmtrAcctNo;
    }

    public void setRmtrAcctNo(String rmtrAcctNo) {
        this.rmtrAcctNo = rmtrAcctNo;
    }

    public String getRmtrNameFl() {
        return rmtrNameFl;
    }

    public void setRmtrNameFl(String rmtrNameFl) {
        this.rmtrNameFl = rmtrNameFl;
    }

    public String getSndToBkNo() {
        return sndToBkNo;
    }

    public void setSndToBkNo(String sndToBkNo) {
        this.sndToBkNo = sndToBkNo;
    }
}
