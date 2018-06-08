package com.zhp.im.utils;

/**
 * @author zhp.dts
 * @date 2018/5/9.
 */
public enum MessageType {
    CLI_2_REG(99),//注册
    SVR_2_CLI(0),//返回
    ONE_2_ONE(1),//"一对一"),
    ONE_2_CHAT(2),//"群聊"),
    SYS_2_ALL(3);//"通知");
    private int key;
    MessageType(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
