package com.winning.monitor.message.netty.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.message.internal.DefaultMessageTree;
import com.winning.monitor.agent.logging.transaction.DefaultTransaction;
import com.winning.monitor.message.Message;
import com.winning.monitor.message.MessageHead;
import com.winning.monitor.message.MessagePackage;
import com.winning.monitor.message.collector.CollectorMessage;
import io.netty.buffer.ByteBuf;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyTextMessageCodec {

    private static final byte TAB = '\t'; // tab character
    private static final byte LF = '\n'; // line feed character
    private static Logger logger = LoggerFactory.getLogger(NettyTextMessageCodec.class);
    private BufferHelper helper = new BufferHelper();

    private ObjectMapper objectMapper = new ObjectMapper();

    private MessageTreeMessageCodec messageTreeMessageCodec = new MessageTreeMessageCodec();

    public void encode(MessagePackage messagePackage, ByteBuf buf) {
        int count = 0;
        int index = buf.writerIndex();

        buf.writeInt(0);
        count += encodeHeader(messagePackage.getHead(), buf);

        if (messagePackage.getMessage() != null) {
            if (messagePackage.getHead().getMessageType().equals(CollectorMessage.TYPE_NAME))
                count += encodeMessage(messagePackage.getMessage(), buf);
            else if (messagePackage.getHead().getMessageType().equals(MessageTree.TYPE_NAME)) {
                count += this.messageTreeMessageCodec.encodeMessage((MessageTree) messagePackage.getMessage(),
                        buf);
            }
        }

        buf.setInt(index, count);
    }


    protected int encodeHeader(MessageHead messageHead, ByteBuf buf) {
        int count = 0;
        count += helper.write(buf, messageHead.getMessageType());
        count += helper.write(buf, TAB);
        count += helper.write(buf, messageHead.getHostName());
        count += helper.write(buf, TAB);
        count += helper.write(buf, messageHead.getIpAddress());
        count += helper.write(buf, TAB);
        count += helper.write(buf, messageHead.getMessageId());
        count += helper.write(buf, TAB);
        count += helper.write(buf, String.valueOf(messageHead.getTimestamp().getTime()));
        count += helper.write(buf, LF);
        return count;
    }


    protected int encodeMessage(Message message, ByteBuf buf) {
        try {
            int count = 0;
            String msg = objectMapper.writeValueAsString(message);
            count += helper.writeRaw(buf, msg);
            count += helper.write(buf, LF);
            return count;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public MessagePackage decode(ByteBuf buf) {
        MessagePackage messagePackage = new MessagePackage();
        decode(buf, messagePackage);
        return messagePackage;
    }


    protected void decode(ByteBuf buf, MessagePackage messagePackage) {
        MessageByteContext ctx = new MessageByteContext();
        ctx.setBuffer(buf);
        decodeHeader(ctx, messagePackage);

        if (buf.readableBytes() > 0) {
            decodeMessage(ctx, messagePackage);
        }
    }

    protected void decodeHeader(MessageByteContext ctx, MessagePackage messagePackage) {
        MessageHead head = new MessageHead();
        head.setMessageType(helper.read(ctx, TAB));
        head.setHostName(helper.read(ctx, TAB));
        head.setIpAddress(helper.read(ctx, TAB));
        head.setMessageId(helper.read(ctx, TAB));
        head.setTimestamp(new Date(Long.valueOf(helper.read(ctx, LF))));
        messagePackage.setHead(head);
    }

    protected void decodeMessage(MessageByteContext ctx, MessagePackage messagePackage) {
        String messageType = messagePackage.getHead().getMessageType();
        String json = helper.read(ctx, LF);
        if (CollectorMessage.TYPE_NAME.equals(messageType)) {
            try {
                CollectorMessage collectorMessage = objectMapper.readValue(json, CollectorMessage.class);
                messagePackage.setMessage(collectorMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (MessageTree.TYPE_NAME.equals(messageType)) {
            try {
                Map<String, Object> map = objectMapper.readValue(json, Map.class);
                MessageTree messageTree = new DefaultMessageTree();
                messageTree.setHostName(String.valueOf(map.get("hostName")));
                messageTree.setDomain(String.valueOf(map.get("domain")));
                messageTree.setIpAddress(String.valueOf(map.get("ipAddress")));
                messageTree.setMessageId(String.valueOf(map.get("messageId")));
                messageTree.setParentMessageId(String.valueOf(map.get("parentMessageId")));
                messageTree.setRootMessageId(String.valueOf(map.get("rootMessageId")));

                Map<String, Object> message = (Map<String, Object>) map.get("message");
                String type = message.get("type").toString();
                String name = message.get("name").toString();
                DefaultTransaction transaction = new DefaultTransaction(type, name, null);
                try {
                    BeanUtils.populate(transaction, message);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                messagePackage.setMessage(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
