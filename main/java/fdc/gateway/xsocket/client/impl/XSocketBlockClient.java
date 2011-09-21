package fdc.gateway.xsocket.client.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.ConnectClient;
import fdc.gateway.xsocket.crypt.des.DesCrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;
import pub.platform.advance.utils.PropertyManager;
import sun.misc.BASE64Encoder;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.BufferUnderflowException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Formatter;

/**
 * �ͻ��˽��շ������Ϣ
 *
 * @author zxb
 */
public class XSocketBlockClient extends ConnectClient implements IConnectHandler {

    private static final Logger logger = LoggerFactory.getLogger(XSocketBlockClient.class);
    private IBlockingConnection bc;   //  ����������
    private INonBlockingConnection nbc;
    private boolean isCrypt = false;
    private DesCrypter crypter;

    public XSocketBlockClient(String serverIP, int serverPort, int timeoutMills) throws IOException {
        super(serverIP, serverPort);
        nbc = new NonBlockingConnection(serverIP, serverPort, this);
        bc = new BlockingConnection(nbc);
        bc.setConnectionTimeoutMillis(timeoutMills);
        bc.setEncoding("GBK");
        bc.setAutoflush(true);  //  �����Զ���ջ���
        if ("DES".equalsIgnoreCase(PropertyManager.getProperty("fdc.socket.data.crypt.type"))) {
            try {
                crypter = DesCrypter.getInstance();
            } catch (Exception e) {
                logger.error("�޷�ʵ���������࣡", e.getMessage());
                throw new RuntimeException("�޷�ʵ���������࣡" + e.getMessage());
            }
            isCrypt = true;
        }
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
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public String sendDataUntilRcv(String dataContent) throws Exception {

        String toSendData = null;
        logger.info("�����ؿͻ��ˡ����ͱ��ģ�" + dataContent);

        if (isCrypt) {
            // ����
            String miContent = crypter.encrypt(dataContent);
            toSendData = miContent.getBytes().length + "\r\n" + miContent;
        } else {
            toSendData = dataContent.getBytes().length + "\r\n" + dataContent;
        }
        logger.info("�����ؿͻ��ˡ��������ģ�" + toSendData);
        if (sendData(toSendData)) {
            String gramLength = bc.readStringByDelimiter("\r\n");
            logger.info("�����ؿͻ��ˡ����ձ������ݳ��ȣ�" + gramLength);

            String dataGram = null;
            if (isCrypt) {
                String strDataGram = bc.readStringByLength(Integer.parseInt(gramLength));
                dataGram = DesCrypter.getInstance().decrypt(strDataGram);
            } else {
                dataGram = bc.readStringByLength(Integer.parseInt(gramLength), "GBK");
            }
            logger.info("�����ؿͻ��ˡ����ձ������ݣ�" + dataGram);
            return dataGram;
        }
        return null;
    }

    @Override
    public boolean sendData(String dataContent) throws IOException {
        if (bc == null || !bc.isOpen()) {
            throw new RuntimeException("δ�������ӣ�");
        } else {
            bc.write(dataContent);
            bc.flush();
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
        if (nbc != null && nbc.isOpen()) {
            nbc.close();
            nbc = null;
        }
        return true;
    }
}
