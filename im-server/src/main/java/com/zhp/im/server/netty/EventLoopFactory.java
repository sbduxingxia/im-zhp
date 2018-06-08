package com.zhp.im.server.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author zhp.dts
 * @date 2018/5/15.
 */
public class EventLoopFactory {
    private volatile static EventLoopGroup PARENT_LOOP_INSTANCE = new NioEventLoopGroup();
    private volatile static EventLoopGroup DEFAULT_LOOP_ISNTANCE = new NioEventLoopGroup();
    public static EventLoopGroup parentEventLoop(){
        return PARENT_LOOP_INSTANCE;
    }
    public static EventLoopGroup defaultEventLoop(){
        return PARENT_LOOP_INSTANCE;
    }
    public static void shutDownEventLoop(){
        PARENT_LOOP_INSTANCE.shutdownGracefully();
        DEFAULT_LOOP_ISNTANCE.shutdownGracefully();
    }
}
