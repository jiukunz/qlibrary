package org.bangbang.song.android.common;

import java.io.File;

import org.bangbang.song.android.common.debug.Log;

import android.app.Application;
import android.os.Environment;

public class DemoApp extends Application {
	private static final String TAG = DemoApp.class.getSimpleName();
	
	public static final boolean DBG = true;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initLog();
		
		Log.d(TAG, "hello world.");
	}
	
	private void initLog() {
		// TODO Auto-generated method stub
		Log.init(new File(Environment.getExternalStorageDirectory(), "OOXX"), TAG);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		
		Log.d(TAG, "bye, world.");
	}
}
