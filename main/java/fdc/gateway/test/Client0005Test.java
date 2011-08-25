package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0003Req;
import fdc.gateway.domain.fdc.T000.T0005Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ÉÏÎç3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0005Test extends ClientBaseTest{

    public static void test() throws Exception {

        T0005Req req = new T0005Req();
        req.head.OpCode = "0005";
        req.head.BankCode = "313";
        req.param.Acct = "123456789";
        req.param.AcctName = "Bill";
        req.param.BankSerial = DateUtil.getDatetime14();
        req.param.Amt="10000";
        req.param.Purpose="»ýÐî";
        req.param.Date = "20110620";
        req.param.Time = "111111";
        testClientService(req.toFDCDatagram());

    }

}
