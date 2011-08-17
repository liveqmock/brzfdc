package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import java.io.IOException;

/**
 * 客户端接收服务端信息
 *
 * @author zxb
 */
public class XSocketClient extends ConnectClient {

    private static final Logger logger = LoggerFactory.getLogger(XSocketClient.class);
    private INonBlockingConnection nbc;   //  非阻塞连接

    public XSocketClient(String hostIP, int port) throws IOException {
        super(hostIP, port);
        nbc = new NonBlockingConnection(serverIP, serverPort, new ClientHandler());
        nbc.setIdleTimeoutMillis(timeoutMills);
        nbc.setEncoding("GBK");
        nbc.setAutoflush(true);  //  设置自动清空缓存
    }

    /**
     * 发送数据
     *
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public boolean sendData(String dataContent) throws IOException {
        if (nbc == null || !nbc.isOpen()) {
            throw new RuntimeException("链接未建立！");
        } else {
            logger.info("【本地客户端】发送报文:"+dataContent);
            nbc.write(dataContent);
        }
        return true;
    }

    /**
     * 关闭客户端链接
     *
     * @return
     * @throws java.io.IOException
     */
    public boolean close() throws IOException {
        if (nbc != null && nbc.isOpen()) {
            nbc.close();
            nbc = null;
        }
        return true;
    }

}
