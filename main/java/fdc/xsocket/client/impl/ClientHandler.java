package fdc.xsocket.client.impl;

import fdc.xsocket.client.IClientHandler;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * �ͻ��˶����ݴ�����
 * @author zxb
 *
 */
public class ClientHandler implements IClientHandler {

   // private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    /**
     * ���ӵĳɹ�ʱ�Ĳ���
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,BufferUnderflowException {
        String  remoteName=nbc.getRemoteAddress().getHostName();
        System.out.println("remoteName "+remoteName +" has connected ��");
       return true;
    }

        /**
     *
     * ���յ�����ʱ��Ĵ���
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
     * ���ӶϿ�ʱ�Ĳ���
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
