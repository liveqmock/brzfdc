package fdc.gateway.test;

import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.T200.T2003Req;
import fdc.gateway.service.IMessageService;
import fdc.gateway.service.XSocketService;
import fdc.gateway.service.impl.ClientMessageService;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:06
 * To change this template use File | Settings | File Templates.
 */
public class ClientBaseTest {

    public static void testClientService(String data) throws Exception {
            XSocketService xSocketService = new XSocketService();
            String datagram = xSocketService.sendAndRecvByBlockConn(data);
            IMessageService messageService = new ClientMessageService();
            messageService.handleMessage(datagram);
    }

    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>2003</OpCode>" +
                "<OpDate>20110825</OpDate><OpTime>121021</OpTime><BankCode>313</BankCode></Head>" +
                "<Param><ContractNum>201000145114</ContractNum><Acct>12345678</Acct>" +
                "<AcctName>�ൺΰҵ���޹�˾</AcctName><BuyerName>����</BuyerName><BuyerAcct>0000000</BuyerAcct>" +
                "<BuyerBankName>���������ൺ����</BuyerBankName><BuyerIDType>���֤</BuyerIDType>" +
                "<BuyerIDCode>37020519810325551X</BuyerIDCode><TotalAmt>84395800</TotalAmt>" +
                "<Deposit>000</Deposit><DownPay>000</DownPay><Mortgage>84395800</Mortgage>" +
                "<HouseAddress></HouseAddress><HouseType>01</HouseType><HouseNO>�ķ���Ƽ��·16�ż�3��¥601</HouseNO>" +
                "<OverAmt>000</OverAmt><TreasuryName>" +
                "</TreasuryName><TreasuryAcct></TreasuryAcct><TreasuryBankName></TreasuryBankName>" +
                "</Param></root>";
        T2003Req req = (T2003Req)BaseBean.toObject(T2003Req.class, str);
        System.out.println(req.param.HouseNO);
    }
}
