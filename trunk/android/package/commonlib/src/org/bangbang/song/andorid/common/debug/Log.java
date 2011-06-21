
package org.bangbang.song.andorid.common.debug;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import android.os.Environment;

/**
 * JUST a wrapper for android.util.Log
 * 
 * @author bangbang.song@gmail.com
 */
public class Log {
    private static String mRootTag = "OOXX Changed by Log.setRootTag(String). ";
    private static final String TAG_DELIMITER = "/";
    private static boolean mLog = true;

    private static boolean mLog2File = true;
    private static Logger mLogger = null;
    private static Handler mFileHandler;
    private static int COUNT = 5;
    private static int LIMIT = 1 * 1024 * 1024;

    public static void setRootTag(String rootTag) {
        mRootTag = rootTag;
    }
    
    public static void setLog(boolean log) {
        Log.mLog = log;
    }

    public static void setLog2File(boolean log2file) {
        log2file = assureCanLog2File();
        Log.mLog2File = log2file;
    }

    private static boolean assureCanLog2File() {
        // TODO Auto-generated method stub
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    private static void _d(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.d(tag, message);
    }

    private static void _df(String tag, String message) {
        // TODO Auto-generated method stub
        mLogger.log(Level.FINE, tag + TAG_DELIMITER + message);
    }

    private static void _e(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.d(tag, message);
    }

    private static void _ef(String tag, String message) {
        mLogger.log(Level.SEVERE, tag + TAG_DELIMITER + message);
    }

    private static void _i(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.i(tag, message);
    }

    private static void _if(String tag, String message) {
        mLogger.log(Level.INFO, tag + TAG_DELIMITER + message);
    }

    private static void _v(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.v(tag, message);
    }

    private static void _vf(String tag, String message) {
        mLogger.log(Level.FINEST, tag + TAG_DELIMITER + message);
    }

    private static void _w(String tag, String message) {
        // TODO Auto-generated method stub
        android.util.Log.w(tag, message);
    }

    private static void _wf(String tag, String message) {
        mLogger.log(Level.WARNING, tag + TAG_DELIMITER + message);
    }

    public static void d(String tag, String message) {
        if (mLog) {
            _d(mRootTag + TAG_DELIMITER + tag, message);
        }

        if (mLog2File) {
            _df(mRootTag + TAG_DELIMITER + tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (mLog) {
            _e(mRootTag + TAG_DELIMITER + tag, message);
        }

        if (mLog2File) {
            _ef(mRootTag + TAG_DELIMITER + tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (mLog) {
            _i(mRootTag + TAG_DELIMITER + tag, message);
        }

        if (mLog2File) {
            _if(mRootTag + TAG_DELIMITER + tag, message);
        }
    }
    
    public static void init(File appExternalLogDir){
        init(appExternalLogDir, "log");
    }

    public static void init(File appExternalLogDir, String logFileName) {
        mLogger = Logger.getAnonymousLogger();
        mLogger.setLevel(Level.ALL);
        
        if (!appExternalLogDir.exists()) {
            final boolean success = appExternalLogDir.mkdirs();
            System.out.print(success);
            mLog2File = false;
            return;
        }

        try {
            mFileHandler = new FileHandler(appExternalLogDir.getPath() + "/" + logFileName + "%g",
                    LIMIT, COUNT);
            mFileHandler.setFormatter(new SimpleFormatter() {

                @Override
                public String format(LogRecord r) {
                    final Date d = new Date(r.getMillis());
                    return "[" + (d.getYear() + 1900) + "/" + d.getMonth() + "/" + d.getDate()
                            + " " + d.getHours()
                            + ":" + d.getMinutes() + ":" + d.getSeconds() + "]" + r.getMessage()
                            + "\n";
                }

            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mLogger.addHandler(mFileHandler);
    }

    public static void v(String tag, String message) {
        if (mLog) {
            _v(mRootTag + TAG_DELIMITER + tag, message);
        }

        if (mLog2File) {
            _vf(mRootTag + TAG_DELIMITER + tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (mLog) {
            _w(mRootTag + TAG_DELIMITER + tag, message);
        }

        if (mLog2File) {
            _wf(mRootTag + TAG_DELIMITER + tag, message);
        }
    }
}
