package com.zhp.im.client.code;

import com.zhp.im.trans.MessageContant;
import com.zhp.im.trans.ZhpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * 编码类：Object -> byteBuf
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageEncoder extends MessageToByteEncoder<ZhpMessage> {
    private final Charset charset;

    public MessageEncoder() {
        this(Charset.defaultCharset());
    }



    public MessageEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ZhpMessage zhpMessage, ByteBuf byteBuf) throws Exception {
        if(zhpMessage!=null){
            byte[] dataBytes = zhpMessage.toJsonString().getBytes(this.charset);
            byteBuf.writeInt(MessageContant.HEAD_FLAG);
            byteBuf.writeInt(dataBytes.length);//数据长度
            byteBuf.writeBytes(dataBytes);
            byteBuf.writeBytes(ZhpMessage.END_FLAG.getBytes());
        }
    }
    /*@Override
    protected void encode(ChannelHandlerContext ctx, ZhpMessage zhpMessage, List<Object> list) throws Exception {
        if (zhpMessage!=null) {
            list.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(zhpMessage.toJsonString()), this.charset));
        }
    }*/


}
