package com.zhp.im.server.netty.client;

import com.zhp.im.trans.ZhpMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ZhpMessage> {
    private String userId;
    private AtomicInteger receiveNum = new AtomicInteger(0);
    public NettyClientHandler(String userId){
        this.userId = userId;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ZhpMessage msg) throws Exception {
//        System.out.println("Client 收到:"+zhpMessage.toJsonString());
//        System.out.println("client channelRead..");
//        ByteBuf buf = msg.readBytes(msg.readableBytes());
        msg.setReceiveTime(System.currentTimeMillis());
        receiveNum.getAndDecrement();
//        System.out.println(System.currentTimeMillis()+" - Client "+userId+" received "+receiveNum.getAndDecrement()+": " + msg.toJsonString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
