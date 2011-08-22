package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import java.io.IOException;

/**
 * �ͻ��˽��շ������Ϣ
 *
 * @author zxb
 */
public class XSocketClient extends ConnectClient {

    private static final Logger logger = LoggerFactory.getLogger(XSocketClient.class);
    private IBlockingConnection bc;   //  ����������

    public XSocketClient(String hostIP, int port) throws IOException {
        super(hostIP, port);
        bc = new BlockingConnection(serverIP, serverPort);
        bc.setIdleTimeoutMillis(timeoutMills);
        bc.setEncoding("GBK");
        bc.setAutoflush(true);  //  �����Զ���ջ���
    }

    /**
     * ��������
     *
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public boolean sendDataUntilRcv(String dataContent) throws IOException {
        sendData(dataContent);
        String gramLength = bc.readStringByDelimiter("\r\n");
        logger.info("�����ؿͻ��ˡ����ձ������ݳ��ȣ�"+gramLength);
        String datagram = bc.readStringByLength(Integer.parseInt(gramLength), "GBK");
        logger.info("�����ؿͻ��ˡ����ձ������ݣ�"+datagram);
        return true;
    }

    @Override
    public boolean sendData(String dataContent) throws IOException {
          if (bc == null || !bc.isOpen()) {
            throw new RuntimeException("����δ������");
        } else {
            logger.info("�����ؿͻ��ˡ����ͱ��ģ�"+dataContent);
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
