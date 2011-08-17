package fdc.xmlbean.fdc.T100;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ReqHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T1001Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {

        public String ContractNum = "";
        public String ContractDate = "";
        public String HouseNum = "";
        public String BuyerName = "";
        public String BuyerAcct = "";
        public String BuyerBankName = "";
        public String BuyerIDType = "";
        public String BuyerIDCode = "";
        public String SellerName = "";
        public String SellerAcct = "";
        public String SellerBankName = "";
        public String SellerIDType = "";
        public String SellerIDCode = "";
        public String ManageAcct = "";
        public String ManageAcctName = "";
        public String ManageBankName = "";
        public String TotalAmt = "";
        public String Deposit = "";
        public String DepositDate = "";
        public String DownPay = "";
        public String DownPayDate = "";
        public String Loan = "";
        public String LoanDate = "";
        public String LoanFrom = "";
        public String OtherAmt = "";
        public String HouseAddress = "";
        public String OldID = "";
    }
}
