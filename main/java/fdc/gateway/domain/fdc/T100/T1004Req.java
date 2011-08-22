package fdc.gateway.domain.fdc.T100;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.ReqHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T1004Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {
        public String Acct = "";
        public String AcctName = "";
        public String ContractNum = "";
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
        public String EndReason = "";
        public String TransSellerAmt = "";
        public String TransBuyerAmt = "";
    }
}
