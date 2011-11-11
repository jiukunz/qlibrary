
package com.broadgalaxy.util;

import java.lang.reflect.Field;

public class ReflecUtil {
    private static String TAG = ReflecUtil.class.getSimpleName();

    public static String fieldName(Class clazz, String fieldPrefix, int fieldValue) {
        String name = "un known name";
        Field[] fields = clazz.getFields();
        String fname;
        int fvalue;
        for (Field f : fields) {
            if (null != f) {
                fname = f.getName();
                if (fname.startsWith(fieldPrefix)) {
                    try {
                        fvalue = f.getInt(null);
                        if (fieldValue == fvalue) {
                            name = fname;
                        }
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, "IllegalArgumentException", e);// it's safe
                    } catch (IllegalAccessException e) {
                        Log.e(TAG, "IllegalArgumentException", e);// it's safe
                    }
                }
            }
        }
        
        return name;
    }
}
