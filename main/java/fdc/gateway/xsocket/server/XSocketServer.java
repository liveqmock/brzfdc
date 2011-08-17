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
 * �����
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
        this.server.setFlushmode(FlushMode.ASYNC);   // �첽
    }

    public void start() throws IOException {
        init();

        logger.info("������  " + server.getLocalAddress() + ":" + PORT + "  ��ʼ����...");
        server.start();
        logger.info("������  " + server.getLocalAddress() + ":" + PORT + "  �����ɹ�...");

        Map<String, Class> maps = server.getOptions();
        if (maps != null) {
            for (Map.Entry<String, Class> entry : maps.entrySet()) {
                System.out.println("     key=    " + entry.getKey() + " ,   value =    " + entry.getValue().getName());
            }
        }
    }

    public boolean stop() throws IOException {
        logger.info("������  " + server.getLocalAddress() + ":" + PORT + "  ��ʼ�ر�...");
        server.close();
        server = null;
        logger.info("�������رս���...");

        return true;
    }

}
