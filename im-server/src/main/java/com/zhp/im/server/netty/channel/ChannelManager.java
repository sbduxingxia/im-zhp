package com.zhp.im.server.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.HashMap;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class ChannelManager implements IChannelController{
    //TODO 优化最大长连接上线，配合jvm内存进行优化
    private static final int MAX_CHANNEL = 200000;
    private static volatile HashMap<String,ChannelHolder> USER_ID_CHANNEL = new HashMap<>(MAX_CHANNEL);
    private static volatile HashMap<ChannelId,String> CHANNEL_TO_USER = new HashMap<>(MAX_CHANNEL);
    public static ChannelManager instance(){
        return ChannelHolderWrap.INSTANCE;
    }
    @Override
    public ChannelHolder getChannel(String key) {
        ChannelHolder channelHolder = null;
        synchronized (USER_ID_CHANNEL){
            channelHolder = USER_ID_CHANNEL.get(key);
        }
        return channelHolder;
    }

    @Override
    public boolean existChannel(String key) {
        synchronized (USER_ID_CHANNEL){
            return USER_ID_CHANNEL.containsKey(key);
        }
    }

    @Override
    public ChannelHolder putChannel(String key,Channel channel) {
        ChannelHolder channelHolder = null;
        synchronized (USER_ID_CHANNEL){
            channelHolder = USER_ID_CHANNEL.put(key,new ChannelHolder(channel));
        }
        synchronized (CHANNEL_TO_USER){
            CHANNEL_TO_USER.put(channel.id(),key);
        }
        return channelHolder;
    }

    @Override
    public ChannelHolder removeChannel(Channel channel) {
        String userId = null;
        ChannelHolder channelHolder = new ChannelHolder(null);
        synchronized (CHANNEL_TO_USER){
            userId = CHANNEL_TO_USER.remove(channel.id());
        }
        if(userId!=null){
            synchronized (USER_ID_CHANNEL){
                channelHolder = USER_ID_CHANNEL.remove(userId);
                channelHolder.setChannel(null);
            }
        }
        return channelHolder;
    }

    private static class ChannelHolderWrap{
        private static final ChannelManager INSTANCE = new ChannelManager();
    }
}
