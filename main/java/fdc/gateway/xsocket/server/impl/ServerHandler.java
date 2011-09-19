package fdc.gateway.xsocket.server.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.service.impl.ServerMessageService;
import fdc.gateway.xsocket.server.IServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 服务端数据处理类
 *
 * @author zxb
 */
@Component
public class ServerHandler implements IServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    @Autowired
    private ServerMessageService serverMessageService;

    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("【本地服务端】远程主机: " + remoteName + "与本地主机建立连接！");
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        logger.info("【本地服务端】远程主机与本地主机断开连接！");
        return true;
    }

    /**
     * 得到数据
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {

        String dataLength = nbc.readStringByDelimiter("\r\n");
        logger.info("【本地服务端】接收到报文长度: " + dataLength);

        String datagram = nbc.readStringByLength(Integer.parseInt(dataLength), "GBK");
        logger.info("【本地服务端】接收到报文内容: " + datagram);

        // 处理接收到的报文，并生成响应报文
        String responseMsg = serverMessageService.handleMessage(datagram);

        responseMsg = responseMsg.getBytes().length + "\r\n" +responseMsg;

        int sendDatagramLength = nbc.write(responseMsg, "GBK");
        logger.info("【本地服务端】发送报文内容:" + responseMsg);
        logger.info("【本地服务端】发送报文长度:" + sendDatagramLength);
        nbc.flush();
        return true;
    }

    /**
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("【本地服务端】Idle 超时。");
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
        return true;
    }

    public ServerMessageService getServerMessageService() {
        return serverMessageService;
    }

    public void setServerMessageService(ServerMessageService serverMessageService) {
        this.serverMessageService = serverMessageService;
    }
}
