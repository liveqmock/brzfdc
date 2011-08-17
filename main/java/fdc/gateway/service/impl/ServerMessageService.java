package fdc.gateway.service.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.xmlbean.BaseBean;
import fdc.gateway.xmlbean.fdc.T200.T2008Req;
import fdc.gateway.xmlbean.fdc.T200.T2008Res;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����2:27
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);

    @Override
    public synchronized String handleMessage(String message) {
        String responseMsg = null;
        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            case 2008:
                try {
                    Object obj = BaseBean.toObject(T2008Req.class, message);
                    T2008Req req = (T2008Req) obj;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    // TODO JobLog
                }

                T2008Res res = new T2008Res();   //  Ĭ�Ϸ��سɹ���Ӧ��
                responseMsg = res.toFDCDatagram();
                break;
            default:
                T2008Res response = new T2008Res();   //  Ĭ�Ϸ��سɹ���Ӧ��
                responseMsg = response.toFDCDatagram();
                break;
        }
        return responseMsg;
    }
}
