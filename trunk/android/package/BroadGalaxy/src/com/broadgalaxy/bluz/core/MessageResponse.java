package com.broadgalaxy.bluz.core;

import java.nio.ByteBuffer;

import com.broadgalaxy.util.Log;

public class MessageResponse extends Response {
    private static final boolean DEBUG = true;
    private static final String TAG = MessageResponse.class.getSimpleName();
    private static final int PAYLOAD_LEN_INDEX = PAYLOAD_INDEX  + 3 + 2;

    private static final int HOUR_INDEX = 3;
    private static final int MIN_INDEX = HOUR_INDEX + 1;    
    private static final int LEN_INDEX = MIN_INDEX + 1;
    private static final int MSG_INDEX = PAYLOAD_LEN_INDEX + 2;   

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
        
        int payloadLen = 0;
//        payload = getPayloadLen();
        buffer.position(PAYLOAD_LEN_INDEX);
        payloadLen = buffer.getShort(PAYLOAD_LEN_INDEX);
        if (DEBUG) {
            Log.d(TAG, "payloadLen: " + payloadLen);
        }
        byte[] payload = new byte[payloadLen];   
        buffer.position(PAYLOAD_INDEX);
        buffer.get(payload, 0, payloadLen);
        setPayload(payload);
        
        parsePayload(payload);
        
        setCRC(buffer.get(ADDRESS_INDEX + payloadLen));
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
        
        mSendTimeHour = buffer.get(HOUR_INDEX);
        
        mSendTimeMin = buffer.get(MIN_INDEX);
        
        mMsgLen = buffer.getShort(LEN_INDEX);
        
        byte[] msg = new byte[mMsgLen];
        buffer.position(MSG_INDEX);
        buffer.get(msg, 0, mMsgLen);
        
        decodeMsg(msg);
    }

    private void decodeMsg(byte[] data) {
        String msg = new String(data);
        
        Log.e(TAG, "msg: " + msg);
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
}
