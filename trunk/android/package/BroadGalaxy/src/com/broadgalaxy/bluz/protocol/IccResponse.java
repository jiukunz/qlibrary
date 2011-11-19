package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

public class IccResponse extends Response {
    private static final int SERVE_FREQ_INDEX = 3;
    
    int mUserAddress;
    int mServeFreq;
    int mLevel;
    
    public IccResponse(byte[] msgBytes) {
        super(msgBytes);
    }

    @Override
    int getPayloadLen() {
        return 3 + 2 + 1;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer() ;
        byte[] addressBytes = new byte[ADDRESS_LEN];
        buffer.get(addressBytes, 0, ADDRESS_LEN);
        mUserAddress = byte2Address(addressBytes);
        buffer.position(SERVE_FREQ_INDEX);
        mServeFreq = buffer.getShort(SERVE_FREQ_INDEX);
        mLevel = buffer.get();
    }

}
