package fdc.gateway.test;

import fdc.gateway.domain.fdc.T000.T0003Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: …œŒÁ3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client0003Test extends ClientBaseTest{

    public static void main(String[] args) {

        T0003Req req = new T0003Req();
        req.head.OpCode = "0003";
        req.head.BankCode = "105";
        req.param.Acct = "123456789";
        req.param.AcctName = "Bill";
        req.param.BankSerial = DateUtil.getDatetime14();
        req.param.Reason = "‘≠“Ú";
        testClientService(req.toFDCDatagram());

    }

}
