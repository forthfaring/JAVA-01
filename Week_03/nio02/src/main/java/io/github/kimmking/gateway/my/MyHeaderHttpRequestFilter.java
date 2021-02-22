package io.github.kimmking.gateway.my;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.UUID;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/22 16:22
 */
public class MyHeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("session", UUID.randomUUID().toString());
    }
}
