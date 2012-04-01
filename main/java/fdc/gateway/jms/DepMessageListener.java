package fdc.gateway.jms;

import fdc.gateway.service.impl.ServerMessageService;
import fdc.gateway.xsocket.crypt.des.DesCrypter;
import org.jdom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.bean.ReferencedBean;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: ����4:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DepMessageListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(DepMessageListener.class);

    @Autowired
    private ServerMessageService serverMessageService;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Override
    public void onMessage(Message message) {

        try {
            String msgId = message.getJMSMessageID();
            TextMessage textMessage = (TextMessage)message;
            String msgContent = DesCrypter.getInstance().decrypt(textMessage.getText());
            logger.info("���յ���ϢID:"+msgId);
            logger.info("brzfdc ���ձ���: "+msgContent);

            String rtnMsg = serverMessageService.handleMessage(msgContent);
            logger.info("brzfdc ��Ӧ���ģ�"+rtnMsg);

            jmsTemplate.send(new DepMessageCreator(rtnMsg, msgId));
            logger.info("brzfdc �������!");

        } catch (Exception e) {
            logger.error("��Ϣ����POJO�쳣", e.getMessage());
        }
    }
}
