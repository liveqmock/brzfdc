package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
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
    private IBlockingConnection bc;   //  非阻塞连接

    public XSocketClient(String hostIP, int port) throws IOException {
        super(hostIP, port);
        bc = new BlockingConnection(serverIP, serverPort);
        bc.setIdleTimeoutMillis(timeoutMills);
        bc.setEncoding("GBK");
        bc.setAutoflush(true);  //  设置自动清空缓存
    }

    /**
     * 发送数据
     *
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public boolean sendDataUntilRcv(String dataContent) throws IOException {
        sendData(dataContent);
        String gramLength = bc.readStringByDelimiter("\r\n");
        logger.info("【本地客户端】接收报文内容长度："+gramLength);
        String datagram = bc.readStringByLength(Integer.parseInt(gramLength), "GBK");
        logger.info("【本地客户端】接收报文内容："+datagram);
        return true;
    }

    @Override
    public boolean sendData(String dataContent) throws IOException {
          if (bc == null || !bc.isOpen()) {
            throw new RuntimeException("链接未建立！");
        } else {
            logger.info("【本地客户端】发送报文："+dataContent);
            bc.write(dataContent);
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
        if (bc != null && bc.isOpen()) {
            bc.close();
            bc = null;
        }
        return true;
    }

}
