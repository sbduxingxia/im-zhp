package com.zhp.im.server.websocket.session;

import javax.websocket.Session;
import java.util.HashMap;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
public class SessionManager implements ISessionController{
    private static final int MAX_CHANNEL = 200000;
    private static volatile HashMap<String,SessionHolder> USER_TO_SESSION = new HashMap<>();
    private static volatile HashMap<Session,String> SESSION_TO_USER = new HashMap<>();
    @Override
    public SessionHolder putSession(String key, Session session) {
        SessionHolder sessionHolder = new SessionHolder(session,key);
        synchronized (USER_TO_SESSION){
            USER_TO_SESSION.put(key,sessionHolder);
        }
        synchronized (SESSION_TO_USER){
            SESSION_TO_USER.put(session,key);
        }
        return sessionHolder;
    }

    @Override
    public SessionHolder removeSession(Session session) {
        String userId = null;
        synchronized (SESSION_TO_USER){
            userId = SESSION_TO_USER.remove(session);
        }
        if(userId!=null){
            synchronized (USER_TO_SESSION){
                return USER_TO_SESSION.remove(userId);
            }
        }
        return null;
    }

    @Override
    public boolean existSession(String key) {
        synchronized (USER_TO_SESSION){
            return  USER_TO_SESSION.containsKey(key);
        }
    }

    @Override
    public SessionHolder getSession(String key) {
        synchronized (USER_TO_SESSION){
            return  USER_TO_SESSION.get(key);
        }
    }
    public static SessionManager instance(){
        return SessionManagerWrap.SESSION_MANAGER_INSTANCE;
    }
    private static class SessionManagerWrap{
        private final static SessionManager SESSION_MANAGER_INSTANCE = new SessionManager();
    }
}
