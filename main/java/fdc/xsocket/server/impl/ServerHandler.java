package fdc.xsocket.server.impl;


import fdc.xsocket.server.IServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * ��������ݴ�����
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private boolean isConnected = false;

    /**
     * ���ӵĳɹ�ʱ�Ĳ���
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        isConnected = true;
        logger.info("�����ط���ˡ�Զ������: " + remoteName + "�뱾�������������ӣ�");
        return true;
    }

    /**
     * ���ӶϿ�ʱ�Ĳ���
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        logger.info("�����ط���ˡ�Զ�������뱾�������Ͽ����ӣ�");
        isConnected = false;
        return true;
    }

    /**
     * �õ�����
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        int dataLength = nbc.available();
        String dataContent = nbc.readStringByLength(dataLength);
        logger.info("�����ط���ˡ����յ�����: " + dataContent);
        // �õ������룬���ݽ����뽫xmlת������Ӧ��Tia ����
        /* String opCode = StringUtil.getSubstrBetweenStrs(dataContent, "<OpCode>", "</OpCode>");
        if("2008".equalsIgnoreCase(opCode)) {
            Object obj = BaseBean.toObject(T2008Tia.class, dataContent);
            T2008Tia tia = (T2008Tia)obj;
            // TODO

        }
       // nbc.write(" arrived OK : "+dataContent);
        nbc.flush();*/
        return true;
    }

    /**
     * ������ʱ�Ĵ����¼�
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("�����ط���ˡ���Զ�������������ӳ�ʱ��");
        return true;
    }

    /**
     * ���ӳ�ʱ�����¼�
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("�����ؿͻ��ˡ���Զ���������ӳ�ʱ��");
        return true;
    }

    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        logger.error("�����ؿͻ��ˡ���Զ���������ӷ����쳣��");
        return false;
    }
}
