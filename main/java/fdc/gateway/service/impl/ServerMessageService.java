package fdc.gateway.service.impl;

import fdc.gateway.service.IMessageService;
import fdc.gateway.xmlbean.BaseBean;
import fdc.gateway.xmlbean.fdc.T200.T2004Req;
import fdc.gateway.xmlbean.fdc.T200.T2008Res;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

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
        String dataContent = message;
       /* try {
            dataContent = new String(message.getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("�����ַ���ת������", e);
        }*/
        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            case 2004:
                T2004Req req = null;
                try {
                    req  = (T2004Req) BaseBean.toObject(T2004Req.class, dataContent);
                } catch (Exception e) {
                    logger.error("ת������", e);
                    // TODO JobLog
                }
                if(req != null) {
                    logger.info("�����ط���ˡ��������С�"+req.param.ToBankName);
                }else {
                    logger.info("�����ط���ˡ�����ת������");
                }
                T2008Res res = new T2008Res();   //  Ĭ�Ϸ��سɹ���Ӧ��
                res.head.RetMsg="������Ϣ";
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
