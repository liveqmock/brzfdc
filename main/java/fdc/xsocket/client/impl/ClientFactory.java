package fdc.xsocket.client.impl;

import fdc.xsocket.client.IConnect;
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
    XSocket(PropertyManager.getProperty("socket_server_monitor_ip")==null?"127.0.0.1":PropertyManager.getProperty("socket_server_monitor_ip"),
            PropertyManager.getIntProperty("socket_server_monitor_port"));
    private String serverIp;
    private int port;
    private IConnect client;

    private ClientFactory(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public  IConnect getClient() throws IOException {
         client =  null;
         client =  new XSocketClient(serverIp, port);
        // set default timeoutMills
        //  client.setTimeoutMills(PropertyManager.getLongProperty("socket_idle_timeout_millis"));
          return client;
    }
}
