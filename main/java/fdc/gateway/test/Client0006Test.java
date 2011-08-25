package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0006Req;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ÉÏÎç3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0006Test extends ClientBaseTest{

    public static void test() throws Exception {

        T0006Req req = new T0006Req();
        req.head.OpCode = "0006";
        req.head.BankCode = "313";
        req.param.Acct = "123456789";
        req.param.AcctName = "Bill";
        req.param.Status = "1";
        req.param.Balance = "10000";
        req.param.LockAmt = "2000";
        testClientService(req.toFDCDatagram());

    }

}
