package fdc.gateway.test;

import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.fdc.T200.T2004Req;
import fdc.gateway.service.IMessageService;
import fdc.gateway.service.XSocketService;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.IBlockConnect;
import fdc.gateway.xsocket.client.impl.ClientFactory;
import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ÉÏÎç3:06
 * To change this template use File | Settings | File Templates.
 */
public class ClientBaseTest {

     public static  void testClientService(String data) {
            try {
            XSocketService xSocketService = new XSocketService();
            String datagram = xSocketService.sendAndRecvByBlockConn(data);
            IMessageService messageService = new ClientMessageService();
            messageService.handleMessage(datagram);

        } catch (Exception e) {
            System.out.println("¡¾ÍøÂçÒì³££¡¡¿");
        }
    }
}
