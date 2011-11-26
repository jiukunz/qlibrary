package com.broadgalaxy.bluz.protocol;

import java.nio.ByteBuffer;

import com.broadgalaxy.util.Log;

abstract public class Response extends Pack {
    private static final String TAG = Response.class.getSimpleName();
    private static final boolean DEBUG = true;
    
    public Response(byte[] data) {
        super();
        ByteBuffer buffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
        
        buffer.position(0);
        byte[] codeBytes = new byte[CODE_LEN];
        buffer.get(codeBytes, 0, CODE_LEN);
        String code = new String(codeBytes);
        if (DEBUG) {
            Log.d(TAG, "code: " + code);
        }
        setCode(code);        
        
        setLength(buffer.getShort(LENGTH_INDEX));
        
        int address = 0;
        byte[] addressBytes = new byte[ADDRESS_LEN];
        buffer.position(ADDRESS_INDEX);
        buffer.get(addressBytes, 0, ADDRESS_LEN);
        address = byte2Address(addressBytes);
        setUserAddress(address);
        
        int payloadLen = getPayloadLen();
        byte[] payload = new byte[payloadLen];
        buffer.position(PAYLOAD_INDEX);
        buffer.get(payload, 0, payloadLen);
        setPayload(payload);
        
        parsePayload(payload);
        
        setCRC(buffer.get(ADDRESS_INDEX + payloadLen));
    }
    
    public Response(){
        super();
    }
    
    abstract int getPayloadLen();
    
    abstract void parsePayload(byte[] payload);

}
