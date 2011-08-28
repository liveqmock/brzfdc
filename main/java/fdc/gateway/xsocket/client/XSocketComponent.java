package fdc.gateway.xsocket.client;

import fdc.gateway.domain.CommonRes;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.impl.ClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-23
 * Time: ����11:19
 * To change this template use File | Settings | File Templates.
 */
@Component
public class XSocketComponent {

    /**
     * ���ͱ��ģ����ؽ��յ�����Ӧ����
     * @param datagram
     * @return
     * @throws java.io.IOException
     */
    public String sendAndRecvDataByBlockConn(String datagram) throws IOException {
        IBlockConnect client = ClientFactory.XSocket.getBlockClient();
        String recvDatagram = client.sendDataUntilRcv(datagram);
        client.close();
        return recvDatagram;
    }
}
