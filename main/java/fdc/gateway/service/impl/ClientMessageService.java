package fdc.gateway.service.impl;

import fdc.gateway.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:20
 * To change this template use File | Settings | File Templates.
 */
public class ClientMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageService.class);

    @Override
    public String handleMessage(String message) {
        String responseMsg = null;
        /*if (responseMsg != null) return
        CommonRes resHead = (CommonRes)BaseBean.toObject(CommonRes.class, message);
        if("0000".equalsIgnoreCase(resHead.head.RetCode)) {
            logger.info("���ͻ��ˡ����ر��Ĵ������ͳɹ�");
        }else {
            logger.error("���ͻ��ˡ����ر��Ĵ���"+resHead.head.RetCode+resHead.head.RetMsg);
        }*/
        return responseMsg;
    }
}
