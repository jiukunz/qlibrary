package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;


public class LocationResponse extends Response {    
    private static final int PAYLOAD_LEN = 4 + 4 + 4 + 2 + 2;
    
    private int mLocationT;
    private int mLocationL;
    private int mLocationB;
    private short mLocationH;
    private short mLocationX;

    public LocationResponse(byte[] data) {
        super(data);
    }

    @Override
    int getPayloadLen() {
        return PAYLOAD_LEN;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer();
//        buffer.po
        mLocationT = buffer.getInt();
        mLocationL = buffer.getInt();
        mLocationB = buffer.getInt();
        mLocationH = buffer.getShort();
        mLocationX = buffer.getShort();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\tmLocationT: " + mLocationT + "\tdesc: " + getLocationTStr() + 
               "\tmLocationL: " + mLocationL + "\tdesc: " + getLocationLStr() + 
               "\tmLocationB: " + mLocationB + "\tdesc: " + getLocationBStr() +  
               "\tmLocationH: " + mLocationH + "\tdesc: " + getLocationHStr() +  
               "\tmLocationX: " + mLocationX + "\tdesc: " + getLocationXStr();
    }

    public int getLocationT(){
        return mLocationT;
    }
    
    public String getLocationTStr() {
        return ((mLocationT & 0xff000000) >> 24) + "时 " +
        ((mLocationT & 0xff0000) >> 16) + "分 " +
        ((mLocationT & 0xff00) >> 8) + "." + 
        (((int)(0.01 * (mLocationT & 0xff) * 100)) % 100) + "" + "秒 ";
    }
    
    public int getLocationL() {
        return mLocationL & 0x3fff; // 00 11 1111 1111 1111
    } 
    
    public String getLocationLStr() {
        return ((mLocationL & 0xff000000) >> 24) + "^ " + 
        ((mLocationL & 0xff0000) >> 16) + "' " +
        ((mLocationL & 0xff00) >> 8) + "." + 
        ((mLocationL & 0xff)) + "" +  "\" " ;        
    }
    
    public int getLocationB() {
        return mLocationB;
    }
    
    public String getLocationBStr() {
        return ((mLocationB & 0xff000000) >> 24) + "^ " + 
        ((mLocationB & 0xff0000) >> 16) + "' " +
        ((mLocationB & 0xff00) >> 8) + "." + 
        ((mLocationB & 0xff)) + "" +  "\" " ;    
    }
    
    public short getLocationH() {
        return mLocationH;
    }    
    
    public String getLocationHStr() {
        return "" + mLocationH;
    }
    
    public short getLocationX() {
        return mLocationX;
    }
    
    public String getLocationXStr() {
        return "" + mLocationX;
    }

    public void setLocationT(int mLocationT) {
        this.mLocationT = mLocationT;
    }

    public void setLocationL(int mLocationL) {
        this.mLocationL = mLocationL;
    }

    public void setLocationB(int mLocationB) {
        this.mLocationB = mLocationB;
    }

    public void setLocationH(short mLocationH) {
        this.mLocationH = mLocationH;
    }

    public void setLocationX(short mLocationX) {
        this.mLocationX = mLocationX;
    }
}
