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
 * ��������ݴ�����
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private IMessageService messageService = new ServerMessageService();

    /**
     * ���ӵĳɹ�ʱ�Ĳ���
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("�����ط���ˡ�Զ������: " + remoteName + "�뱾�������������ӣ�");
        return true;
    }

    /**
     * ���ӶϿ�ʱ�Ĳ���
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        logger.info("�����ط���ˡ�Զ�������뱾�������Ͽ����ӣ�");
        return true;
    }

    /**
     * �õ�����
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {

        String dataLength = nbc.readStringByDelimiter("\r\n");
        logger.info("�����ط���ˡ����յ����ĳ���: " + dataLength);

        String datagram = nbc.readStringByLength(Integer.parseInt(dataLength), "GBK");
        logger.info("�����ط���ˡ����յ���������: " + datagram);

        // ������յ��ı��ģ���������Ӧ����
        String responseMsg = messageService.handleMessage(datagram);

        int sendDatagramLength = nbc.write(responseMsg, "GBK");
        logger.info("�����ط���ˡ����ͱ�������:" + responseMsg);
        logger.info("�����ط���ˡ����ͱ��ĳ���:" + sendDatagramLength);
        nbc.flush();
        return true;
    }

    /**
     * ������ʱ�Ĵ����¼�
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("�����ط���ˡ�Idle ��ʱ��");
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
        return true;
    }
}
