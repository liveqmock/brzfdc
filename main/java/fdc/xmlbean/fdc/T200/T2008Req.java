package fdc.xmlbean.fdc.T200;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ReqHead;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T2008Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {
        public String Acct = "";
        public String AcctName = "";
        public String PlanNo = "";
        public String SubmitDate = "";
        public String PlanAmt = "";
        public String PlanNum = "";
        @XStreamImplicit
        public List<Record> recordList = new ArrayList<Record>();

        @XStreamAlias("RecordSet")
        public static class Record {
            public String PlanDetailNO = "";
            public String ToAcctName = "";
            public String ToAcct = "";
            public String ToBankName = "";
            public String Amt = "";
            public String PlanDate = "";
            public String Purpose = "";
            public String Remark = "";
        }
    }

    public static void main(String[] args) {
       T2008Req tia = new T2008Req();
        tia.head.OpCode = "2008";
        tia.head.BankCode = "ssss";
        Param.Record record = new Param.Record();
        record.Remark = "hello";
        record.ToBankName = "建行";
        tia.param.recordList.add(record);
        record = new Param.Record();
        record.Remark = "hi";
        record.ToBankName = "农行";
        tia.param.recordList.add(record);
        System.out.println(tia.toXml());
    }
}
