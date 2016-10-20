package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.data.storage.api.MessageTreeStorage;
import com.winning.monitor.data.storage.mongodb.po.message.MessageTreePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by nicholasyan on 16/9/30.
 */
@Repository
public class MongoMessageTreeStorage implements MessageTreeStorage {

    private static final Logger logger = LoggerFactory.getLogger(MongoMessageTreeStorage.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void storeTransaction(MessageTree tree) {
        if (tree == null)
            return;

        String collectionName = this.getCollectionName(tree.getDomain());
        MessageTreePO messageTreePO = new MessageTreePO(tree);
        this.mongoTemplate.insert(messageTreePO, collectionName);
    }


    public String getCollectionName(String domain) {
        return "Messages-" + domain;
    }

}
