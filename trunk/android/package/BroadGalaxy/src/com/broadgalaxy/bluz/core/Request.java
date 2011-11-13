package com.broadgalaxy.bluz.core;

import java.util.zip.CRC32;

abstract public class Request extends Pack {

    public Request(String code, int address, byte[] payload) {
        super();
        mCode = code;
        mUserAddress = address;
        mPayload = payload;
        mLength = caculateLength();
        mCRC = genCRC();
    }

    private int caculateLength() {
        return 5 + 2 + 3 + mPayload.length + 1;
    }

    private int genCRC() {
        CRC32 crc = new CRC32();
        crc.update(getNonCRCByte());
        
        return (int)crc.getValue();
    }
}
