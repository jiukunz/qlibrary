package com.broadgalaxy.bluz.protocol;

public class SigRequest extends Request {
    public SigRequest(int address, byte freqency){
        super(CODE_SIG, address, toPayload(freqency));
    }

    private static byte[] toPayload(byte freqency) {
        byte[] payload = new byte[1];
        payload[0] = freqency;
        return payload;
    }
}
