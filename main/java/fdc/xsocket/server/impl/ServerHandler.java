package fdc.xsocket.server.impl;


import fdc.xsocket.server.IServerHandler;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * ��������ݴ�����
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    //private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    /**
     * ���ӵĳɹ�ʱ�Ĳ���
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
       System.out.println("remoteName :   " + remoteName + "   has connected ��");
        return true;
    }

    /**
     * ���ӶϿ�ʱ�Ĳ���
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        //String remoteName = nbc.getRemoteAddress().getHostName();
        System.out.println("  disconnected ��");
        return true;
    }

    /**
     * �õ�����
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException,BufferUnderflowException {
        int dataLength = nbc.available();
        String dataContent = nbc.readStringByLength(dataLength);
        System.out.println(dataContent);
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
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * ���ӳ�ʱ�����¼�
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
