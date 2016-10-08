package com.winning.monitor.data.storage.api;

import com.winning.monitor.agent.logging.message.MessageTree;

/**
 * Created by nicholasyan on 16/9/30.
 */
public interface MessageTreeStorage {

    void storeTransaction(MessageTree tree);

}
