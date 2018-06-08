package com.zhp.im.trans;

/**
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageFactory {
    public static ZhpMessage createReceiveOk(ZhpMessage zhpMessage){
        if(zhpMessage==null) {
            return null;
        }
        ZhpMessage newObj = new ZhpMessage();
        newObj.setSendTime(System.currentTimeMillis());
        newObj.setMessageId(zhpMessage.getMessageId());
        newObj.setFromUser(zhpMessage.getFromUser());
        newObj.setToUser(zhpMessage.getFromUser());
        newObj.setContent(new StringBuffer("Ok"));
        newObj.setContentType(ZhpMessage.CONTENT_BACK);
        newObj.setMessageType(zhpMessage.getMessageType());
        return newObj;
    }
    public static ZhpMessage createRegisterOk(ZhpMessage zhpMessage){
        if(zhpMessage==null) {
            return null;
        }
        ZhpMessage newObj = new ZhpMessage();
        newObj.setMessageId(zhpMessage.getMessageId());
        newObj.setFromUser(zhpMessage.getFromUser());
        newObj.setSendTime(System.currentTimeMillis());
        newObj.setToUser(zhpMessage.getFromUser());
        newObj.setContent(new StringBuffer("Register Ok"));
        newObj.setContentType(ZhpMessage.CONTENT_BACK);
        newObj.setMessageType(zhpMessage.getMessageType());
        return newObj;
    }
    public static ZhpMessage createRegister(String from){
        ZhpMessage newObj = new ZhpMessage();
        newObj.setMessageId("0");
        newObj.setSendTime(System.currentTimeMillis());
        newObj.setFromUser(from);
        newObj.setToUser(from);
        newObj.setContent(new StringBuffer("Register..."));
        newObj.setContentType(ZhpMessage.CONTENT_TXT);
        newObj.setMessageType(ZhpMessage.MESSAGE_INIT);
        return newObj;
    }
    public static ZhpMessage createSendOne(String from,String to,String msg){

        ZhpMessage newObj = new ZhpMessage();
        newObj.setMessageId(String.valueOf(System.currentTimeMillis()));
        newObj.setSendTime(System.currentTimeMillis());
        newObj.setFromUser(from);
        newObj.setToUser(to);
        newObj.setContent(new StringBuffer(msg));
        newObj.setContentType(ZhpMessage.CONTENT_TXT);
        newObj.setMessageType(ZhpMessage.MESSAGE_ONE);
        return newObj;
    }

}
