package fdc.xsocket.client.impl;

import fdc.xsocket.client.IClientHandler;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 客户端定数据处理类
 * @author zxb
 *
 */
public class ClientHandler implements IClientHandler {

   // private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,BufferUnderflowException {
        String  remoteName=nbc.getRemoteAddress().getHostName();
        System.out.println("remoteName "+remoteName +" has connected ！");
       return true;
    }

        /**
     *
     * 接收到数据时候的处理
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException,BufferUnderflowException {
         String dataContent = null;
        dataContent = nbc.readStringByLength(nbc.available());
       // logger.info(dataContent);
        System.out.println(dataContent);
        // TODO handle Data
         return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        // TODO Auto-generated method stub
       return false;
    }

    @Override
    public boolean onIdleTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        // TODO
        return false;
    }

    @Override
    public boolean onConnectionTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
