package com.broadgalaxy.bluz.protocol;

public class ZrdResponse extends Response {
    int mCheck;
    
    public ZrdResponse(byte[] msgBytes) {
        super(msgBytes);
    }

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
