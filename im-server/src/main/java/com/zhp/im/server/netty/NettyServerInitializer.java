package com.zhp.im.server.netty;

import com.zhp.im.server.netty.channel.ChannelManagerFactory;
import com.zhp.im.server.netty.code.MessageDecoder;
import com.zhp.im.server.netty.code.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final Logger log = LogManager.getLogger(NettyServerInitializer.class);
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new MessageDecoder());
        pipeline.addLast("encoder", new MessageEncoder());
        pipeline.addLast("handler", new NettyServerHandler().bindChannelController(ChannelManagerFactory.getChannelController()));
        log.debug(socketChannel.remoteAddress()+" 连接上服务器");
    }

}
