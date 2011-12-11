package com.broadgalaxy.bluz.protocol;

public class IccRequest extends Request {
    public IccRequest(){
        super(CODE_ICC, 0, new byte[]{});
    }
}
