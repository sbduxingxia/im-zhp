package com.zhp.im.server.websocket;

import com.zhp.im.server.message.MessageQueueHandler;
import com.zhp.im.server.websocket.session.ISessionController;
import com.zhp.im.server.websocket.session.SessionManager;
import com.zhp.im.server.websocket.session.SessionManagerFactory;
import com.zhp.im.utils.UrlParamUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
@ServerEndpoint(value = "/websocket"
)
@Component
public class WebSocketServer {
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private volatile AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    private static volatile ISessionController sessionController = SessionManagerFactory.getSessionManager();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        Map<String,String> param = new HashMap<>();
        UrlParamUtils.parseToMap(session.getQueryString(),param);
        String userId = param.get("uid");
        if(userId==null){
            try {
                sendMessage("参数异常");
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        sessionController.putSession(userId,session);
        webSocketSet.add(this);
        //在线数加1
        log.info("有新连接加入！当前在线人数为" +  onlineCount.incrementAndGet());
        try {
            sendMessage("很好");
        } catch (IOException e) {
            log.debug("IO异常");
        }
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        sessionController.removeSession(this.session);
        log.debug("有一连接关闭！当前在线人数为" +onlineCount.decrementAndGet());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("来自客户端的消息:" + message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     @OnError
     public void onError(Session session, Throwable error) {
         log.debug("发生错误");
         error.printStackTrace();
     }
     /**
      * 群发自定义消息
      * */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
}
