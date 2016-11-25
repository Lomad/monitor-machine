package com.winning.monitor.data.api.transaction.domain;

import java.util.LinkedHashMap;

/**
 * Created by sao something on 2016/11/25.
 */
public class ServerCountWithType {
    //key代表系统名，value代表该系统调用次数
    private LinkedHashMap<String, Long> serverCountMap;

    public LinkedHashMap<String, Long> getServerCountMap() {
        return serverCountMap;
    }

    public void setServerCountMap(LinkedHashMap<String, Long> serverCountMap) {
        this.serverCountMap = serverCountMap;
    }


}
