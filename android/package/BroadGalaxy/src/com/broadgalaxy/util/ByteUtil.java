
package com.broadgalaxy.util;


public class ByteUtil {
    private static final  char[] HEX_ARRAY = new char[]{  '0', '1', '2', '3',
                                                          '4', '5', '6', '7', 
                                                          '8', '9', 'a', 'b', 
                                                          'c', 'd', 'e', 'f'};
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

    public static String byte2HexString(byte b) {
        return "" + HEX_ARRAY[((byte)b>>4 & 0x0f)] + HEX_ARRAY[((byte)b & 0x0f)];
    }
}
