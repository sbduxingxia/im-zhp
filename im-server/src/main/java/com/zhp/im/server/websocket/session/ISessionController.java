package com.zhp.im.server.websocket.session;

import javax.websocket.Session;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
public interface ISessionController {
    SessionHolder putSession(String key,Session session);
    SessionHolder removeSession(Session session);
    boolean existSession(String key);
    SessionHolder getSession(String key);
}
