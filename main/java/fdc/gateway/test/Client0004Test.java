package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0003Req;
import fdc.gateway.domain.fdc.T000.T0004Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: …œŒÁ3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0004Test extends ClientBaseTest{

    public static void main(String[] args) {

        T0004Req req = new T0004Req();
        req.head.OpCode = "0004";
        req.head.BankCode = "105";
        req.param.Date = "20110820";
        req.param.Time = "101010";
        req.param.Acct = "123456789";
        req.param.AcctName = "Bill";
        req.param.BankSerial = DateUtil.getDatetime14();
        req.param.Reason = "‘≠“Ú";
        testClientService(req.toFDCDatagram());

    }

}
