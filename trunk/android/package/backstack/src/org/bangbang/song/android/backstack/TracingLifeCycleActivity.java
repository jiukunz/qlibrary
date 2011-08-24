package org.bangbang.song.android.backstack;

import org.bangbang.song.andorid.common.debug.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

abstract public class TracingLifeCycleActivity extends Activity {
	private static String TAG = TracingLifeCycleActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(getTag(), "onCreate(). savedInstanceState: " + savedInstanceState);
    }
    
    protected abstract String getTag();        

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
        Log.d(getTag(), "onNewIntent(). intent: " + intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		
        Log.d(getTag(), "onPostCreate(). savedInstanceState: " + savedInstanceState);
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		
        Log.d(getTag(), "onPostResume()");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
        Log.d(getTag(), "onRestoreInstanceState(). savedInstanceState: " + savedInstanceState);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub		
        Log.d(getTag(), "onRetainNonConfigurationInstance()");
        
		return super.onRetainNonConfigurationInstance();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
        Log.d(getTag(), "onSaveInstanceState(). outState: " + outState);
	}

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