package fdc.gateway;

import fdc.gateway.service.ContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-26
 * Time: 上午11:54
 * To change this template use File | Settings | File Templates.
 */
public class StartGatewayApp {
     private static final Logger logger = LoggerFactory.getLogger(StartGatewayApp.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        ContainerService.init();
        logger.info("====== Socket Server 初始化成功==========");
    }
}
