package fdc.gateway.xsocket;

import fdc.utils.DateUtil;
import fdc.gateway.xsocket.server.XSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XSocketManager extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(XSocketManager.class);
    private static final long serialVersionUID = -5534543207744847501L;

    static {
        logger.info("////===========================================////");
        logger.info("Socket Server 开始初始化...");
        logger.info("////===========================================////");
    }

    // 初始化
    public void init() throws ServletException {
        printLine();
        try {
            XSocketServer server = XSocketServer.getInstance();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        printLine();
    }


    // 销毁
    public void destroy() {
        printLine();
        try {
            XSocketServer server = XSocketServer.getInstance();
            server.stop();
            server = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        printLine();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("restart".equals(request.getParameter("action"))) {
            destroy();
            init();
        }
        if ("start".equals(request.getParameter("action"))) {
            destroy();
            init();
        }
        if ("stop".equals(request.getParameter("action"))) {
            destroy();
        }
    }

    private static void printLine() {
        logger.info("//////////////////////////" + DateUtil.getDatetime18() + "//////////////////////////");
    }

}


