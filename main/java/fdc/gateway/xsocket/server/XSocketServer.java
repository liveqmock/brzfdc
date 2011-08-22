package fdc.gateway.xsocket.server;

import fdc.gateway.xsocket.server.impl.ServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.IConnection.FlushMode;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;
import java.util.Map;

/**
 * 服务端
 *
 * @author zxb
 */
public class XSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(XSocketServer.class);
    private static final int PORT = PropertyManager.getIntProperty("socket_server_monitor_port");
    //private static final String IP = PropertyManager.getProperty("socket_server_monitor_ip");
    private IServer server;
    private static XSocketServer xSocketServer = new XSocketServer();

    private XSocketServer() {
    }

    public static XSocketServer getInstance() {
        return xSocketServer;
    }

    private void init() throws IOException {
        this.server = new Server(PORT, new ServerHandler());
        this.server.setFlushmode(FlushMode.ASYNC);   // 异步
    }

    public void start() throws IOException {
        init();

        logger.info("【SocketServer】 " + server.getLocalAddress() + ":" + PORT + "  开始启动...");
        server.start();
        logger.info("【SocketServer】  " + server.getLocalAddress() + ":" + PORT + "  启动成功...");
    }

    public boolean stop() throws IOException {
        logger.info("【SocketServer】  " + server.getLocalAddress() + ":" + PORT + "  开始关闭...");
        if (server != null) {
            server.close();
            server = null;
        }
        logger.info("【SocketServer】关闭结束...");

        return true;
    }

}
