package com.zhp.im.server.netty.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhp.dts
 * @date 2018/5/22.
 */
@Component
public class NettyServerEvn {
    private final static String ZHP_IM_SERVER="zhp.im.server";
    @Autowired
    private Environment evn;

    public List<String> getServerIpPortList(){
        String listStr = evn.getProperty(ZHP_IM_SERVER);
        if(listStr != null){
            return Arrays.asList((listStr.split(",")));
        }
        return new ArrayList<>();
    }
}
