package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG03Req extends AbstractReqMsg {

    /*
    客户帐号	28	转出账号？
    委托单位帐号	28	转入账号？
    金额	15
     */

    private String payOutAccount;
    private String payInAccount;
    private String payAmt;

    public String bodyToString() {

        return StringUtils.rightPad(payOutAccount, 28, ' ') + StringUtils.rightPad(payInAccount, 28, ' ')
                + StringUtils.rightPad(payAmt, 15, ' ');
    }

    // -----------------------------------------------
    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayInAccount() {
        return payInAccount;
    }

    public void setPayInAccount(String payInAccount) {
        this.payInAccount = payInAccount;
    }

    public String getPayOutAccount() {
        return payOutAccount;
    }

    public void setPayOutAccount(String payOutAccount) {
        this.payOutAccount = payOutAccount;
    }
}
