package java0.nio01.netty.my;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.channel().eventLoop().schedule(() -> {
//            //获取客户端发送过来的消息
//            ByteBuf byteBuf = (ByteBuf) msg;
//            System.out.println("收到客户端" + ctx.channel().remoteAddress() +
//                    "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
//        }, 10, TimeUnit.SECONDS);
        ctx.writeAndFlush(Unpooled.copiedBuffer("这波啊这波是肉蛋葱鸡",CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送消息给客户端
//        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?",
//                CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }
}