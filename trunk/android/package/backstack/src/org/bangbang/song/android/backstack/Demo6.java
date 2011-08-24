package org.bangbang.song.android.backstack;

import android.app.Activity;
import android.os.Bundle;

public class Demo6 extends TracingLifeCycleActivity {
	private static final String TAG = Demo6.class.getSimpleName();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


	@Override
	protected String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Service.start(getApplicationContext());
	}
}