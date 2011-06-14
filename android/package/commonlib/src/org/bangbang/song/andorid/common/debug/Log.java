
package org.bangbang.song.andorid.common.debug;

import java.io.File;

import android.util.*;

/**
 * @author bangbang.song@gmail.com JUST a wrapper for android.util.Log
 */
public class Log {

    public static void d(String tag, String string) {
        // TODO Auto-generated method stub
        android.util.Log.d(tag, string);
    }

    public static void e(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.d(tag, message);
    }

    public static void init(File appExternalLogDir, String string) {
        // TODO Auto-generated method stub

    }

    public static void w(String tag, String string) {
        // TODO Auto-generated method stub
        android.util.Log.w(tag, string);
    }
}
