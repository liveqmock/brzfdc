package fdc.gateway.service.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.xmlbean.BaseBean;
import fdc.gateway.xmlbean.fdc.T200.T2004Res;
import fdc.gateway.xmlbean.fdc.T200.T2008Req;
import fdc.gateway.xmlbean.fdc.T200.T2008Res;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����3:20
 * To change this template use File | Settings | File Templates.
 */
public class ClientMessageService implements IMessageService{

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageService.class);
    @Override
    public String handleMessage(String message) {
        String responseMsg = null;
        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            case 2004:
                try {
                    Object obj = BaseBean.toObject(T2004Res.class, message);
                    T2004Res req = (T2004Res) obj;
                    logger.info("�������ġ�������ɣ�������:"+req.head.RetCode+"- ������Ϣ:"+req.head.RetMsg);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    // TODO JobLog
                }
                break;
            default:
                logger.error("�������ġ�δ����������:"+opCode);
                break;
        }
        return responseMsg;
    }
}
