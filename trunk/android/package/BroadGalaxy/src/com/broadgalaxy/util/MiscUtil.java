package com.broadgalaxy.util;

import com.broadgalaxy.bluz.protocol.Pack;

public class MiscUtil {
    private static final String TAG = MiscUtil.class.getSimpleName();

    public static int userid2int(String userId) {
        int id = 0;
        try {
        if (userId.startsWith("0x") || userId.startsWith("0X")) {
            userId = userId.substring(2);
            id = Pack.byte2Address(ByteUtil.string2ByteArray(userId));
        } else {
            id = Integer.valueOf(userId);
        }
        } catch (Exception e) {
            Log.d(TAG, "Exception", e);
        }
        
        return id;
    }
}
