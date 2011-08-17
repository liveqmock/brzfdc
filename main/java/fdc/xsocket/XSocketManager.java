package fdc.xsocket;

import fdc.xsocket.server.XSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class XSocketManager extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(XSocketManager.class);
    private static final long serialVersionUID = -5534543207744847501L;

    static {
        logger.info("////////////////////////////////////////");
        logger.info("loading XSocketManager.class");
        logger.info("////////////////////////////////////////");
    }

    // ³õÊ¼»¯
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


    // Ïú»Ù
    public void destroy() {
        printLine();
        try {
            XSocketServer server = XSocketServer.getInstance();
            server.stop();
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
        System.out.println("//////////////////////////" + new Date() + "//////////////////////////");
    }

}


