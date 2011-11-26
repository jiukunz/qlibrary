package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

public class SigResponse extends Response {
    int mSig1;
    int mSig2;
    int mSig3;
    int mSig4;
    int mSig5;
    int mSig6;
    
    public SigResponse(byte[] data) {
        super(data);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return super.toString() + 
                "\tsig1: " + mSig1 +
                "\tsig2: " + mSig2 +
                "\tsig3: " + mSig3 +
                "\tsig4: " + mSig4 +
                "\tsig5: " + mSig5 +
                "\tsig6: " + mSig6;
    }
    
    @Override
    int getPayloadLen() {
        return 6;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer();
        mSig1 = buffer.get();
        mSig2 = buffer.get();
        mSig3 = buffer.get();
        mSig4 = buffer.get();
        mSig5 = buffer.get();
        mSig6 = buffer.get(); 
    }

    public int getSig1() {
        return mSig1;
    }

    public void setSig1(int mSig1) {
        this.mSig1 = mSig1;
    }

    public int getSig2() {
        return mSig2;
    }

    public void setSig2(int mSig2) {
        this.mSig2 = mSig2;
    }

    public int getSig3() {
        return mSig3;
    }

    public void setSig3(int mSig3) {
        this.mSig3 = mSig3;
    }

    public int getSig4() {
        return mSig4;
    }

    public void setSig4(int mSig4) {
        this.mSig4 = mSig4;
    }

    public int getSig5() {
        return mSig5;
    }

    public void setSig5(int mSig5) {
        this.mSig5 = mSig5;
    }

    public int getSig6() {
        return mSig6;
    }

    public void setSig6(int mSig6) {
        this.mSig6 = mSig6;
    }

}
