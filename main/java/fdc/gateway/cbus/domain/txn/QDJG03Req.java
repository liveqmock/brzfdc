package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG03Req extends AbstractReqMsg {

    /*
    �ͻ��ʺ�	28	ת���˺ţ�
    ί�е�λ�ʺ�	28	ת���˺ţ�
    ���	15
     */

    public String payOutAccount;
    public String payInAccount;
    public String payAmt;

    public String bodyToString() {

        return StringUtils.rightPad(payOutAccount, 28, ' ') + StringUtils.rightPad(payInAccount, 28, ' ')
                + StringUtils.rightPad(payAmt, 15, ' ');
    }
}
