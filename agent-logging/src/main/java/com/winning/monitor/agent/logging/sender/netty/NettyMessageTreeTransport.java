package com.winning.monitor.agent.logging.sender.netty;


import com.winning.monitor.agent.logging.message.MessageTree;
import com.winning.monitor.agent.logging.sender.IMessageTransport;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyMessageTreeTransport implements IMessageTransport {

    private static Logger logger = LoggerFactory.getLogger(NettyMessageTreeTransport.class);

    private final MessageTreeMessageCodec messageTreeMessageCodec = new MessageTreeMessageCodec();
    private final NettyChannelManager nettyChannelManager;

    public NettyMessageTreeTransport(String servers) {
        //nettyChannelManager = new NettyChannelManager(servers);
//        NettyChannelManager nettyChannelManager
        this.nettyChannelManager = NettyChannelManager.get(servers);
    }

    @Override
    public void initialize() {
        logger.info("正在初始化 NettyMessageTreeTransport...");
    }

    @Override
    public void sendMessage(MessageTree messageTree) {

        long current = System.currentTimeMillis();

        ChannelFuture future = nettyChannelManager.getChannelFuture();

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10 * 1024); // 10K

        messageTreeMessageCodec.encode(messageTree, buf);

        int size = buf.readableBytes();
        Channel channel = future.channel();
        channel.writeAndFlush(buf);

        current = System.currentTimeMillis() - current;

//        System.out.println("发送消息长度:" + size + ",用时" + current + "ms");
    }

    @Override
    public void shutdown() {
        logger.info("正在关闭 NettyMessageTreeTransport");
    }
}
