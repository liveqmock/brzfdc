package fdc.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContainerService {

    private static final Logger logger = LoggerFactory.getLogger(ContainerService.class);
    private static ApplicationContext context;

    private ContainerService() {
    }

    public static void init() {
        logger.info("...XSocket Container Service 初始化开始.......");
        context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
    }

    public static Object getBean(String key) {
        if (context == null) {
            init();
        }
        return context.getBean(key);
    }

    public static void stop(int code) {
        if (code == 0) {
            logger.info("Xsocket Container Service 正常关闭...");
        } else {
            logger.info("Xsocket Container Service 发生异常，即将关闭...");
        }
        System.exit(code);
    }
}
