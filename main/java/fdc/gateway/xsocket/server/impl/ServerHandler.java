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
 * 服务端数据处理类
 *
 * @author zxb
 */
public class ServerHandler implements IServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private boolean isConnected = false;
    private MessageHandler messageHandler = new MessageHandler();

    /**
     * 连接的成功时的操作
     */
    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException,
            BufferUnderflowException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        isConnected = true;
        logger.info("【本地服务端】远程主机: " + remoteName + "与本地主机建立连接！");
        return true;
    }

    /**
     * 连接断开时的操作
     */
    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        logger.info("【本地服务端】远程主机与本地主机断开连接！");
        isConnected = false;
        return true;
    }

    /**
     * 得到数据
     */
    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException {
        int dataLength = nbc.available();
        String dataContent = nbc.readStringByLength(dataLength);
        logger.info("【本地服务端】接收到报文: " + dataContent);

        // 得到交易码，根据交易码将xml转换成相应的接口对象。
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
            // "0000"表示成功
            res.head.RetCode = "0000";
            String resContent = res.toFDCDatagram();
            logger.info("【本地服务端】发送报文内容:" + resContent);
            logger.info("【本地服务端】发送报文长度:" + nbc.write(resContent));
        }
        nbc.flush();
        return true;
    }

    /**
     * 请求处理超时的处理事件
     */
    @Override
    public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("【本地服务端】与远程主机空闲连接超时。");
        return true;
    }

    /**
     * 连接超时处理事件
     */
    @Override
    public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
        logger.error("【本地客户端】与远程主机连接超时。");
        return true;
    }

    @Override
    public boolean onConnectException(INonBlockingConnection iNonBlockingConnection, IOException e) throws IOException {
        logger.error("【本地客户端】与远程主机连接发生异常。");
        return false;
    }
}
