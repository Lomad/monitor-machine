package com.winning.monitor.agent.logging.entity;

import com.winning.monitor.agent.config.sender.SenderConfig;
import com.winning.monitor.agent.config.utils.ConfigUtils;
import com.winning.monitor.agent.config.utils.NetworkInterfaceManager;

import java.util.Map;
import java.util.Properties;

/**
 * Created by nicholasyan on 16/9/8.
 */
public class ConfigManager {

    private final Map<String, Map> jsonMap;
    private Domain domain;
    private SenderConfig senderConfig;

    public ConfigManager(Map<String, Map> jsonMap) {
        this.jsonMap = jsonMap;
        this.initDomainConfig();
        this.initSenderConfig();
    }

    public Domain getDomain() {
        return domain;
    }

    public SenderConfig getSenderConfig() {
        return this.senderConfig;
    }

    private void initDomainConfig() {
        this.domain = new Domain();
        this.domain.setIp(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
        this.domain.setHostName(NetworkInterfaceManager.INSTANCE.getLocalHostName());

        if (this.initDomainName(domain, "META-INF/winning/properties/application.properties", "application.name"))
            return;

        if (this.initDomainName(domain, "META-INF/app.properties", "app.name"))
            return;
    }

    private boolean initDomainName(Domain domain, String propertiesFile, String keyName) {
        Properties properties =
                ConfigUtils.loadProperties(propertiesFile, false, false);

        if (properties == null || properties.keySet().size() == 0)
            return false;

        String name = properties.getProperty(keyName);
        if (name != null || !"".equals(name)) {
            domain.setId(name);
            return true;
        }

        return false;
    }


    private void initSenderConfig() {
        SenderConfig config = new SenderConfig();
        Map<String, String> sender = this.jsonMap.get("sender");
        config.setSender(sender.get("name"));
        config.setServers(sender.get("servers"));
        this.senderConfig = config;
    }

}
