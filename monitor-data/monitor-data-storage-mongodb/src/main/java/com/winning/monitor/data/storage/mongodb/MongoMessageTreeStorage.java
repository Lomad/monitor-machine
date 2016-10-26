package com.winning.monitor.data.storage.mongodb;

import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.data.storage.api.MessageTreeStorage;
import com.winning.monitor.data.storage.api.entity.MessageTreeList;
import com.winning.monitor.data.storage.mongodb.po.message.MessageTreePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public MessageTreeList queryMessageTree(String domain, long startTime,
                                            long endTime, Map<String, Object> arguments,
                                            int startIndex, int pageSize,
                                            LinkedHashMap<String, String> orderBy) {

        String collectionName = this.getCollectionName(domain);

        Query query = new Query();
        query.addCriteria(new Criteria("domain").is(domain));
        query.addCriteria(new Criteria("messageTree.message.timestampInMillis")
                .gte(startTime).lt(endTime));

        if (arguments != null && arguments.containsKey("transactionTypeName"))
            query.addCriteria(new Criteria("messageTree.message.type").is(arguments.get("transactionTypeName")));
        if (arguments != null && arguments.containsKey("transactionName"))
            query.addCriteria(new Criteria("messageTree.message.name").is(arguments.get("transactionName")));
        if (arguments != null && arguments.containsKey("serverIpAddress"))
            query.addCriteria(new Criteria("messageTree.ipAddress").is(arguments.get("serverIpAddress")));
        if (arguments != null && arguments.containsKey("clientAppName"))
            query.addCriteria(new Criteria("messageTree.caller.name").is(arguments.get("clientAppName")));
        if (arguments != null && arguments.containsKey("clientIpAddress"))
            query.addCriteria(new Criteria("messageTree.caller.ip").is(arguments.get("clientIpAddress")));

        if (arguments != null && arguments.containsKey("status")) {
            String status = (String) arguments.get("status");
            if ("0".equals(status))
                query.addCriteria(new Criteria("messageTree.message.status").is("0"));
            else
                query.addCriteria(new Criteria("messageTree.message.status").ne("0"));
        }

        long total = this.mongoTemplate.count(query, collectionName);

        query.fields().include("messageTree");

        if (orderBy != null) {
            for (Map.Entry<String, String> orderByItem : orderBy.entrySet()) {
                String field = orderByItem.getKey();
                Sort.Direction orderDirection = "DESC".equals(orderByItem.getValue()) ?
                        Sort.Direction.DESC : Sort.Direction.ASC;

                if ("duration".equals(field)) {
                    query.with(new Sort(orderDirection, "messageTree.message.durationInMicro"));
                }
                if ("time".equals(field)) {
                    query.with(new Sort(orderDirection, "messageTree.message.timestampInMillis"));
                }
                if ("status".equals(field)) {
                    query.with(new Sort(orderDirection, "messageTree.message.status"));
                }
            }
        }

        query.skip(startIndex).limit(pageSize);

        List<MessageTreePO> messageTrees =
                this.mongoTemplate.find(query, MessageTreePO.class,
                        this.getCollectionName(domain));

        MessageTreeList messageTreeList = new MessageTreeList();
        for (MessageTreePO messageTreePO : messageTrees) {
            messageTreeList.addMessageTree(messageTreePO.getMessageTree());
        }

        messageTreeList.setTotalSize(total);

        return messageTreeList;
    }


    public String getCollectionName(String domain) {
        return "Messages-" + domain;
    }

}
