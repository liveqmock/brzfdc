package fdc.gateway.test;

import fdc.gateway.domain.fdc.T200.T2007Req;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class Client2007Test extends ClientBaseTest {

    public static void main(String[] args) {

        T2007Req req = new T2007Req();
        req.head.OpCode = "2007";
        req.head.BankCode = "105";

        req.param.Acct = "888888888888";
        req.param.BuyerName = "leyee";
        req.param.BuyerAcct = "5555555555";
        req.param.BuyerBankName = "建行";
        req.param.BuyerIDType = "1";
        req.param.BuyerIDCode = "3710000000000";
        req.param.ContractNum = "999999";
        req.param.TotalAmt = "10000";
        req.param.HouseAddress = "市南";
        req.param.EndReason = "无缘无故";
        req.param.TransBuyerAmt = "100";
        testClientService(req.toFDCDatagram());

    }

}
