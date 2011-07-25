package com.tudou.android.CoverFlowTest;

import java.io.File;

import org.bangbang.song.andorid.common.debug.Log;

import android.app.Application;
import android.os.Environment;

public class App extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.setRootTag("CoverFlowTest");
		Log.init(new File(Environment.getExternalStorageDirectory(), "coverflow/logs"), "log");
		Log.setLog(true);
		Log.setLog2File(true);
		}
	
}
