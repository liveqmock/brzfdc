package fdc.gateway.service;

import fdc.gateway.xsocket.client.IBlockConnect;
import fdc.gateway.xsocket.client.impl.ClientFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-23
 * Time: ÉÏÎç11:19
 * To change this template use File | Settings | File Templates.
 */
@Service
public class XSocketService {

    public String sendAndRecvByBlockConn(String datagram) throws IOException {
        IBlockConnect client = ClientFactory.XSocket.getBlockClient();
        String recvDatagram = client.sendDataUntilRcv(datagram);
        client.close();
        return recvDatagram;
    }
}
