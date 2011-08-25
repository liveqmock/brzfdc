package fdc.gateway.test;

import fdc.gateway.domain.T000.T0007Req;
import platform.service.SystemService;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0007Test extends ClientBaseTest {

    public static void test() throws Exception {

        T0007Req req = new T0007Req();
        req.head.OpCode = "0007";
        req.head.BankCode = "313";

        T0007Req.Param.Record record = T0007Req.getRecord();
        record.Date = SystemService.getSdfdate8();
        record.Time = SystemService.getSdftime6();
        record.Flag = "1";
        record.Type = "01";
        record.Amt = "1000";
        record.ContractNum = "100000000088";
        record.ToAcct = "555555555";
        record.BankSerial = SystemService.getDatetime14();
        record.ToBankName = "日照银行";
        record.Purpose = "购房";
        testClientService(req.toFDCDatagram());

    }

}
