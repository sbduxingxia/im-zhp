package com.zhp.im.server.netty.channel;


import io.netty.channel.Channel;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public interface IChannelController {
    ChannelHolder getChannel(String key);
    boolean existChannel(String key);
    ChannelHolder putChannel(String key,Channel channel);
    ChannelHolder removeChannel(Channel channel);

}
