package com.winning.monitor.agent.collector.sender.io;

import com.winning.monitor.agent.collector.api.entity.CollectData;
import com.winning.monitor.agent.collector.sender.ICollectDataMessageTransport;
import com.winning.monitor.agent.logging.sender.netty.NettyChannelManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/8.
 */
public class NettyCollectDataMessageTransport implements ICollectDataMessageTransport {

    private final NettyChannelManager nettyChannelManager;
    private final CollectDatasMessageCodec collectDataMessageCodec;

    public NettyCollectDataMessageTransport(String servers) {
        this.nettyChannelManager = NettyChannelManager.get(servers);
        this.collectDataMessageCodec = new CollectDatasMessageCodec();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void sendMessage(List<CollectData> collectData) {
        long current = System.currentTimeMillis();

        ChannelFuture future = nettyChannelManager.getChannelFuture();

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10 * 1024); // 10K

        this.collectDataMessageCodec.encode(collectData, buf);

        int size = buf.readableBytes();
        Channel channel = future.channel();
        channel.writeAndFlush(buf);

        current = System.currentTimeMillis() - current;

        System.out.println("发送消息长度:" + size + ",用时" + current + "ms");
    }

    @Override
    public void shutdown() {

    }
}
