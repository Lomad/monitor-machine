package com.winning.monitor.messsage.netty.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.message.Message;
import com.winning.monitor.message.MessageHead;
import com.winning.monitor.message.MessagePackage;
import io.netty.buffer.ByteBuf;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyTextMessageCodec {

    private static final byte TAB = '\t'; // tab character

    private static final byte LF = '\n'; // line feed character

    private BufferHelper helper = new BufferHelper();

    private ObjectMapper objectMapper = new ObjectMapper();

    public void encode(MessagePackage messagePackage, ByteBuf buf) {
        int count = 0;
        int index = buf.writerIndex();

        buf.writeInt(0);
        count += encodeHeader(messagePackage.getHead(), buf);

        if (messagePackage.getMessage() != null) {
            count += encodeMessage(messagePackage.getMessage(), buf);
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
        count += helper.write(buf, LF);
        return count;
    }

    public int encodeMessage(Message message, ByteBuf buf) {
        try {
            String msg = objectMapper.writeValueAsString(message);
            return helper.writeRaw(buf, msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
