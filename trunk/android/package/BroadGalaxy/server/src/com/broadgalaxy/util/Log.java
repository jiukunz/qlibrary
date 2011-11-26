package com.broadgalaxy.util;

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
 * <p>
 * Firstly, you must toggle log on by {@link #setLog(boolean)}.
 * Secondly, you can toggle log-to-file by {@link #setLog2File(boolean)} and 
 * initiate with by {@link #init(File)} or {@link #init(File, String)}.
 * 
 * @author bysong@tudou.com
 */
public class Log {
    private static String mRootTag = "broadgalaxy";
    private static final String TAG_DELIMITER = ":";
    private static boolean mLog2STDOUT = true;

    private static boolean mLog2File = false;
    private static Logger mLogger = null;
    private static Handler mFileHandler;
    private static int COUNT = 10;
    private static int LIMIT = 1 * 1024 * 1024;

    public static void setRootTag(String rootTag) {
        mRootTag = rootTag;
    }

    public static void setLog(boolean log) {
        Log.mLog2STDOUT = log;
    }

    /**
     * @param log2file
     * 
     * @see #init(File)
     * @see #init(File, String)
     */
    public static void setLog2File(boolean log2file) {
        log2file = log2file && assureCanLog2File();
        Log.mLog2File = log2file;
    }

    private static boolean assureCanLog2File() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    private static void _d(String tag, String message) {
        android.util.Log.d(tag, message);
    }

    private static void _d2logger(String tag, String message) {
        mLogger.log(Level.FINE, tag + TAG_DELIMITER + message);
    }

    private static void _e(String tag, String message) {
        android.util.Log.e(tag, message);
    }

    private static void _e2logger(String tag, String message) {
        mLogger.log(Level.SEVERE, tag + TAG_DELIMITER + message);
    }

    private static void _i(String tag, String message) {
        android.util.Log.i(tag, message);
    }

    private static void _i2logger(String tag, String message) {
        mLogger.log(Level.INFO, tag + TAG_DELIMITER + message);
    }

    private static void _v(String tag, String message) {
        android.util.Log.v(tag, message);
    }

    private static void _v2logger(String tag, String message) {
        mLogger.log(Level.FINEST, tag + TAG_DELIMITER + message);
    }

    private static void _w(String tag, String message) {
        android.util.Log.w(tag, message);
    }

    private static void _w2logger(String tag, String message) {
        mLogger.log(Level.WARNING, tag + TAG_DELIMITER + message);
    }

    public static void d(String tag, String message) {
        if (mLog2STDOUT) {
            _d(mRootTag,  tag + TAG_DELIMITER + message);
        }

        if (mLog2File) {
            _d2logger(mRootTag, tag + TAG_DELIMITER +  message);
        }
    }

    public static void d(String tag, String message, Throwable t) {
        if (mLog2STDOUT) {
            _d(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (mLog2File) {
            _d2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void e(String tag, String message) {
        if (mLog2STDOUT) {
            _e(mRootTag, tag + TAG_DELIMITER + message);
        }

        if (mLog2File) {
            _e2logger(mRootTag, tag + TAG_DELIMITER +  message);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (mLog2STDOUT) {
            _e(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (mLog2File) {
            _e2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void i(String tag, String message) {
        if (mLog2STDOUT) {
            _i(mRootTag, tag + TAG_DELIMITER +  message);
        }

        if (mLog2File) {
            _i2logger(mRootTag, tag + TAG_DELIMITER +  message);
        }
    }

    public static void i(String tag, String message, Throwable t) {
        if (mLog2STDOUT) {
            _i(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (mLog2File) {
            _i2logger(mRootTag,  tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void v(String tag, String message) {
        if (mLog2STDOUT) {
            _v(mRootTag,  tag + TAG_DELIMITER + message);
        }

        if (mLog2File) {
            _v2logger(mRootTag, tag + TAG_DELIMITER +   message);
        }
    }

    public static void v(String tag, String message, Throwable t) {
        if (mLog2STDOUT) {
            _v(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (mLog2File) {
            _v2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    public static void w(String tag, String message) {
        if (mLog2STDOUT) {
            _w(mRootTag, tag + TAG_DELIMITER +  message);
        }

        if (mLog2File) {
            _w2logger(mRootTag, tag + TAG_DELIMITER +  message);
        }
    }

    public static void w(String tag, String message, Throwable t) {
        if (mLog2STDOUT) {
            _w(mRootTag,  tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }

        if (mLog2File) {
            _w2logger(mRootTag, tag + TAG_DELIMITER +
                    message + "\n" + android.util.Log.getStackTraceString(t));
        }
    }

    /**
     * init log dir & file which default to {@code appExternalLogDir}/log.
     * 
     * @param appExternalLogDir
     */
    public static void init(File appExternalLogDir) {
        init(appExternalLogDir, "log");
    }

    /**
     * init log dir & file.
     * 
     * @param appExternalLogDir
     * @param logFileName
     * 
     * @see #setLog2File(boolean)
     */
    public static void init(File appExternalLogDir, String logFileName) {
        boolean error = false;
        mLogger = Logger.getAnonymousLogger();
        mLogger.setLevel(Level.ALL);

        if (!appExternalLogDir.exists() && !appExternalLogDir.mkdirs()) {
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
            e.printStackTrace();
            error = true;
        }

        if (!error) {
            mLogger.addHandler(mFileHandler);
        }
    }
}
