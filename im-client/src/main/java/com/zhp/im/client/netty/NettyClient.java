package com.zhp.im.client.netty;


import com.zhp.im.trans.MessageFactory;
import com.zhp.im.trans.ZhpMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class NettyClient implements Runnable{

    private Bootstrap bs;
    private String ip;
    private int port;
    ChannelFuture channelF;
    Channel channel;
    public static final String userFlag="user-";
    EventLoopGroup group;
    private int mIndex;//测试用，序号
//    private ConcurrentLinkedQueue<ZhpMessage> sendMsg = new ConcurrentLinkedQueue<>();
    public static AtomicInteger hadSendMsgNum = new AtomicInteger(0);
    private LinkedList<ZhpMessage> sendMsg= new LinkedList<ZhpMessage> ();
    public NettyClient(String ip,int port,int mIndex){
        this.ip = ip;
        this.port = port;
        this.mIndex = mIndex;
        group = new NioEventLoopGroup(1);
        //是一个启动NIO服务的辅助启动类
        bs = new Bootstrap();
        bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer(userFlag+mIndex));

    }
    public void register(){
        addMessage(MessageFactory.createRegister(userFlag+mIndex));
    }
    private void sendMessage(ZhpMessage msg){
        channel.writeAndFlush(msg);
        channel.writeAndFlush(ZhpMessage.END_FLAG.getBytes());
    }
    public int testAddSend(int num,int max){
        int allNum = 0;
        sendMsg.clear();
        String from = userFlag+mIndex;
        for(int i=0;i<num;i++){
            addMessage(MessageFactory.createSendOne(from,userFlag+(i%max),"0000-"+i));
            allNum ++;
        }
        return allNum;
    }
    public void addMessage(ZhpMessage msg){
        synchronized (sendMsg){
            sendMsg.add(msg);
        }
    }
    @Override
    public void run() {
        try {
            channel = bs.connect(ip,port).sync().channel();
            while (true){
                ZhpMessage msg =null;
                synchronized (sendMsg) {
                    if (sendMsg.size() > 0) {
                        try {
                            msg = sendMsg.removeFirst();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(msg!=null){
                    NettySendMsgTick.instance().tick();
                    sendMessage(msg);
                }else{
                    Thread.sleep(10);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
    public void listenCommand(){
        new Thread(this).start();
    }
    public static void main(String[] args){
        //客户端连接数量
        int maxNum =1000;
        List<NettyClient> clientList = new ArrayList<>();
        int sendMessageNum=0;
        for(int i=0;i<maxNum;i++){
            NettyClient nettyClient = new NettyClient("127.0.0.1",8000,i);
            nettyClient.register();
            new Thread(nettyClient).start();
            clientList.add(nettyClient);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                sendMessageNum=0;

                String numStr = in.readLine();
                System.out.println(System.currentTimeMillis()+" - 发送开始");
                int num = Integer.parseInt(numStr);
                //计数 归0
                NettySendMsgTick.instance().reset();
                for(int i=0;i<clientList.size();i++){
                    sendMessageNum+=clientList.get(i).testAddSend(num,maxNum);
                }
                System.out.println(System.currentTimeMillis()+" - 发送数量结束，数量="+sendMessageNum);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

//        new Thread(nettyClient).start();
//        nettyClient.listenCommand();

    }
}
