package fdc.xsocket.client.impl;

import fdc.xsocket.client.ConnectClient;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import java.io.IOException;

/**
 * �ͻ��˽��շ������Ϣ
 * @author zxb
 */
public class XSocketClient extends ConnectClient {

   // private static final Logger logger = LoggerFactory.getLogger(XSocketClient.class);
    private INonBlockingConnection nbc;    //����������

    public XSocketClient(String hostIP, int port) throws IOException {
        super(hostIP, port);
        nbc = new NonBlockingConnection(serverIP, serverPort, new ClientHandler());
        nbc.setIdleTimeoutMillis(timeoutMills);
        nbc.setEncoding("GBK"); // TODO  hardcoding
        nbc.setAutoflush(true);  // TODO �����Զ���ջ���
    }

    /**
     * ��������
     * @param dataContent
     * @return
     * @throws java.io.IOException
     */
    public boolean sendData(String dataContent) throws IOException {
        if(nbc == null) {
            throw new RuntimeException("����δ������");
        }else {
             nbc.write(dataContent);
        }
        return true;
    }

    /**
     * �رտͻ�������
     * @return
     * @throws java.io.IOException
     */
    public boolean close() throws IOException {
          if(nbc == null) {
            throw new RuntimeException("����δ������");
        }else {
             nbc.close();
              nbc = null;
        }
        return true;
    }

}
