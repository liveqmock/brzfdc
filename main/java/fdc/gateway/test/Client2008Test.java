package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0007Req;
import fdc.gateway.domain.fdc.T200.T2005Req;
import fdc.gateway.domain.fdc.T200.T2008Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client2008Test extends ClientBaseTest {

    public static void main(String[] args) {

        T2008Req req = new T2008Req();
        req.head.OpCode = "2008";
        req.head.BankCode = "ssss";
        req.param.Acct = "2008888888";
        req.param.AcctName = "2008";
        req.param.PlanAmt = "10000";
        req.param.PlanNo = "2008";
        req.param.PlanNum = "2";
        req.param.SubmitDate = DateUtil.getDate8();

        T2008Req.Param.Record record =T2008Req.getRecord();

        record.Amt = "5000";
        record.PlanDate = DateUtil.getDate8();
        record.PlanDetailNO = "200801";
        record.Purpose = "原因1";
        record.ToAcct = "66666";
        record.ToAcctName = "Daniel";
        record.Remark = "hello";
        record.ToBankName = "建行";
        req.param.recordList.add(record);

        record = T2008Req.getRecord();
          record.Amt = "5000";
        record.PlanDate = DateUtil.getDate8();
        record.PlanDetailNO = "200802";
        record.Purpose = "原因2";
        record.ToAcct = "666665";
        record.ToAcctName = "DanielZhang";
        record.Remark = "hello";
        record.ToBankName = "中行";
        req.param.recordList.add(record);

        testClientService(req.toFDCDatagram());

    }

}
