package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

public class StsResponse extends Response {
    int mCardState;
    int mAddress;
    int mInState;
    int mPower1;
    int mPower2;
    int mPower3;
    int mPower4;
    int mPower5;
    int mPower6;    
    
    public StsResponse(byte[] msgBytes) {
        super(msgBytes);
    }

    @Override
    int getPayloadLen() {
        return 1 + 3 + 1 + 1 * 6;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer();
        mCardState = buffer.get();
        byte[] addressBytes = new byte[ADDRESS_LEN];
        buffer.get(addressBytes, 0, ADDRESS_LEN);
        mInState = buffer.get();
        mPower1 = buffer.get();
        mPower2 = buffer.get();
        mPower3 = buffer.get();
        mPower4 = buffer.get();
        mPower5 = buffer.get();
        mPower6 = buffer.get();
    }

    public int getCardState() {
        return mCardState;
    }

    public void setCardState(int mCardState) {
        this.mCardState = mCardState;
    }

    public int getAddress() {
        return mAddress;
    }

    public void setAddress(int mAddress) {
        this.mAddress = mAddress;
    }

    public int getInState() {
        return mInState;
    }

    public void setInState(int mInState) {
        this.mInState = mInState;
    }

    public int getPower1() {
        return mPower1;
    }

    public void setPower1(int mPower1) {
        this.mPower1 = mPower1;
    }

    public int getPower2() {
        return mPower2;
    }

    public void setPower2(int mPower2) {
        this.mPower2 = mPower2;
    }

    public int getPower3() {
        return mPower3;
    }

    public void setPower3(int mPower3) {
        this.mPower3 = mPower3;
    }

    public int getPower4() {
        return mPower4;
    }

    public void setPower4(int mPower4) {
        this.mPower4 = mPower4;
    }

    public int getPower5() {
        return mPower5;
    }

    public void setPower5(int mPower5) {
        this.mPower5 = mPower5;
    }

    public int getPower6() {
        return mPower6;
    }

    public void setPower6(int mPower6) {
        this.mPower6 = mPower6;
    }

}
