package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

import com.broadgalaxy.bluz.persistence.IMsg;
import com.broadgalaxy.util.Log;

import android.content.ContentValues;

public class MessageResponse extends Response {
    private static final boolean DEBUG = true;
    private static final String TAG = MessageResponse.class.getSimpleName();
    private static final int MESSAGE_LEN_INDEX = PAYLOAD_INDEX  + 3 + 2;

    private static final int HOUR_INDEX = 3;
    private static final int MIN_INDEX = HOUR_INDEX + 1;    
    private static final int LEN_INDEX = MIN_INDEX + 1;
    private static final int MSG_INDEX = LEN_INDEX + 2;   

    private int mSenderAddress;
    private int mSendTimeHour;
    private int mSendTimeMin;
    private int mMsgLen;
    private String msg;

    public MessageResponse(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
        setCode(Pack.CODE_MESSGE);
        
        buffer.position(LENGTH_INDEX);
        setLength(buffer.getShort());
        
        int address = 0;
        byte[] addressBytes = new byte[ADDRESS_LEN];
        buffer.position(ADDRESS_INDEX);
        buffer.get(addressBytes, 0, ADDRESS_LEN);
        address = byte2Address(addressBytes);
        setUserAddress(address);
        
        int msgPayloadLen = 0;
        buffer.position(MESSAGE_LEN_INDEX);
        msgPayloadLen = buffer.getShort(MESSAGE_LEN_INDEX);
        msgPayloadLen = msgPayloadLen / 8;

        byte[] payload = new byte[msgPayloadLen + 7]; 
        
        buffer.position(PAYLOAD_INDEX);
        buffer.get(payload, 0, msgPayloadLen + 7);
        setPayload(payload);
        
        parsePayload(payload);
        
        setCRC(buffer.get(PAYLOAD_INDEX + msgPayloadLen));
    }
    
    @Override
    public String toString() {
        return super.toString() + 
                "\tmSenderAddress: " + mSenderAddress +
                "\tmSendTimeHour: " + mSendTimeHour + 
                "\tmSendTimeMin: " + mSendTimeMin + 
                "\tmMsgLen: " + mMsgLen + 
                "\tmsg: " + msg;
    }
    
    @Override
    int getPayloadLen() {
        return 0;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer();
        byte[] senderAddBytes = new byte[ADDRESS_LEN];
        buffer.position(0);
        buffer.get(senderAddBytes, 0, ADDRESS_LEN);
        mSenderAddress = byte2Address(senderAddBytes);
        
        buffer.position(HOUR_INDEX);
        mSendTimeHour = buffer.get();
        buffer.position(MIN_INDEX);
        mSendTimeMin = buffer.get();
        buffer.position(LEN_INDEX);
        mMsgLen = buffer.getShort();
        mMsgLen = mMsgLen / 8;
        byte[] msg = new byte[mMsgLen];
        buffer.position(MSG_INDEX);
        buffer.get(msg, 0, mMsgLen);
        
        decodeMsg(msg);
    }

    private void decodeMsg(byte[] data) {
        msg = new String(data);
    }
    public int getSenderAddress() {
        return mSenderAddress;
    }

    public void setSenderAddress(int mSenderAddress) {
        this.mSenderAddress = mSenderAddress;
    }

    public int getSendTimeHour() {
        return mSendTimeHour;
    }

    public void setSendTimeHour(int mSendTimeHour) {
        this.mSendTimeHour = mSendTimeHour;
    }

    public int getSendTimeMin() {
        return mSendTimeMin;
    }

    public void setSendTimeMin(int mSendTimeMin) {
        this.mSendTimeMin = mSendTimeMin;
    }

    public int getMsgLen() {
        return mMsgLen;
    }

    public void setMsgLen(int mMsgLen) {
        this.mMsgLen = mMsgLen;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg; 
    }

    public ContentValues toValues() {
        ContentValues v = new ContentValues();
        v.put(IMsg.COLUMN_FROM_ADDRESS, mSenderAddress + "");
        v.put(IMsg.COLUMN_DEST_ADDRESS, mUserAddress);
        v.put(IMsg.COLUMN_DATA, msg);
        v.put(IMsg.COLUMN_DATA_LEN, msg.getBytes().length);
        v.put(IMsg.COLUMN_STATUS, IMsg.STATUS_RCVD);
        v.put(IMsg.COLUMN_TIME, System.currentTimeMillis());
        return v;
    }
}
