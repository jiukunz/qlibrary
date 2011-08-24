package org.bangbang.song.android.backstack;

import org.bangbang.song.andorid.common.debug.Log;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

abstract public class TracingLifeCycleService extends Service {
	private static String TAG = TracingLifeCycleService.class.getSimpleName();
	
	abstract String getTag();

	/* 
	 * return null.
	 * XXX how 2 java doc in Override function.
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		Log.d(getTag(), "onBind(). intent:" + intent);
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.d(getTag(), "onCreate()");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.d(getTag(), "onDestroy().");
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
		
		Log.d(getTag(), "onRebind(). intent: " + intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		Log.d(getTag(), "onStart(). intent: " + intent + "\t startId: " + startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(getTag(), "onUnbind(). intent: " + intent);
		
		return super.onUnbind(intent);
	}
	
}