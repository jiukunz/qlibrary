package com.broadgalaxy.bluz.protocol;

import com.broadgalaxy.bluz.persistence.IMsg;

import android.content.ContentValues;

import java.nio.ByteBuffer;

public class MessageRequest extends Request {
    private String mMsg;
    private int mToAdd;

    public MessageRequest(int fromAddress, int toAddress, int encode, String msg){
        super(Pack.CODE_MESSGE, fromAddress, format(toAddress, encode, msg));
        mMsg = msg;
        mToAdd = toAddress;
    }
    
    public MessageRequest(int fromAddress, int toAddress, String msg){
        this(fromAddress, toAddress, ENCODE_CODE, msg);
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
        short len = (short)msgLen;
        len *= 8; // int bit unit.
        buffer.putShort(len);// 电文长度　
        buffer.put(msgBytes);//　电文内容
             
        int payloadLen = msgLen + 3 + 1 + 2 ;
        byte[] result = new byte[payloadLen];
        buffer.position(0);
        buffer.get(result, 0, payloadLen);
        return result;
    }
    
    public ContentValues toValues() {
        ContentValues values = new ContentValues();

        values.put(IMsg.COLUMN_FROM_ADDRESS, mUserAddress + "");
        values.put(IMsg.COLUMN_DEST_ADDRESS, mToAdd + "");
        values.put(IMsg.COLUMN_DATA, mMsg);
        values.put(IMsg.COLUMN_DATA_LEN, mMsg.getBytes().length);
        values.put(IMsg.COLUMN_STATUS, IMsg.STATUS_SENT);
        values.put(IMsg.COLUMN_TIME, System.currentTimeMillis());
        return values;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public int getmToAdd() {
        return mToAdd;
    }

    public void setmToAdd(int mToAdd) {
        this.mToAdd = mToAdd;
    }
}
