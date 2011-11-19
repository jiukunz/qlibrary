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
        mLocationT = buffer.getInt();
        mLocationL = buffer.getInt();
        mLocationB = buffer.getInt();
        mLocationH = buffer.getShort();
        mLocationX = buffer.getShort();
    }

    public int getLocationT(){
        return mLocationT;
    }
    
    public int getLocationL() {
        return mLocationL;
    }    
    
    public int getLocationB() {
        return mLocationB;
    }
    
    public short getLocationH() {
        return mLocationH;
    }    
    
    public short getLocationX() {
        return mLocationX;
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
