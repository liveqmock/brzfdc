package fdc.gateway.test;

import fdc.gateway.domain.T000.T0004Req;
import platform.service.SystemService;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0004Test extends ClientBaseTest{

    public static void test() throws Exception {

        T0004Req req = new T0004Req();
        req.head.OpCode = "0004";
        req.head.BankCode = "313";
        req.param.Date = "20110820";
        req.param.Time = "101010";
        req.param.Acct = "123456789";
        req.param.AcctName = "Bill";
        req.param.BankSerial = SystemService.getDatetime14();
        req.param.Reason = "ԭ��";
        testClientService(req.toFDCDatagram());

    }

}