package com.zhp.im.server.netty.client;

import com.zhp.im.server.netty.code.MessageDecoder;
import com.zhp.im.server.netty.code.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    private String userId;
    public NettyClientInitializer(String userId){
        this.userId = userId;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new MessageDecoder());
        pipeline.addLast("encoder", new MessageEncoder());
//        pipeline.addLast("decoder", new StringDecoder());
//        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new NettyClientHandler(userId));

    }
}
