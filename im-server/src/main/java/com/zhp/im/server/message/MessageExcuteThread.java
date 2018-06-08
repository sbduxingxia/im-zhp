package com.zhp.im.server.message;

import com.zhp.im.server.netty.channel.ChannelHolder;
import com.zhp.im.server.netty.channel.ChannelManagerFactory;
import com.zhp.im.server.netty.channel.IChannelController;
import com.zhp.im.trans.ZhpMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageExcuteThread implements Runnable {
    private static final Logger log = LogManager.getLogger(MessageExcuteThread.class);
    private IChannelController channelController;
    private volatile AtomicInteger runNum = new AtomicInteger(0);
    public MessageExcuteThread(){
        this.channelController = ChannelManagerFactory.getChannelController();
    }
    @Override
    public void run() {
        log.info( Thread.currentThread().getId()+":MessageTransform Thread isRun.");
        while(true){
            ChannelHolder channelHolder = null;
            ZhpMessage msg = MessageQueueHandler.next();
            if(msg!=null&&msg.getToUser()!=null){
                channelHolder = channelController.getChannel(msg.getToUser());
            }
            if(channelHolder!=null){
                channelHolder.sendMessage(msg);
                int n = runNum.getAndIncrement()+1;
                if(n%10000==0){
                    log.debug("Server Tran Msg Num = "+n);
                }

            }else{
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
