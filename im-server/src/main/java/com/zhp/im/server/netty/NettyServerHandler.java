package com.zhp.im.server.netty;

import com.zhp.im.server.netty.channel.IChannelController;
import com.zhp.im.server.message.MessageQueueHandler;
import com.zhp.im.trans.MessageFactory;
import com.zhp.im.trans.ZhpMessage;
import com.zhp.im.utils.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ZhpMessage> {
    private IChannelController channelController;
    public NettyServerHandler bindChannelController(IChannelController iChannelController){
        this.channelController = iChannelController;
        return this;
    }
    /**
     * 每当服务端收到新的客户端连接时,客户端的channel存入ChannelGroup列表中,并通知列表中其他客户端channel
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        channelController.putChannel("client-1",ctx.channel());
        super.handlerAdded(ctx);
    }
    /**
     *每当服务端断开客户端连接时,客户端的channel从ChannelGroup中移除,并通知列表中其他客户端channel
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
    /**
     * 每当从服务端读到客户端写入信息时,将信息转发给其他客户端的Channel.
     * @throws Exception
     */
    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server 收到:"+msg);
        if(msg instanceof ZhpMessage){

        }
        ctx.channel().writeAndFlush(msg);
    }
*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ZhpMessage msg) throws Exception {
//        System.out.println(System.currentTimeMillis()+" - Server Receive:"+msg.toJsonString());
//        ctx.channel().writeAndFlush(MessageFactory.createReceiveOk(msg));
        //TODO 根据消息类型处理或推送到MQ
        if(msg.getMessageType() == MessageType.CLI_2_REG.getKey()){
            //注册信息
            channelController.putChannel(msg.getFromUser(),ctx.channel());
            MessageQueueHandler.pushOneMessage(MessageFactory.createReceiveOk(msg));
        }else{
            MessageQueueHandler.pushOneMessage(msg.newInstance());
        }
    }

    /**
     * 服务端监听到客户端活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
    /**
     * 服务端监听到客户端不活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
    /**
     * 当服务端的IO 抛出异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        channelController.removeChannel(ctx.channel());
        super.channelUnregistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

}
