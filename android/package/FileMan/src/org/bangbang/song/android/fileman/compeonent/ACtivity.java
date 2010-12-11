package org.bangbang.song.android.fileman.compeonent;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.os.Bundle;

public class ACtivity extends Activity {
	protected boolean hasResumed =  false;
	protected boolean hasPaused = false;
	
	protected ComponentController.IComponentCallback mComponentCallback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mComponentCallback = new ActivityComponentCallback();
		ComponentController.getInstance().regComponentCallback(mComponentCallback);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		hasResumed = true;
		hasPaused = false;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		hasPaused = true;
		hasResumed = false;
	}
	
	protected void onUserFinish(){
		finish();
	}
	
	public boolean onUnCaughtException(Thread thread, Throwable throwable) {
		return false;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ComponentController.getInstance().unRegComponentCallback(mComponentCallback);
	}
	
	class ActivityComponentCallback implements ComponentController.IComponentCallback{

		@Override
		public void onUserFinish() {
			// TODO Auto-generated method stub
			ACtivity.this.onUserFinish();
		}

		@Override
		public boolean onUnCaughtException(Thread thread, Throwable throwable) {
			// TODO Auto-generated method stub
			return ACtivity.this.onUnCaughtException(thread, throwable);
		}
		
	}
}
