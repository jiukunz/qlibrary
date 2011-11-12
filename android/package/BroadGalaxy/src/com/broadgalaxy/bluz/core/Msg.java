package com.broadgalaxy.bluz.core;

import java.nio.ByteBuffer;

import com.broadgalaxy.util.ByteUtil;

public class Msg extends Pack {
    public static final int ENCODE_ZH = 0x44;
    public static final int ENCODE_CODE = 0x46; //???
    
    public Msg(int fromAddress, int toAddress, int encode, String msg){
        super(Msg.CODE_MESSGE, fromAddress, format(toAddress, encode, msg));
    }

    private static byte[] format(int toAddress, int encode, String msg) {
        byte[] msgBytes = msg.getBytes();
        int msgLen = msgBytes.length;
        if (msgLen > 75) { // 600 = 8 * 75
            String detailMessage = "msg is too large, must <= 75 byte.";
            throw new IllegalStateException(detailMessage );
        }
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(Pack.address2bytes(toAddress)); //收件方地址
        buffer.put((byte)encode); // 信息类型
        buffer.putShort((short)msgLen);// 电文长度　
        buffer.put(msgBytes);//　电文内容
             
        int payloadLen = msgLen + 3 + 1 + 2 ;
        byte[] result = new byte[payloadLen];
        buffer.position(0);
        buffer.get(result, 0, payloadLen);
        return result;
    }
}
