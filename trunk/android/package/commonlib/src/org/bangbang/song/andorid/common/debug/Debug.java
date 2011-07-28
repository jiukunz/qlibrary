
package org.bangbang.song.andorid.common.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        buildInfo += extractFields(className, fields);

        className = "android.os.Build$VERSION";
        fields = new String[] {
                "CODENAME", "INCREMENTAL", "RELEASE", "SDK", "SDK_INT"
        };
        buildInfo += extractFields(className, fields);

        return buildInfo;
    }

    private static String extractFields(final String className,
            final String[] fields) {
        String buildInfo = "\n    "/* 4 spaces */+ className;
        for (String field : fields) {
            if (!filter(field)) {
                try {
                    Class clazz = Class.forName(className);
                    Field f;

                    f = clazz.getField(field);
                    buildInfo += "\n        "/* 8 spaces */+ field + ": " + f.get(clazz);

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
        String log = "System Log:";

        String command = "/system/bin/dmesg";
        log += extractCommandOutput(command);

        return log;
    }

    private static String extractCommandOutput(String logCommand) {
        String log = "logCommand: " + logCommand;
        try {
            Process p = new ProcessBuilder()
            .command(logCommand)
            .redirectErrorStream(true)
            .start();
            
            InputStream in = p.getInputStream();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = buffReader.readLine()) != null) {
                log += "\n    "/* 4 spaces */ + line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return log;
    }
}
