package com.zhp.im.server.netty;

import com.zhp.im.server.netty.channel.ChannelManagerFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyServerInitializerString extends ChannelInitializer<SocketChannel> {
    private static final Logger log = LogManager.getLogger(NettyServerInitializerString.class);
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new NettyServerHandlerString().bindChannelController(ChannelManagerFactory.getChannelController()));
        log.debug(socketChannel.remoteAddress()+" 连接上服务器");
    }

}
