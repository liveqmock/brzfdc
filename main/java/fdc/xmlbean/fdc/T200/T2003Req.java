package fdc.xmlbean.fdc.T200;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ReqHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T2003Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {
        public String ContractNum = "";
        public String Acct = "";
        public String AcctName = "";
        public String BuyerName = "";
        public String BuyerAcct = "";
        public String BuyerBankName = "";

        public String BuyerIDType = "";
        public String BuyerIDCode = "";
        public String TotalAmt = "";
        public String Deposit = "";
        public String DownPay = "";
        public String Mortgage = "";
        public String HouseAddress = "";
        public String HouseType = "";
        public String HouseNO = "";
        public String TreasuryName = "";
        public String TreasuryAcct = "";
        public String TreasuryBankName = "";
    }
}
