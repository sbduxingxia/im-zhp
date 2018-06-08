package com.zhp.im.client;

import com.zhp.im.client.netty.NettyClient;
import org.junit.Test;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyClientTest {
    @Test
    public void TestSendMessage(){
        final NettyClient nettyClient = new NettyClient("127.0.0.1",8000,0);
        new Thread(nettyClient).start();
        while (true){
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
