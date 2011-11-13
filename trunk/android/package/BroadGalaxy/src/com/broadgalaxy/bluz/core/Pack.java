package com.broadgalaxy.bluz.core;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import com.broadgalaxy.util.ByteUtil;
import com.broadgalaxy.util.Log;

public class Pack {
    public static final String CODE_MESSGE = "$Msg_";
    public static final String CODE_LOCATION = "$Pos_";
    private static final String TAG = Pack.class.getSimpleName();
    String mCode;
    int mLength;
    int mUserAddress;
    byte[] mPayload;
    int mCRC;
    
    public Pack(String code, int address, byte[] payload){
        mCode = code;
        mUserAddress = address;
        mPayload = payload;
        mLength = caculateLength();
        mCRC = genCRC();
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
    
    private byte[] getNonCRCByte() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(mCode.getBytes());  // 指令
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
            Integer.toHexString(new Integer(b).byteValue());

//            hexString += " " + Byte.toString(b);
        }
        
        return hexString;
    }
    
    public static byte[] address2bytes(int address) {
        Log.d(TAG , "address: " + address + "\tbinaryString: " + Integer.toBinaryString(address));
        byte[] add = new byte[3];
        add[2] = (byte)((address << 24) >> 24);
        add[1] = (byte)((address << 16) >> 24);
        add[0] = (byte)((address << 8) >> 24);
        return add;
    }
    
    private int caculateLength() {
        return 5 + 2 + 3 + mPayload.length + 1;
    }

    private int genCRC() {
        CRC32 crc = new CRC32();
        crc.update(getNonCRCByte());
        
        return (int)crc.getValue();
    }
    
    public String getCode() {
        return mCode;
    }
    public void setmCode(String mCode) {
        this.mCode = mCode;
    }
    public int getLength() {
        return mLength;
    }
    public void setmLength(int mLength) {
        this.mLength = mLength;
    }
    public int getUserAddress() {
        return mUserAddress;
    }
    public void setmUserAddress(int mUserAddress) {
        this.mUserAddress = mUserAddress;
    }
    public byte[] getPayload() {
        return mPayload;
    }
    public void setmPayload(byte[] mPayload) {
        this.mPayload = mPayload;
    }
    public int getCRC() {
        return mCRC;
    }
    public void setmCRC(int mCRC) {
        this.mCRC = mCRC;
    }
}
