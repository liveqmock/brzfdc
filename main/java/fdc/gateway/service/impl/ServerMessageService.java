package fdc.gateway.service.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.fdc.T200.T2004Req;
import fdc.gateway.domain.fdc.T200.T2008Res;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午2:27
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);

    @Override
    public synchronized String handleMessage(String message) {
        String responseMsg = null;
        String dataContent = message;
        /* try {
            dataContent = new String(message.getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("报文字符集转换错误", e);
        }*/
        // 得到交易码，根据交易码将xml转换成相应的接口对象。
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            case 2004:
                T2004Req req = null;
                try {
                    req = (T2004Req) BaseBean.toObject(T2004Req.class, dataContent);
                } catch (Exception e) {
                    logger.error("转换错误", e);
                    // TODO JobLog
                }
                if (req != null) {
                    logger.info("【本地服务端】【报文中】" + req.param.ToBankName);
                } else {
                    logger.info("【本地服务端】报文转换错误。");
                }
                T2008Res res = new T2008Res();   //  默认返回成功响应码
                res.head.RetMsg = req.param.ToBankName;
                responseMsg = res.toFDCDatagram();
                break;
            default:
                T2008Res response = new T2008Res();   //  默认返回成功响应码
                responseMsg = response.toFDCDatagram();
                break;
        }
        return responseMsg;
    }
}
