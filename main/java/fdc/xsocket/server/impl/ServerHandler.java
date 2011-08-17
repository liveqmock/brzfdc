package fdc.xsocket.server.impl;


import fdc.xsocket.server.IServerHandler;
import org.xsocket.connection.INonBlockingConnection;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 服务端数据处理类
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    //private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
       System.out.println("remoteName :   " + remoteName + "   has connected ！");
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        //String remoteName = nbc.getRemoteAddress().getHostName();
        System.out.println("  disconnected ！");
        return true;
    }

    /**
     * 得到数据
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException,BufferUnderflowException {
        int dataLength = nbc.available();
        String dataContent = nbc.readStringByLength(dataLength);
        System.out.println(dataContent);
        // 得到交易码，根据交易码将xml转换成相应的Tia 对象。
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
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 连接超时处理事件
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
