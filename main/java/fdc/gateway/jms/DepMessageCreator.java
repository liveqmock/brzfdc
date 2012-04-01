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

        logger.info("开始创建文本消息...");
        String miStr = null;
        try {
            miStr = DesCrypter.getInstance().encrypt(msg);
        } catch (Exception e) {
            logger.error("响应DEP报文加密异常！", e.getMessage());
            throw new RuntimeException("响应DEP报文加密异常！");
        }
        TextMessage txtMsg = session.createTextMessage(miStr);
        txtMsg.setJMSCorrelationID(corelationId);
        txtMsg.setStringProperty("messageType", "response");
        logger.info("生成响应消息的JMSCorrelationID：" + txtMsg.getJMSCorrelationID());
        return txtMsg;
    }
}
