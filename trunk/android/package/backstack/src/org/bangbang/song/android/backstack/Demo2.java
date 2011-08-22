package org.bangbang.song.android.backstack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Demo2 extends TracingLifeCycleActivity {
	private static final String TAG = Demo2.class.getSimpleName();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    protected void onResume(){
    	super.onResume();
    	
    	startActivity(new Intent(this, Demo3.class));
    }
    
	@Override
	protected String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}
}