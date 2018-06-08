package com.zhp.im.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static javafx.scene.input.KeyCode.T;

/**
 * @author zhp.dts
 * @date 2018/5/18.
 */
public class UrlParamUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    public static void parseToMap(String str,Map<String, String> map) {
        if(Objects.isNull(str) || str.isEmpty()) {
            return ;
        }
        Arrays.stream(str.split("&"))
                .filter(kv -> kv.contains("="))
                .map(kv -> kv.split("="))
                .forEach(array -> map.put(array[0], array[1]));
    }

    public static void main(String[] args){

    }
}
