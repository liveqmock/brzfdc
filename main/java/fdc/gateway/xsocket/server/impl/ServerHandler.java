package fdc.gateway.xsocket.server.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.service.impl.ServerMessageService;
import fdc.gateway.xsocket.server.IServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 服务端数据处理类
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private boolean isConnected = false;
    private IMessageService messageService = new ServerMessageService();

    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        isConnected = true;
        logger.info("【本地服务端】远程主机: " + remoteName + "与本地主机建立连接！");
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        logger.info("【本地服务端】远程主机与本地主机断开连接！");
        isConnected = false;
        return true;
    }

    /**
     * 得到数据
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
      //  int dataLength = nbc.available();
      //  String dataContent = nbc.readStringByLength(dataLength);

        String dataLength = nbc.readStringByDelimiter("\r\n");
        logger.info("【本地服务端】接收到报文长度: " + dataLength);

         String datagram = nbc.readStringByLength(Integer.parseInt(dataLength), "GBK");
        //String datagram = nbc.readStringByLength(Integer.parseInt(dataLength));
        logger.info("【本地服务端】接收到报文内容: " + datagram);

        // 处理接收到的报文，并生成响应报文
        String responseMsg = messageService.handleMessage(datagram);

        logger.info("【本地服务端】发送报文内容:" + responseMsg);
        logger.info("【本地服务端】发送报文长度:" + nbc.write(responseMsg, "GBK"));
        nbc.flush();
        return true;
    }

    /**
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("【本地服务端】与远程主机空闲连接超时。");
        return true;
    }

    /**
     * 连接超时处理事件
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("【本地客户端】与远程主机连接超时。");
        return true;
    }

    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        logger.error("【本地客户端】与远程主机连接发生异常。");
        return false;
    }
}
