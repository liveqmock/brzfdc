package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.BufferUnderflowException;

/**
 * 客户端接收服务端信息
 *
 * @author zxb
 */
public class XSocketBlockClient extends ConnectClient implements IConnectHandler{

    private static final Logger logger = LoggerFactory.getLogger(XSocketBlockClient.class);
    private IBlockingConnection bc;   //  非阻塞连接

    public XSocketBlockClient(String serverIP, int serverPort, int cnctTimeoutMills, int idleTimeoutMills) throws IOException {
        super(serverIP, serverPort);
        INonBlockingConnection nbc = new NonBlockingConnection(serverIP, serverPort, this);
        bc = new BlockingConnection(nbc);
        //bc = new BlockingConnection(serverIP,serverPort);
        bc.setIdleTimeoutMillis(idleTimeoutMills);
        // TODO 确认是否有必要精确到多个超时
        // bc.setReadTimeoutMillis(cnctTimeoutMills);
        bc.setConnectionTimeoutMillis(cnctTimeoutMills);
        bc.setEncoding("GBK");
        bc.setAutoflush(true);  //  设置自动清空缓存
    }

        @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
       String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("【本地客户端】与远程主机:" + remoteName + "建立连接。");
        return true;
    }

    /**
     * 发送并接收数据
     *
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public boolean sendDataUntilRcv(String dataContent) throws SocketTimeoutException,IOException {

        sendData(dataContent);

        String gramLength = bc.readStringByDelimiter("\r\n");
        logger.info("【本地客户端】接收报文内容长度：" + gramLength);
        String datagram = bc.readStringByLength(Integer.parseInt(gramLength), "GBK");
        logger.info("【本地客户端】接收报文内容：" + datagram);
        return true;
    }

    @Override
    public boolean sendData(String dataContent) throws IOException {
        if (bc == null || !bc.isOpen()) {
            throw new RuntimeException("链接未建立！");
        } else {
            logger.info("【本地客户端】发送报文：" + dataContent);
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
