package fdc.gateway.jms;

import fdc.gateway.xsocket.crypt.des.DesCrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ClientMessageCreator implements MessageCreator {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageCreator.class);
    private String msg;
    private Message message;

    public ClientMessageCreator(String msg) {
        this.msg = msg;
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
        txtMsg.setStringProperty("messageType","request");
        this.message = txtMsg;

        return txtMsg;
    }

    public String getMessageID() throws JMSException {
        return message == null ? null : message.getJMSMessageID();
    }
}
