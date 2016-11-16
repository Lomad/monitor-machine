package com.winning.monitor.data.storage.api;

import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.data.storage.api.entity.MessageTreeList;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/30.
 */
public interface MessageTreeStorage {

    void storeTransaction(MessageTree tree);

    MessageTreeList queryMessageTree(String group,String domain,
                                     long startTime, long endTime,
                                     Map<String, Object> arguments,
                                     int startIndex, int pageSize,
                                     LinkedHashMap<String, String> orderBy);

    MessageTreeList queryMessageTree(String group,String messageId,String domain);


}
