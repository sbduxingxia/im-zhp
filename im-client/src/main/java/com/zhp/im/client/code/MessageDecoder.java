package com.zhp.im.client.code;

import com.zhp.im.trans.MessageContant;
import com.zhp.im.trans.ZhpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 协议头标识（int 4）| 数据长度（int 4）| 数据内容不得超过2048
 * @author zhp.dts
 * @date 2018/5/16.
 */
public class MessageDecoder extends ByteToMessageDecoder{
    private final Charset charset;
    private final static int MESSAGE_BASE_LENGTH = 8;
    private final static int CONTENT_MAX_LENGTH = 2048;
    public MessageDecoder() {
        this(Charset.defaultCharset());
    }



    public MessageDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    /**
     * 解码
     * @param channelHandlerContext
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) throws Exception {

        if(buffer.readableBytes()>=MESSAGE_BASE_LENGTH){
            // 防止socket字节流攻击
            // 防止，客户端传来的数据过大
            // 因为，太大的数据，是不合理的
            if (buffer.readableBytes() > CONTENT_MAX_LENGTH) {
                buffer.skipBytes(buffer.readableBytes());
            }
        }
        // 记录包头开始的index
        int beginReader;

        while (true) {
            // 获取包头开始的index
            beginReader = buffer.readerIndex();
            // 标记包头开始的index
            buffer.markReaderIndex();
            // 读到了协议的开始标志，结束while循环
            if (buffer.readInt() ==MessageContant.HEAD_FLAG) {
                break;
            }
            // 未读到包头，略过一个字节
            // 每次略过，一个字节，去读取，包头信息的开始标记
            buffer.resetReaderIndex();
            buffer.readByte();

            // 当略过，一个字节之后，
            // 数据包的长度，又变得不满足
            // 此时，应该结束。等待后面的数据到达
            if (buffer.readableBytes() < MESSAGE_BASE_LENGTH) {
                return;
            }
        }

        // 消息的长度

        int length = buffer.readInt();
        // 判断请求数据包数据是否到齐
        if (buffer.readableBytes() < length) {
            // 还原读指针
            buffer.readerIndex(beginReader);
            return;
        }
        // 读取data数据
        byte[] data = new byte[length];
        buffer.readBytes(data);
        String receiveMsg = new String(data,this.charset);
        list.add(ZhpMessage.parse(receiveMsg));
    }
}
