package com.zhp.im.server;

import com.zhp.im.server.netty.NettyServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImServerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void nettyServerRun(){
        NettyServer nettyServer = new NettyServer(8000);
        nettyServer.openServer();
        while (true){
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
