package fdc.gateway.xsocket.server.impl;


import fdc.utils.StringUtil;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.fdc.T200.T2008Req;
import fdc.xmlbean.fdc.T200.T2008Res;
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
    private boolean isConnected = false;
    private MessageHandler messageHandler = new MessageHandler();

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

        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(dataContent, "<OpCode>", "</OpCode>");

        if("2008".equalsIgnoreCase(opCode)) {
            try {
            Object obj = BaseBean.toObject(T2008Req.class, dataContent);
            T2008Req req = (T2008Req)obj;
            }catch (Exception e) {
                logger.error(e.getMessage());
                // TODO JobLog
            }

            T2008Res res = new T2008Res();
            // "0000"��ʾ�ɹ�
            res.head.RetCode = "0000";
            String resContent = res.toFDCDatagram();
            logger.info("�����ط���ˡ����ͱ�������:" + resContent);
            logger.info("�����ط���ˡ����ͱ��ĳ���:" + nbc.write(resContent));
        }
        nbc.flush();
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
