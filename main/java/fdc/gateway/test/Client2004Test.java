package fdc.gateway.test;

import fdc.gateway.domain.T200.T2004Req;
import platform.service.SystemService;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client2004Test extends ClientBaseTest {

    public static void test() throws Exception {

            T2004Req req = new T2004Req();
            req.head.OpCode = "2004";
            req.head.BankCode = "313";
            req.param.Acct = "123456789";
            req.param.AcctName = "Bill";
            req.param.Amt = "10000";
            req.param.BankSerial = SystemService.getDatetime14();
            req.param.Date = SystemService.getSdfdate8();
            req.param.Flag = "0";
            req.param.PlanDetailNO = "1000001";
            req.param.Purpose = "�׸���";
            req.param.Time = SystemService.getSdftime6();
            req.param.ToAcct = "987654321";
            req.param.Type = "02";
            req.param.ToAcctName = "Gates";
            req.param.ToBankName = "��������";
            testClientService(req.toFDCDatagram());

    }

}