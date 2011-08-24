package org.bangbang.song.android.backstack;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class Service extends TracingLifeCycleService {
	private static final String TAG = Service.class.getSimpleName();
	private static final String ACTION_START_SERVER = "org.bangbang.song.android.ACTION_START_SERVICE";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}

	public static void start(Context context){
		Intent service	 = new Intent(ACTION_START_SERVER);
		context.startService(service);
	}
}
