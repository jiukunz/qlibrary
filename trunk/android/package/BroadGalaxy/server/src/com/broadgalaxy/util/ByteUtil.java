
package com.broadgalaxy.util;


public class ByteUtil {
    private static final  char[] HEX_ARRAY = new char[]{  '0', '1', '2', '3',
                                                          '4', '5', '6', '7', 
                                                          '8', '9', 'a', 'b', 
                                                          'c', 'd', 'e', 'f'};
    private static String TAG = ByteUtil.class.getSimpleName();
    public static byte[] string2ByteArray(String str) {
//        Log.d(TAG , "before formating: str: " + str);
        str = str.replaceAll("\\s*", "");
//        Log.d(TAG, "after formating: str: " + str);
        int len = str.length();
//        Log.d(TAG, "len: " + len);    
        byte[] array = new byte[len / 2];
        byte b;
        byte h;
        byte l;
        for (int i = 0 ; i < len ; ) {
            h = (byte) (char2Byte(str.charAt(i)) * 16);
            l = char2Byte(str.charAt(i + 1));
            
            b = Byte.valueOf((byte) (h + l));
            array[i / 2] = b;
            
            i += 2;
        }
        
        return array;
    }
    
    static byte char2Byte(char c) {
        byte b = -1 ;
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
            default:
                    Log.e(TAG, "un wanted char: " + c);
                    
        }
        return b;
    }
    
    public static byte[] appendByte(byte[] base, byte[] append) {
        int len = base.length + append.length;
        byte[] result = new byte[len];
        System.arraycopy(base, 0, result, 0, base.length);
        System.arraycopy(append, 0, result, base.length, append.length);
        
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
        return "" + HEX_ARRAY[((byte)(b>>4 & 0x0f))] + HEX_ARRAY[((byte)(b & 0x0f))];
    }
    
    public static String byteArray2HexString(byte[] array){
        String str  = "";
        for (byte b : array) {
            str += byte2HexString(b);
        }
        
        return str;
    }
}
