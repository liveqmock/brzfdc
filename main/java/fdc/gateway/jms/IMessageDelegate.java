package fdc.gateway.jms;

import javax.jms.*;

@Deprecated
public interface IMessageDelegate {

    void handleMessage(TextMessage message);

    void handleMessage(BytesMessage message);

    void handleMessage(MapMessage message);

    void handleMessage(ObjectMessage message);

    void handleMessage(StreamMessage message);
}
