package org.bangbang.song.android.common.debug;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import android.text.format.DateFormat;

/**
 * TODO wrap a log util.
 * <p>
 * <b>call</b> {@link #init(String)} to initiate configuration.
 * 
 * @author Administrator
 *
 */
public class Log {	
	private static final int LOG_COUNT = 5;
	private static final int LOG_LIMIT = 1 * 1024 * 1024; // 1M
	
	private static String mLogTag;
	private static Logger mLogger;
	private static Handler mHandler;

	public static void init(File logDir, String logTag){
		mLogTag = logTag;
		try {
			String filePattern = logDir.getPath() + "/log" + ".%g";
			mHandler = new FileHandler(filePattern, LOG_LIMIT, LOG_COUNT);
			mHandler.setFormatter(new AndroidFormatter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mLogger = Logger.getLogger(mLogTag);
		mLogger.setLevel(Level.ALL);
		if (mHandler != null){
			mLogger.addHandler(mHandler);
		}
	}

	public static void d(String tag, String message) {
		_d(tag, message);
	}

	private static void _d(String tag, String message) {
		android.util.Log.d(mLogTag, tag + " : " + message);
		mLogger.fine(tag + " (D): " + message + "\n");
	}
	 
	static class AndroidFormatter extends Formatter{
		private static final String sFormat = "yyyy MM dd hh:mmaa";
		public AndroidFormatter() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String format(LogRecord r) {
			return DateFormat.format(sFormat, r.getMillis()) + " : " + r.getMessage();
		}
		
	}

	public static void w(String tag, String message) {
		_w(tag, message);
	}

	private static void _w(String tag, String message) {
		android.util.Log.w(mLogTag, tag + " : " + message);
		mLogger.fine(tag + " (W): " + message + "\n");	}
}
