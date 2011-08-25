package fdc.gateway.xsocket.client.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.BufferUnderflowException;

/**
 * �ͻ��˽��շ������Ϣ
 *
 * @author zxb
 */
public class XSocketBlockClient extends ConnectClient implements IConnectHandler{

    private static final Logger logger = LoggerFactory.getLogger(XSocketBlockClient.class);
    private IBlockingConnection bc;   //  ����������

    public XSocketBlockClient(String serverIP, int serverPort, int timeoutMills) throws IOException {
        super(serverIP, serverPort);
        INonBlockingConnection nbc = new NonBlockingConnection(serverIP, serverPort, this);
        bc = new BlockingConnection(nbc);
        bc.setConnectionTimeoutMillis(timeoutMills);
        bc.setEncoding("GBK");
        bc.setAutoflush(true);  //  �����Զ���ջ���
    }

        @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
       String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("�����ؿͻ��ˡ���Զ������:" + remoteName + "�������ӡ�");
        return true;
    }

    /**
     * ���Ͳ���������
     *
     *
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public String sendDataUntilRcv(String dataContent) throws SocketTimeoutException,IOException {

        if(sendData(dataContent)) {

        String gramLength = bc.readStringByDelimiter("\r\n");
        logger.info("�����ؿͻ��ˡ����ձ������ݳ��ȣ�" + gramLength);
        String datagram = bc.readStringByLength(Integer.parseInt(gramLength), "GBK");
        logger.info("�����ؿͻ��ˡ����ձ������ݣ�" + datagram);
        return datagram;
        }
        return null;
    }

    @Override
    public boolean sendData(String dataContent) throws IOException {
        if (bc == null || !bc.isOpen()) {
            throw new RuntimeException("δ�������ӣ�");
        } else {
            logger.info("�����ؿͻ��ˡ����ͱ��ģ�" + dataContent);
            bc.write(dataContent);
        }
        return true;
    }

    /**
     * �رտͻ�������
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