package fdc.gateway.xsocket.client.impl;

import fdc.gateway.xsocket.client.IBlockConnect;
import fdc.gateway.xsocket.client.IConnect;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-10
 * Time: ÏÂÎç1:57
 * To change this template use File | Settings | File Templates.
 */
public enum ClientFactory {
    XSocket(PropertyManager.getProperty("socket_server_monitor_ip"),
            PropertyManager.getIntProperty("socket_server_monitor_port"),
            PropertyManager.getIntProperty("socket_connection_timeout_millis"),
            PropertyManager.getIntProperty("socket_idle_timeout_millis"));
    private String serverIp;
    private int serverPort;
    private int cnctTimeoutMills;
    private int idleTimeoutMills;
    private IBlockConnect client;

    private ClientFactory(String serverIp, int serverPort, int cnctTimeoutMills, int idleTimeoutMills) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.cnctTimeoutMills = cnctTimeoutMills;
        this.idleTimeoutMills = idleTimeoutMills;
    }

    public IBlockConnect getBlockClient() throws IOException {
        client = null;
        client = new XSocketBlockClient(serverIp, serverPort,cnctTimeoutMills,idleTimeoutMills);
        // set default timeoutMills
        //  client.setTimeoutMills(PropertyManager.getLongProperty("socket_idle_timeout_millis"));
        return client;
    }
}
