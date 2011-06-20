
package org.bangbang.song.andorid.common.debug;

import java.lang.reflect.Field;

public class Debug {
    public static final String debugInfo() {
        return buildInfo() + "\n" + collectSystemLog();
    }

    public static final String buildInfo() {
        String buildInfo = "BuildInfo:";
        
        String className = "android.os.Build";
        String[] fields = new String[] {
                "BOARD", "BOOTLOADER", "BRAND", "CPU_ABI", "CPU_ABI2",
                "DEVICE", "DISPLAY", "FINGERPRINT", "HARDWARE", "HOST", "ID", "MANUFACTURER",
                "MODEL",
                "PRODUCT", "RADIO", "SERIAL", "TAGS", "TIME", "TYPE", "USER"
        };

        buildInfo += extractPublicFields(className, fields);
        
        className = "android.os.Build$VERSION";
        fields = new String[]{"CODENAME", "INCREMENTAL", "RELEASE", "SDK", "SDK_INT"};
        buildInfo += extractPublicFields(className, fields);

        return buildInfo;
    }

    private static String extractPublicFields(final String className,
            final String[] fields) {
        String buildInfo = "\n    "/*4 spaces*/ + className;
        for (String field : fields) {
            if (!filter(field)) {
                try {
                    Class clazz = Class.forName(className);
                    Field f;

                    f = clazz.getField(field);
                    buildInfo += "\n        "/* 8 spaces*/ + field + ": " + f.get(clazz);

                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                // going on
            }
        }
        return buildInfo;
    }

    private static boolean filter(String field) {
        // TODO Auto-generated method stub
        return false;
    }

    public static final String collectSystemLog() {
        String log = "";

        return log;
    }
}
