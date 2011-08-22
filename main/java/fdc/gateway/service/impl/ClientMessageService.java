package fdc.gateway.service.impl;

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
        // 得到交易码，根据交易码将xml转换成相应的接口对象。
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            case 2004:
                try {
                    Object obj = BaseBean.toObject(T2004Res.class, message);
                    T2004Res req = (T2004Res) obj;
                    logger.info("【处理报文】处理完成，返回码:" + req.head.RetCode + "- 返回信息:" + req.head.RetMsg);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    // TODO JobLog
                }
                break;
            default:
                logger.error("【处理报文】未处理，交易码:" + opCode);
                break;
        }
        return responseMsg;
    }
}
