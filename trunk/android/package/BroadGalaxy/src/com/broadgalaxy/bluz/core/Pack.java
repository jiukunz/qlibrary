package com.broadgalaxy.bluz.core;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import com.broadgalaxy.util.ByteUtil;
import com.broadgalaxy.util.Log;

public class Pack {
    public static final String CODE_MESSGE = "$Msg_";
    public static final String CODE_LOCATION = "$Pos_";
    private static final String TAG = Pack.class.getSimpleName();
    public static final int ENCODE_ZH = 0x44;
    public static final int ENCODE_CODE = 0x46;
    

    public static final int MSG_INDEX = 0;
    public static final int LENGTH_INDEX = MSG_INDEX + 5;
    public static final int ADDRESS_INDEX = LENGTH_INDEX + 2;
    public static final int PAYLOAD_INDEX = ADDRESS_INDEX  + 3;
    
    public static final int ADDRESS_LEN = 3;
    
    
    String mCode;
    int mLength;
    int mUserAddress;
    byte[] mPayload;
    int mCRC;
    
    public Pack() {

    }
    

    public byte[] getByte() {
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        buffer.put(mCode.getBytes());  // 指令
//        buffer.putShort((short)mLength); // 长度　
//        buffer.put(Pack.address2bytes(mUserAddress)); // 用户地址
//        buffer.put(mPayload); // 信息内容
//        buffer.put((byte)mCRC); // 校验和
//        
//        byte[] result = new byte[mLength];
//        buffer.position(0);
//        buffer.get(result, 0, mLength);
//        return result;
        
        byte[] nonCRC = getNonCRCByte();
        byte crc = (byte)mCRC;
        return ByteUtil.appendByte(nonCRC, new byte[]{crc});
    }
    
    protected byte[] getNonCRCByte() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            byte[] c = mCode.getBytes("utf-8");
            buffer.put(c);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException", e);
        }  // 指令
        buffer.putShort((short)mLength); // 长度　
        buffer.put(Pack.address2bytes(mUserAddress)); // 用户地址
        buffer.put(mPayload); // 信息内容
        
        byte[] result = new byte[mLength - 1];
        buffer.position(0);
        buffer.get(result, 0, mLength - 1);
        return result;
    }
    
    public String toHexString() {
        byte[] bytes = getByte();
        String hexString = "0x";
        for (byte b : bytes) {
            hexString += " " + ByteUtil.byte2HexString(b);
//            Integer.toHexString(new Integer(b).byteValue());

//            hexString += " " + Byte.toString(b);
        }
        
        return hexString;
    }
    
    public static byte[] address2bytes(int address) {
//        Log.d(TAG , "address: " + address + "\tbinaryString: " + Integer.toBinaryString(address));
        byte[] add = new byte[3];
        add[2] = (byte)((address << 24) >> 24);
        add[1] = (byte)((address << 16) >> 24);
        add[0] = (byte)((address << 8) >> 24);
        return add;
    }
    
    public int byte2Address(byte[] addressBytes) {
        int address = 0;
        
        return address & addressBytes[0] << 24 & addressBytes[1] << 16 & addressBytes[2];
    }
    
  
    public String getCode() {
        return mCode;
    }
    public void setCode(String mCode) {
        this.mCode = mCode;
    }
    public int getLength() {
        return mLength;
    }
    public void setLength(int mLength) {
        this.mLength = mLength;
    }
    public int getUserAddress() {
        return mUserAddress;
    }
    public void setUserAddress(int mUserAddress) {
        this.mUserAddress = mUserAddress;
    }
    public byte[] getPayload() {
        return mPayload;
    }
    public void setPayload(byte[] mPayload) {
        this.mPayload = mPayload;
    }
    public int getCRC() {
        return mCRC;
    }
    public void setCRC(int mCRC) {
        this.mCRC = mCRC;
    }
}
