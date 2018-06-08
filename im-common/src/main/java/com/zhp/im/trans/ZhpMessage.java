package com.zhp.im.trans;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class ZhpMessage implements Serializable {
    /**
     * 消息类型
     */
    //初始化（注册）消息；通道声明用户id,初始化userId->channel
    public final static int MESSAGE_INIT = 99;
    //一对一聊天
    public final static int MESSAGE_ONE = 1;
    //聊天组
    public final static int MESSAGE_CHAT = 2;
    //通知消息
    public final static int MESSAGE_ALL = 3;
    //客户端数据推送
    public final static int MESSAGE_DATA = 4;

    /**
     * 消息内容类型
     */
    //消息接收反馈
    public final static int CONTENT_BACK = 0;
    //文本
    public final static int CONTENT_TXT = 1;
    //图片
    public final static int CONTENT_IMG = 2;
    //视频
    public final static int CONTENT_VEDIO = 3;
    //音频
    public final static int CONTENT_AUDIO = 4;
    //复合文本
    public final static int CONTENT_ALL = 5;

    public final static String END_FLAG="\n";
    private String fromUser;
    private String toUser;
    private String messageId;
    private long sendTime;
    private long receiveTime;
    private int messageType;
    private int contentType;
    private StringBuffer content;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public StringBuffer getContent() {
        return content;
    }

    public void setContent(StringBuffer content) {
        this.content = content;
    }
    public String toJsonString(){
        return JSON.toJSONString(this);
    }
    public static ZhpMessage parse(String object){
        return JSON.parseObject(object,ZhpMessage.class);
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 加入消息队列时，新建对象
     * @return
     */
    public ZhpMessage newInstance(){
        ZhpMessage msg = new ZhpMessage();
        msg.setReceiveTime(this.receiveTime);
        msg.setSendTime(this.sendTime);
        msg.setMessageType(this.messageType);
        msg.setContentType(this.contentType);
        msg.setToUser(this.toUser);
        msg.setFromUser(this.fromUser);
        msg.setMessageId(this.messageId);
        msg.setContent(this.content);
        return msg;
    }
}
