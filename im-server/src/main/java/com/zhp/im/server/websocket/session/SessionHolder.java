package com.zhp.im.server.websocket.session;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
public class SessionHolder implements Serializable {
    private Session session;
    private String userId;
    private int userType;
    public SessionHolder(Session session,String userId){
        this.session = session;
        this.userId =userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
