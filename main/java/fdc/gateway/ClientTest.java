package fdc.gateway;

import fdc.gateway.domain.fdc.T200.T2004Req;
import fdc.gateway.xsocket.client.IBlockConnect;
import fdc.gateway.xsocket.client.impl.ClientFactory;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:06
 * To change this template use File | Settings | File Templates.
 */
public class ClientTest {
    public static void main(String[] args) {
        try {
            T2004Req req = new T2004Req();
            req.head.OpCode = "2004";
            req.head.BankCode = "105";
            req.param.Acct = "123456789";
            req.param.AcctName = "Bill";
            req.param.Amt = "10000";
            req.param.BankSerial = DateUtil.getDatetime14();
            req.param.Date = DateUtil.getDate8();
            req.param.Flag = "0";
            req.param.PlanDetailNO = "1000001";
            req.param.Purpose = "首付款";
            req.param.Time = DateUtil.getTime6();
            req.param.ToAcct = "987654321";
            req.param.Type = "02";
            req.param.ToAcctName = "Gates";
            req.param.ToBankName = "日照银行";

            IBlockConnect client = ClientFactory.XSocket.getBlockClient();
            //Thread.currentThread().sleep(6000);
             client.sendDataUntilRcv(req.toFDCDatagram());
            //client.sendData(req.toFDCDatagram());
             client.close();
        }catch (Exception e) {
            // 链接超时 SocketTimeoutException  Idle超时 ExtendedClosedChannelException
            System.out.println("【网络超时！】");
        }
    }
}
