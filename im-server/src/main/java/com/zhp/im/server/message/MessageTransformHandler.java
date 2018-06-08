package com.zhp.im.server.message;

import com.zhp.im.trans.MessageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 消耗缓存的消息队列
 * @author zhp.dts
 * @date 2018/5/17.
 */
public class MessageTransformHandler {
    private static final Logger log = LogManager.getLogger(MessageTransformHandler.class);
    private final int FIXED_THREAD_NUM = 1;
    private ExecutorService fixedThreadPool = new ThreadPoolExecutor(FIXED_THREAD_NUM,
            FIXED_THREAD_NUM,
            30000,
            TimeUnit.DAYS,
            new SynchronousQueue<>());
    private MessageQueueHandler messageQueueHandler = new MessageQueueHandler();

    public static void excute(){
        TransformHandler.MESSAGE_TRANS_HANDLER.run();
    }
    private void run(){
        final MessageExcuteThread messageExcuteThread = new MessageExcuteThread();
        for(int i=0;i<FIXED_THREAD_NUM;i++){
            fixedThreadPool.submit(messageExcuteThread);
        }
//        new Thread(messageExcuteThread).start();
    }
    private static class TransformHandler{
        private final static MessageTransformHandler MESSAGE_TRANS_HANDLER = new MessageTransformHandler();
    }
}
