package fdc.gateway.service.impl;

import fdc.gateway.domain.ResHead;
import fdc.gateway.domain.fdc.T000.T0003Res;
import fdc.gateway.domain.fdc.T200.T2003Res;
import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.fdc.T200.T2004Res;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午3:20
 * To change this template use File | Settings | File Templates.
 */
public class ClientMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageService.class);

    @Override
    public String handleMessage(String message) {
        String responseMsg = null;
        ResHead resHead = (ResHead)BaseBean.toObject(ResHead.class, message);
        if("0000".equalsIgnoreCase(resHead.RetCode)) {
            logger.info("【客户端】返回报文处理：发送成功");
        }else {
            logger.error("【客户端】返回报文处理："+resHead.RetCode+resHead.RetMsg);
        }
        return responseMsg;
    }
}
