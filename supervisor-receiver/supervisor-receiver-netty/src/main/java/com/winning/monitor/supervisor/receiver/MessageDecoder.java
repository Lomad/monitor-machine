package com.winning.monitor.supervisor.receiver;

import com.winning.monitor.message.MessagePackage;
import com.winning.monitor.message.netty.io.NettyTextMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/7.
 */
public class MessageDecoder extends ByteToMessageDecoder {


    private final NettyTextMessageCodec nettyTextMessageCodec = new NettyTextMessageCodec();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() < 4) {
            return;
        }
        buffer.markReaderIndex();
        int length = buffer.readInt();
        buffer.resetReaderIndex();
        if (buffer.readableBytes() < length + 4) {
            return;
        }
        try {
            if (length > 0) {
                ByteBuf readBytes = buffer.readBytes(length + 4);
                readBytes.markReaderIndex();
                readBytes.readInt();

                MessagePackage messagePackage = nettyTextMessageCodec.decode(readBytes);

                System.out.print(messagePackage.getHead().getIpAddress());
//                readBytes.resetReaderIndex();
//                tree.setBuffer(readBytes);
//                m_handler.handle(tree);
//                m_processCount++;

//                long flag = m_processCount % CatConstants.SUCCESS_COUNT;

//                if (flag == 0) {
//                    m_serverStateManager.addMessageTotal(CatConstants.SUCCESS_COUNT);
//                }
            } else {
                // client message is error
                buffer.readBytes(length);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            m_serverStateManager.addMessageTotalLoss(1);
//            m_logger.error(e.getMessage(), e);
        }
    }
}
