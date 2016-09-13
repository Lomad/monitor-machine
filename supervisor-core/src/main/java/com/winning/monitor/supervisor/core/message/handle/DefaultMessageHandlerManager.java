package com.winning.monitor.supervisor.core.message.handle;

import com.winning.monitor.message.Message;
import com.winning.monitor.superisor.consumer.api.analysis.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/9.
 */
public class DefaultMessageHandlerManager implements MessageHandlerManager, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageHandlerManager.class);

    private HashMap<String, MessageHandler> messageHandlerHashMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initialize() {
        Map<String, MessageHandler> beansOfType = this.applicationContext.getBeansOfType(MessageHandler.class);
        for (MessageHandler messageHandler : beansOfType.values())
            this.messageHandlerHashMap.put(messageHandler.getMessageTypeName(), messageHandler);
    }

    @Override
    public void handle(Message message) {
        try {
            MessageHandler messageHandler = this.messageHandlerHashMap.get(message.getMessageType());
            messageHandler.handleMessage(message);
        } catch (Throwable e) {
            logger.error("处理消息时发生错误!", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
