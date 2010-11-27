package org.bangbang.song.android.fileman;

import org.bangbang.song.android.common.debug.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FileExplorer extends Activity {
	private static final String TAG = FileExplorer.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		test();
	}

	private void test() {
		Intent in = new Intent();
		in.setAction(Intent.ACTION_GET_CONTENT);
		in.setType("*/*");
		in.addCategory(Intent.CATEGORY_OPENABLE);
		
		startActivity(Intent.createChooser(in, "ooxx"));
	}
}
