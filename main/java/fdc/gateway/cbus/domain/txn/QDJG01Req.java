package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG01Req extends AbstractReqMsg {

    /*
    ’À∫≈	28	◊Û∂‘∆Î”“≤πø’∏Ò
     */
    public String accountNo;

    public String bodyToString() {

        return StringUtils.rightPad(accountNo, 28, ' ');
    }

}
