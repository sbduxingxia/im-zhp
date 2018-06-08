package com.zhp.im.server.netty.client;

import com.zhp.im.server.message.MessageQueueHandler;
import com.zhp.im.trans.ZhpMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhp.dts
 * @date 2018/5/17.
 */
public class NettySendMsgTick {
    private static final Logger log = LogManager.getLogger(NettySendMsgTick.class);
    private volatile AtomicInteger tickNum = new AtomicInteger(0);
    private volatile LinkedList<ZhpMessage> wait_send_list = new LinkedList<>();
    public static NettySendMsgTick instance(){
        return TickHolder.INSTANCE;
    }
    public int tick(){
        int num = tickNum.getAndIncrement()+1;
        synchronized (NettySendMsgTick.class){
            if((num)%1000==0){
                log.debug("hadSendNum = "+num);
            }
        }
        return num;
    }
    public void reset(){
        tickNum.set(0);
    }
    public void addMessage(ZhpMessage msg){
        synchronized (wait_send_list){
            wait_send_list.add(msg);
        }
    }
    public ZhpMessage nextMsg(){
        synchronized (wait_send_list){
            if(wait_send_list.size()>0){
                return wait_send_list.removeFirst();
            }
        }
        return null;
    }
    private static class TickHolder{
        private static final NettySendMsgTick INSTANCE = new NettySendMsgTick();
    }
}
