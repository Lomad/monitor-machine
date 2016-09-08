package com.winning.monitor.agent.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.agent.config.utils.Properties;
import com.winning.monitor.agent.logging.entity.ConfigManager;
import com.winning.monitor.agent.logging.message.MessageManager;
import com.winning.monitor.agent.logging.message.internal.DefaultMessageManager;
import com.winning.monitor.agent.logging.message.internal.MessageProducer;
import com.winning.monitor.agent.logging.message.internal.sender.MessageTreeSenderTaskManager;
import com.winning.monitor.agent.logging.message.internal.sender.MessageTreeStorage;
import com.winning.monitor.agent.logging.transaction.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/7.
 */
public class MonitorLogger {

    private static volatile boolean s_init = false;
    private static MonitorLogger s_instance = new MonitorLogger();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private MessageProducer messageProducer;
    private MessageManager messageManager;
    private MessageTreeStorage messageTreeStorage;
    private ConfigManager configContainer;
    private MessageTreeSenderTaskManager messageTreeSenderTaskManager;


    private MonitorLogger() {
    }

    private static void checkAndInitialize() {
        if (!s_init) {
            synchronized (s_instance) {
                if (!s_init) {
                    initialize(new File(getHome(), "client.json"));
                    s_init = true;
                }
            }
        }
    }

    private static String getHome() {
        String homePath = Properties.forString().fromEnv().fromSystem().getProperty("MONITOR_HOME", "/data/winning-monitor");
        return homePath;
    }

    private static boolean isInitialized() {
        synchronized (s_instance) {
            return s_init;
        }
    }

    private static void initialize(File configFile) {
        Map<String, Map> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(configFile, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonMap == null)
            throw new RuntimeException("CAT 初始化失败,未找到配置文件");

        s_instance.configContainer = new ConfigManager(jsonMap);

        s_instance.messageTreeStorage = new MessageTreeStorage(5000);
        s_instance.messageTreeStorage.initialize();

        s_instance.messageManager = new DefaultMessageManager(
                s_instance.configContainer,
                s_instance.messageTreeStorage);
        s_instance.messageManager.initialize();

        s_instance.messageProducer = new MessageProducer(
                s_instance.configContainer,
                s_instance.messageManager);
        s_instance.messageProducer.initialize();


        s_instance.messageTreeSenderTaskManager = new MessageTreeSenderTaskManager(
                s_instance.configContainer,
                s_instance.messageTreeStorage);

        s_instance.messageTreeSenderTaskManager.initialize();
        s_instance.messageTreeSenderTaskManager.start();


        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    s_instance.messageTreeStorage.shutdown();
                    s_instance.messageTreeSenderTaskManager.shutdown();
                } catch (Throwable t) {
                }
            }
        });
    }

    public static Transaction newTransaction(String type, String name) {
        return MonitorLogger.getMessageProducer().newTransaction(type, name);
    }

    private static MessageProducer getMessageProducer() {
        checkAndInitialize();
        return s_instance.messageProducer;
    }


}
