package org.bangbang.song.android.backstack;

import org.bangbang.song.andorid.common.debug.Log;

import android.app.Activity;
import android.os.Bundle;

abstract public class TracingLifeCycleActivity extends Activity {
	private static String TAG = TracingLifeCycleActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(getTag(), "onCreate()");
    }
    
    protected abstract String getTag();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
        Log.d(getTag(), "onDestory()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
        Log.d(getTag(), "onPause()");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
        Log.d(getTag(), "onRestart()");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
        Log.d(getTag(), "onResume()");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
        Log.d(getTag(), "onStart()");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
        Log.d(getTag(), "onStop()");
	}
	
}