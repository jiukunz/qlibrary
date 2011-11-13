package com.broadgalaxy.bluz.core;

import java.nio.ByteBuffer;

abstract public class Response extends Pack {

    public Response(byte[] data) {
        super();
        ByteBuffer buffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
        setCode(Pack.CODE_LOCATION);
        setLength(buffer.getShort(LENGTH_INDEX));
        int address = 0;
        byte[] addressBytes = new byte[ADDRESS_LEN];
        buffer.get(addressBytes, 0, ADDRESS_LEN);
        address = byte2Address(addressBytes);
        setUserAddress(address);
        int payloadLen = getPayloadLen();
        byte[] payload = new byte[payloadLen];
        buffer.get(payload,ADDRESS_INDEX, payloadLen);
        setPayload(payload);
        setCRC(buffer.get(ADDRESS_INDEX + payloadLen));
    }
    
    abstract int getPayloadLen();

}
