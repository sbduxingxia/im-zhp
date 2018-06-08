package com.zhp.im.server.websocket.session;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
public class SessionManagerFactory {
    public static SessionManager getSessionManager(){
        return SessionManager.instance();
    }
}
