package fdc.gateway.xsocket.client.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.IClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 客户端定数据处理类
 *
 * @author zxb
 */
public class ClientHandler implements IClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private IMessageService messageService = new ClientMessageService();
    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("【本地客户端】与远程主机:" + remoteName + "建立连接。");
        return true;
    }

    /**
     * 接收到数据时候的处理
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        String dataContent = null;
        dataContent = nbc.readStringByLength(nbc.available());
        logger.info("【本地客户端】接收到报文:" + dataContent);
        messageService.handleMessage(dataContent);
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        // TODO ----------------------------------
        if (nbc == null || !nbc.isOpen()) {
            logger.info("【本地客户端】NBC == null");
        }
        //-------------------------------------------
        logger.info("【本地客户端】与远程主机断开了连接。");

        return false;
    }

    @Override
    public boolean onIdleTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        logger.error("【本地客户端】与远程主机空闲连接超时。");
        return false;
    }

    @Override
    public boolean onConnectionTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        logger.error("【本地客户端】与远程主机连接超时。");
        return false;
    }


    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        logger.error("【本地客户端】与远程主机连接发生异常。");
        return false;
    }
}
