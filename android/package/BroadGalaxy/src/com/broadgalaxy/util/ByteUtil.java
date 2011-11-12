
package com.broadgalaxy.util;


public class ByteUtil {
    
    public static byte[] appendByte(byte[] base, byte[] append) {
        int len = base.length + append.length;
        byte[] result = new byte[len];
        System.arraycopy(base, 0, result, 0, base.length);
        System.arraycopy(append, 0, result, 0, append.length);
        
        return result;
    }

    public static void assureByte(String str, int byteCount) {
        byte[] bytes = str.getBytes();
        if (byteCount != bytes.length) {
            String detailMessage = "expect byteCount: " + byteCount + "\tactual byteCount: "
                    + bytes.length;
            
            throw new IllegalStateException(detailMessage);
        }
    }
}
