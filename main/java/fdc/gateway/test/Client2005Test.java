package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0005Req;
import fdc.gateway.domain.fdc.T200.T2005Req;
import platform.service.SystemService;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client2005Test extends ClientBaseTest {

    public static void test() throws Exception {

        T2005Req req = new T2005Req();
        req.head.OpCode = "2005";
        req.head.BankCode = "313";
        req.param.Date = SystemService.getSdfdate8();
        req.param.Time = SystemService.getSdftime6();
        req.param.Flag = "1";
        req.param.Type = "01";
        req.param.Amt = "1000";
        req.param.ContractNum = "100000000088";
        req.param.ToAcct = "555555555";
        req.param.BankSerial = SystemService.getDatetime14();
        req.param.ToBankName = "日照银行";
        req.param.Purpose = "购房";
        testClientService(req.toFDCDatagram());

    }

}
