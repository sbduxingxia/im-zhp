package com.zhp.im.server.netty.channel;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class ChannelManagerFactory {
    public static IChannelController getChannelController(){
        return ChannelManager.instance();
    }
}
