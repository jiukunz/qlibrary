package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

public class FbkResponse extends Response {
    int mFlag;
    int mExtra;
    
    public FbkResponse(byte[] msgBytes) {
        super(msgBytes);
    }

    @Override
    int getPayloadLen() {
        return 1 + 4;
    }

    @Override
    void parsePayload(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload).asReadOnlyBuffer();
        mFlag = buffer.get();
        mExtra = buffer.getInt();
    }

    public int getFlag() {
        return mFlag;
    }

    public void setFlag(int mFlag) {
        this.mFlag = mFlag;
    }

    public int getExtra() {
        return mExtra;
    }

    public void setExtra(int mExtra) {
        this.mExtra = mExtra;
    }

}
