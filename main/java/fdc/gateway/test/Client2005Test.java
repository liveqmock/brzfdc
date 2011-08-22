package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0007Req;
import fdc.gateway.domain.fdc.T100.T1002Req;
import fdc.gateway.domain.fdc.T200.T2005Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client2005Test extends ClientBaseTest {

    public static void main(String[] args) {

        T2005Req req = new T2005Req();
        req.head.OpCode = "2005";
        req.head.BankCode = "105";

        T0007Req.Param.Record record = T0007Req.getRecord();
        record.Date = DateUtil.getDate8();
        record.Time = DateUtil.getTime6();
        record.Flag = "1";
        record.Type = "01";
        record.Amt = "1000";
        record.ContractNum = "100000000088";
        record.ToAcct = "555555555";
        record.BankSerial = DateUtil.getDatetime14();
        record.ToBankName = "日照银行";
        record.Purpose = "购房";
        testClientService(req.toFDCDatagram());

    }

}
