package com.broadgalaxy.bluz.core;

import java.nio.ByteBuffer;

public class TimResponse extends Response {
    int mYear;
    int mMon;
    int mDay;
    int mHour;
    int mMin;
    int mSec;
    
    public TimResponse(byte[] msgBytes) {
        super(msgBytes);
    }

    @Override
    int getPayloadLen() {
        // TODO Auto-generated method stub
        return 7;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer() ;
        mYear = buffer.getShort();
        mMon = buffer.get();
        mDay = buffer.get();
        mHour = buffer.get();
        mMin = buffer.get();
        mSec = buffer.get();
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMon() {
        return mMon;
    }

    public void setmMon(int mMon) {
        this.mMon = mMon;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmHour() {
        return mHour;
    }

    public void setmHour(int mHour) {
        this.mHour = mHour;
    }

    public int getmMin() {
        return mMin;
    }

    public void setmMin(int mMin) {
        this.mMin = mMin;
    }

    public int getmSec() {
        return mSec;
    }

    public void setmSec(int mSec) {
        this.mSec = mSec;
    }

}
