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

    public String sndToBkNo;         //  �ձ���  12
    public String rmtrNameFl;        //  ���������  64
    public String rmtrAcctNo;        //  ������ʺ� 32
    public String payeeNameFl;       //  �տ�������  64
    public String payeeFlAcctNo;     //  �տ����ʺ�  32
    public String rmtAmt;            //  �����  16
    public String rmtPurp;           //  �����;  64


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

}
