package com.zhp.im.utils;

/**
 * @author zhp.dts
 * @date 2018/5/9.
 */
public enum StateType {
    ONLINE(1),
    OFFLINE(2),
    HIDDEN(3);
    private int key;
    StateType(int key){
        this.key = key;
    }
}
