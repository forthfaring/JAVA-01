package io.github.kimmking.gateway.my;

import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/22 10:52
 */
public class GateWayChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final List<String> proxyServers;

    public GateWayChannelInitializer(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new MyChannelHandler(this.proxyServers));
    }
}
