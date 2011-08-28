package fdc.gateway.test;

import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0003Req;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.xsocket.client.XSocketComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import platform.common.utils.MessageUtil;
import platform.service.SystemService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: ����10:39
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class TestAction {
    private static final Logger logger = LoggerFactory.getLogger(TestAction.class);
    @Autowired
    private XSocketComponent xSocketComponent;
    @Autowired
    private ClientMessageService clientMessageService;

    // ��2003����Ϊ��
    public String test() {
        try {
            T0003Req req = new T0003Req();
            req.head.OpCode = "0003";
            req.head.BankCode = "313";
            req.param.Acct = "123456789";
            req.param.AcctName = "Bill";
            req.param.BankSerial = SystemService.getDatetime14();
            req.param.Reason = "ԭ��";
            String dataGram = req.toFDCDatagram();                // ����
            String recvMsg = xSocketComponent.sendAndRecvDataByBlockConn(dataGram);
            CommonRes resBean = clientMessageService.transMsgToBean(recvMsg);
            if ("0000".equalsIgnoreCase(resBean.head.RetCode)) {
                logger.info("���ͻ��ˡ����ͱ��ĺ������Ӧ�����ͳɹ�");
                MessageUtil.addInfo("���ݷ��ͳɹ���");
            } else {
                logger.error("���ͻ��ˡ����ͱ��ĺ������Ӧ��" + resBean.head.RetCode + resBean.head.RetMsg);
                MessageUtil.addError(resBean.head.RetCode + resBean.head.RetMsg);
            }
        } catch (Exception e) {
            MessageUtil.addError("�����쳣�����Ժ����ԣ�");
            logger.error("�����쳣", e.getMessage());
        }
        return null;
    }
}
