package org.bangbang.song.android.fileman;

import java.io.File;
import java.io.IOException;

import org.bangbang.song.android.common.debug.Log;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 *
 */
public class FileManApplication extends Application {
	private static final String TAG = FileManApplication.class.getSimpleName();
	public static final boolean DBG = true;
	public static final boolean LOG = true;
	
	public static final File APP_EXTERNAL_DIR = new File(Environment.getExternalStorageDirectory(), "FileMan");
	public static final File APP_EXTERNAL_NO_MEDIA = new File(APP_EXTERNAL_DIR, ".noMedia");	
	public static final File APP_EXTERNAL_ETC_DIR = new File(APP_EXTERNAL_DIR, "etc");
	public static final File APP_EXTERNAL_ETC = new File(APP_EXTERNAL_ETC_DIR, "ext2mimetype.map");
	public static final File APP_EXTERNAL_LOG_DIR = new File(APP_EXTERNAL_DIR, "logs");
	public static final File APP_EXTERNAL_CACHE_DIR = new File(APP_EXTERNAL_DIR, "cache");
		
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		firstInit(); // must follow super.onCreate().
		Log.d(TAG, "hi, bangbang.song@gmail.com");
		
		createFileHierachyIfNecessary();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.d(TAG, "bye, bangbang.song@gmail.com");
	}

	private void firstInit() {
		Log.init(APP_EXTERNAL_LOG_DIR, "FileMan");
	}

	private void createFileHierachyIfNecessary() {
		boolean ok = false;
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
			if (!APP_EXTERNAL_DIR.exists()){
				ok = APP_EXTERNAL_DIR.mkdirs();
				if (!ok) {
					Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_DIR.getPath());
					return;
				}
			}
			if (!APP_EXTERNAL_NO_MEDIA.exists()){
				try {
					ok = APP_EXTERNAL_NO_MEDIA.createNewFile();
				} catch (IOException e) {
					Log.d(TAG, e.getMessage());
					Log.d(TAG, "can not create file: " + APP_EXTERNAL_NO_MEDIA.getPath());
					return;
				}
			}
			if (!APP_EXTERNAL_ETC_DIR.exists()){
				ok = APP_EXTERNAL_ETC_DIR.mkdirs();
				if (!ok) {
					Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_ETC_DIR.getPath());
					return;
				}
			}
			if (!APP_EXTERNAL_ETC.exists()){
				try {
					ok = APP_EXTERNAL_ETC.createNewFile();
				} catch (IOException e) {
					Log.d(TAG, e.getMessage());
					Log.d(TAG, "can not create file: " + APP_EXTERNAL_ETC.getPath());
					return;
				}
			}
			if (!APP_EXTERNAL_LOG_DIR.exists()){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				ok = APP_EXTERNAL_LOG_DIR.mkdirs();
				if (!ok) {
					Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_LOG_DIR.getPath());
					return;
				}
			}
			if (!APP_EXTERNAL_CACHE_DIR.exists()){
				ok = APP_EXTERNAL_CACHE_DIR.mkdirs();
				if (!ok) {
					Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_CACHE_DIR.getPath());
					return;
				}
			}
			
			
		}

	}

}
