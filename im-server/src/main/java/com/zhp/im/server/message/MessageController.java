package com.zhp.im.server.message;

import com.zhp.im.server.netty.channel.IChannelController;
import com.zhp.im.trans.ZhpMessage;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageController {
    private IChannelController channelController;
    public MessageController(IChannelController iChannelController){
        this.channelController = iChannelController;
    }
    //将待回传消息发送到MQ
    public void addMessageToMq(ZhpMessage msg){
        MessageQueueHandler.pushOneMessage(msg);
    }
    public void excute(ZhpMessage msg){

    }

}
