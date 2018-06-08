package com.zhp.im.server.netty;

import com.zhp.im.server.message.MessageTransformHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhp.dts
 * @date 2018/5/9.
 */
public class NettyServer {
    private static final Logger log = LogManager.getLogger(NettyServer.class);
    private ServerBootstrap sb;
    private int port;
    private ChannelFuture future;
    public NettyServer(int port){
        this.port = port;
    }
    public void openServer(){
        sb = new ServerBootstrap();
        sb.group(EventLoopFactory.parentEventLoop(),EventLoopFactory.defaultEventLoop())
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //绑定端口,开始接收进来的连接
        try {
            future = sb.bind(port).sync();
            log.debug("netty server 已启动");
            beginTransform();
            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            EventLoopFactory.shutDownEventLoop();
        }
    }
    private void beginTransform(){
        MessageTransformHandler.excute();
    }
    public static void main(String[] args){
        NettyServer nettyServer = new NettyServer(8000);
        nettyServer.openServer();
        while(true){

        }
    }
}
