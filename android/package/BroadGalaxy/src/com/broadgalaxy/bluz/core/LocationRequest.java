package com.broadgalaxy.bluz.core;

public class LocationRequest extends Request {
    public LocationRequest(int address, byte freqency){
        super(CODE_LOCATION, address, toPayload(freqency));
    }

    private static byte[] toPayload(byte freqency) {
        byte[] payload = new byte[1];
        payload[0] = freqency;
        return payload;
    }
}
