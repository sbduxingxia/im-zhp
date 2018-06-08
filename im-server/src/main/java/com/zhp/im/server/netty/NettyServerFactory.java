package com.zhp.im.server.netty;

/**
 * @author zhp.dts
 * @date 2018/5/17.
 */
public class NettyServerFactory {
    private static NettyServer INSTANCE;
    public static void startServer(int port){
        init(port);
        INSTANCE.openServer();
    }
    private static void init(int port){
        if(INSTANCE==null){
            synchronized (NettyServer.class){
                if(INSTANCE==null){
                    INSTANCE = new NettyServer(port);
                }
            }
        }
    }
}
