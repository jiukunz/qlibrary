package com.broadgalaxy.bluz.protocol;

import java.util.zip.CRC32;

import com.broadgalaxy.util.Log;

public class Request extends Pack {
    String TAG = Request.class.getSimpleName();

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
        byte c = 0;
        byte[] nonCrcBytes = getNonCRCByte();
        boolean first = true;
        for (byte b : nonCrcBytes) {
            if (first) {
                first = !first;
                c = b;
            } else {
                c ^= b;
            }
        }
        
        if (true) {
            return c;
        }
        crc.update(getNonCRCByte());
        
        long crcV = crc.getValue();
        Log.d(TAG, "crcV: " + crcV);
        Log.d(TAG, "binary: " + Long.toHexString(crcV));
//        Log.d(TAG, "binary: " + crcV)
        return (int)crc.getValue();
    }
}
