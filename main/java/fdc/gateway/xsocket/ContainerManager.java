package fdc.gateway.xsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContainerManager {

    private static final Logger logger = LoggerFactory.getLogger(ContainerManager.class);
    private static ApplicationContext context;

    private ContainerManager() {
    }

    public static void init() {
        logger.info("...XSocket Container Service ��ʼ����ʼ.......");
        context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        XSocketManager manager = (XSocketManager)getBean("xSocketManager");
        manager.init();
    }

    public static Object getBean(String key) {
        if (context == null) {
            init();
        }
        return context.getBean(key);
    }

    public static void stop(int code) {
        if (code == 0) {
            logger.info("Xsocket Container Service �����ر�...");
        } else {
            logger.info("Xsocket Container Service �����쳣�������ر�...");
        }
        System.exit(code);
    }
}