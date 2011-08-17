package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.IClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * �ͻ��˶����ݴ�����
 *
 * @author zxb
 */
public class ClientHandler implements IClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    /**
     * ���ӵĳɹ�ʱ�Ĳ���
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("�����ؿͻ��ˡ���Զ������:" + remoteName + "�������ӡ�");
        return true;
    }

    /**
     * ���յ�����ʱ��Ĵ���
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        String dataContent = null;
        dataContent = nbc.readStringByLength(nbc.available());
        logger.info("�����ؿͻ��ˡ����յ�����:" + dataContent);
        // TODO handle Data
        return true;
    }

    /**
     * ���ӶϿ�ʱ�Ĳ���
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        // TODO ----------------------------------
        if (nbc == null || !nbc.isOpen()) {
            logger.info("�����ؿͻ��ˡ�NBC == null");
        }
        //-------------------------------------------
        logger.info("�����ؿͻ��ˡ���Զ�������Ͽ������ӡ�");

        return false;
    }

    @Override
    public boolean onIdleTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        logger.error("�����ؿͻ��ˡ���Զ�������������ӳ�ʱ��");
        return false;
    }

    @Override
    public boolean onConnectionTimeout(INonBlockingConnection iNonBlockingConnection) throws IOException {
        logger.error("�����ؿͻ��ˡ���Զ���������ӳ�ʱ��");
        return false;
    }


    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        logger.error("�����ؿͻ��ˡ���Զ���������ӷ����쳣��");
        return false;
    }
}
