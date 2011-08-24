package org.bangbang.song.android.backstack;

import org.bangbang.song.andorid.common.debug.Log;

import android.os.Environment;

public class Application extends android.app.Application {
	private static final String TAG = "backstack";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.setRootTag(TAG );
		Log.setLog(true);
		Log.setLog2File(true);
		Log.init(Environment.getExternalStorageDirectory());
		Log.d(TAG, "hi world@@@");
	}
}
