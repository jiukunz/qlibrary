package org.bangbang.song.android.fileman.activity;

import org.bangbang.song.android.fileman.R;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about);
		setTheme(android.R.style.Theme_Dialog);
	}
}
