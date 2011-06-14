package com.tudou.android.CoverFlowTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;

public class Demo extends Activity {

	public static final String TAG = Demo.class.getSimpleName();

	private AdapterView mSpinner;
	private ImageAdapter mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coverflow);

		
		
		mAdapter = new ImageAdapter(this);
		mSpinner = (AdapterView) findViewById(R.id.gallery);
		mSpinner.setAdapter(mAdapter);
	}


	
}