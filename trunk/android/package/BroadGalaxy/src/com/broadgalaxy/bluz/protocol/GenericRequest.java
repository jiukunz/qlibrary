package com.broadgalaxy.bluz.protocol;

public class GenericRequest extends Request {

    public GenericRequest(String code, int address, byte[] payload) {
        super(code, address, payload);
    }

}
