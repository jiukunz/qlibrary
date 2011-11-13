package com.broadgalaxy.bluz.core;

public class ZrdResponse extends Response {
    int mCheck;
    
    @Override
    int getPayloadLen() {
        // TODO Auto-generated method stub
        return 4;
    }

    @Override
    void parsePayload(byte[] payload) {
        mCheck = Integer.valueOf(new String(payload));
    }

}
