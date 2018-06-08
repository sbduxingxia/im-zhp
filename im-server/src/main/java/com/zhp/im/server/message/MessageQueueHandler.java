package com.zhp.im.server.message;

import com.zhp.im.trans.ZhpMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageQueueHandler {
    private static final Logger log = LogManager.getLogger(MessageQueueHandler.class);
    private static volatile LinkedList<ZhpMessage> MSG_WAIT_LIST = new LinkedList<>();
    private volatile AtomicInteger num = new AtomicInteger(0);
    private volatile AtomicInteger maxSize = new AtomicInteger(0);
    private boolean addMessage(ZhpMessage msg){
        if(msg==null) {
            return false;
        }
        synchronized (MSG_WAIT_LIST){

            return MSG_WAIT_LIST.add(msg);
        }
    }
    private ZhpMessage first(){
        ZhpMessage msg = null;
        if(MSG_WAIT_LIST.size()>0){
            synchronized (MSG_WAIT_LIST){
                msg = MSG_WAIT_LIST.removeFirst();
            }
            int n = num.getAndIncrement()+1;
            int size = MSG_WAIT_LIST.size();
            if(size>maxSize.get()){
                maxSize.set(size);
            }
            if(n%1000==0){
                log.debug("MSG_LIST.size = "+MSG_WAIT_LIST.size()+" ,maxSize = "+maxSize.get());
            }
        }
        return msg;
    }
    public static boolean pushOneMessage(ZhpMessage msg){
        return MessageQueueWrap.MESSAGE_QUEUE_HANDLER.addMessage(msg);
    }
    public static ZhpMessage next(){
        return MessageQueueWrap.MESSAGE_QUEUE_HANDLER.first();
    }
    private static class MessageQueueWrap{
        private static final MessageQueueHandler MESSAGE_QUEUE_HANDLER = new MessageQueueHandler();
    }
}
