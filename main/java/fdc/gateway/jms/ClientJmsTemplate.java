package fdc.gateway.jms;

import fdc.gateway.xsocket.crypt.des.DesCrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import javax.crypto.NoSuchPaddingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-20
 * Time: 下午1:10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ClientJmsTemplate {

    @Resource(name = "jmsTemplateClient")
    private JmsTemplate jmsTemplateClient;

    private static Logger logger = LoggerFactory.getLogger(ClientJmsTemplate.class);
    private static long RECV_TIMEOUT = PropertyManager.getLongProperty("fm.mq.timeout");
    private static String queueName = PropertyManager.getProperty("queue.fm.313.dep");

    public String sendAndRecv(String strMsg) throws Exception, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {

        String strMessage = null;
        ClientMessageCreator messageCreator = new ClientMessageCreator(strMsg);
        jmsTemplateClient.send(messageCreator);
        String createdMsgID = messageCreator.getMessageID();
        jmsTemplateClient.setReceiveTimeout(RECV_TIMEOUT);

        logger.info("发送消息ID:" + createdMsgID);
        logger.info("发送消息：" + strMsg);
        TextMessage message = (TextMessage)jmsTemplateClient.receiveSelected(queueName, "JMSCorrelationID = '"
                + createdMsgID + "' and messageType='response'");
        if(message != null) {
            strMessage = DesCrypter.getInstance().decrypt(message.getText());
        }else {
            throw new RuntimeException("消息接收超时！");
        }
        return strMessage;
    }
}
