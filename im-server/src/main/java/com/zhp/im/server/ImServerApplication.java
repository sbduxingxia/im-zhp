package com.zhp.im.server;

import com.zhp.im.server.netty.NettyServer;
import com.zhp.im.server.netty.NettyServerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
        //服务器启动
        NettyServerFactory.startServer(8000);
    }
}
