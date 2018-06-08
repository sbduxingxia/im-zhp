package com.zhp.im.server.netty.channel;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class ChannelControllerHandler {
    private IChannelController channelController;
    public ChannelControllerHandler(IChannelController iChannelController){
        this.channelController = iChannelController;
    }
}
