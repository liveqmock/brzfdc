package fdc.xmlbean.fdc.T100;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ReqHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T1003Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {
        public String ContractNum = "";
        public String AcctName = "";
        public String Acct = "";
        public String BankName = "";
        public String toAcctName = "";
        public String toAcct = "";
        public String toBankName = "";
        public String EnterpriseSerial = "";
        public String Type = "";
        public String TransSellerAmt = "";
        public String Purpose = "";
    }
}
