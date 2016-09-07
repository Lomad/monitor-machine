package com.winning.monitor.agent.sender.netty.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class NettyChannelManager {

    private static Logger logger = LoggerFactory.getLogger(NettyChannelManager.class);

    private Bootstrap m_bootstrap;

    private ChannelFuture future;

    public NettyChannelManager(String address) {

        EventLoopGroup group = new NioEventLoopGroup(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
            }
        });

        m_bootstrap = bootstrap;
        List<InetSocketAddress> addressList = this.parseSocketAddress(address);

        this.future = createChannel(addressList.get(0));

    }

    private List<InetSocketAddress> parseSocketAddress(String content) {
        try {
            String[] strs = content.split(";");
            List<InetSocketAddress> address = new ArrayList<InetSocketAddress>();

            for (String str : strs) {
                String[] items = str.split(":");

                address.add(new InetSocketAddress(items[0], Integer.parseInt(items[1])));
            }
            return address;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<InetSocketAddress>();
    }


    private ChannelFuture createChannel(InetSocketAddress address) {
        ChannelFuture future = null;

        try {
            future = m_bootstrap.connect(address);
            future.awaitUninterruptibly(100, TimeUnit.MILLISECONDS); // 100 ms

            if (!future.isSuccess()) {
                logger.error("Error when try connecting to " + address);
                closeChannel(future);
            } else {
                logger.info("Connected to CAT server at " + address);
                return future;
            }
        } catch (Throwable e) {
            logger.error("Error when connect server " + address.getAddress(), e);

            if (future != null) {
                closeChannel(future);
            }
        }
        return null;
    }

    private void closeChannel(ChannelFuture channel) {
        try {
            if (channel != null) {
                logger.info("close channel " + channel.channel().remoteAddress());
                channel.channel().close();
            }
        } catch (Exception e) {
        }
    }


}
