package fdc.gateway.jms;

import fdc.gateway.xsocket.crypt.des.DesCrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class DepMessageCreator implements MessageCreator {

    private static final Logger logger = LoggerFactory.getLogger(DepMessageCreator.class);
    private String msg;
    private String corelationId;

    public DepMessageCreator(String msg, String corelationId) {
        this.msg = msg;
        this.corelationId = corelationId;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {

        logger.info("��ʼ�����ı���Ϣ...");
        String miStr = null;
        try {
            miStr = DesCrypter.getInstance().encrypt(msg);
        } catch (Exception e) {
            logger.error("��ӦDEP���ļ����쳣��", e.getMessage());
            throw new RuntimeException("��ӦDEP���ļ����쳣��");
        }
        TextMessage txtMsg = session.createTextMessage(miStr);
        txtMsg.setJMSCorrelationID(corelationId);
        txtMsg.setStringProperty("messageType", "response");
        logger.info("������Ӧ��Ϣ��JMSCorrelationID��" + txtMsg.getJMSCorrelationID());
        return txtMsg;
    }
}
