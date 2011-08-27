package fdc.gateway.xsocket;

import fdc.gateway.xsocket.server.XSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import platform.service.SystemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("xSocketManager")
public class XSocketManager {

    private static final Logger logger = LoggerFactory.getLogger(XSocketManager.class);
    private static final long serialVersionUID = -5534543207744847501L;
    @Autowired
    private XSocketServer xSocketServer;

    static {
        logger.info("////================ XSocketManager ��ʼ��ʼ��====================////");
    }

    public XSocketManager() {
    }

    // ��ʼ��
    public void init() {
        printLine();
        try {
            xSocketServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        printLine();
    }


    // ����
    public void destroy() {
        printLine();
        try {
            xSocketServer.stop();
            xSocketServer = null;
        } catch (Exception e) {
            throw new RuntimeException("Socket Server ����ʱ�����쳣 !");
        }
        printLine();
    }

    public XSocketServer getxSocketServer() {
        return xSocketServer;
    }

    public void setxSocketServer(XSocketServer xSocketServer) {
        this.xSocketServer = xSocketServer;
    }

    private static void printLine() {
        logger.info("//////////////////////////" + SystemService.getDatetime18() + "//////////////////////////");
    }

}


