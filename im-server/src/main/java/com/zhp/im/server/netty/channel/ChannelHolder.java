package com.zhp.im.server.netty.channel;

import com.zhp.im.trans.ZhpMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author zhp.dts
 * @date 2018/5/17.
 */
public class ChannelHolder{
    private Channel  channel;
    private long lastUsedTime;
    public ChannelHolder(Channel channel){
        this.channel = channel;
        lastUsedTime = System.currentTimeMillis();
    }
    public ChannelFuture sendMessage(ZhpMessage msg){
        if(channel==null){
            return null;
        }
        lastUsedTime = System.currentTimeMillis();
        return channel.writeAndFlush(msg);
    }
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }
}
