package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0003Req;
import fdc.gateway.domain.fdc.T000.T0007Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0007Test extends ClientBaseTest {

    public static void test() throws Exception {

        T0007Req req = new T0007Req();
        req.head.OpCode = "0007";
        req.head.BankCode = "313";

        T0007Req.Param.Record record = T0007Req.getRecord();
        record.Date = DateUtil.getDate8();
        record.Time = DateUtil.getTime6();
        record.Flag = "1";
        record.Type = "01";
        record.Amt = "1000";
        record.ContractNum = "100000000088";
        record.ToAcct = "555555555";
        record.BankSerial = DateUtil.getDatetime14();
        record.ToBankName = "��������";
        record.Purpose = "����";
        testClientService(req.toFDCDatagram());

    }

}
