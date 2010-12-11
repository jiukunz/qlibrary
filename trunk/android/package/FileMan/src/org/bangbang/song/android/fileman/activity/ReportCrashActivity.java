package org.bangbang.song.android.fileman.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;
import org.bangbang.song.android.fileman.R;
import org.bangbang.song.android.fileman.compeonent.ACtivity;
import org.bangbang.song.android.fileman.compeonent.ComponentController;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ReportCrashActivity extends ACtivity 
	implements OnClickListener
{
	private static final String TAG = ReportCrashActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_crash);
		findViewById(R.id.confirm).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
	}
	
	String collectExceptionInfo(){
		String info = "";
		File exFile = new File(FileManApplication.APP_EXTERNAL_CRASH_DIR.getPath() + "/exception.txt");
		if (exFile.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(exFile));
				String line = "";
				try {
					while ((line = reader.readLine()) != null){
						info += line;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d(TAG, e.getMessage());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.d(TAG, e.getMessage());
			}
		}
		return info;
	}
	
	String collectEnvInfo(){
		String envInfo = "";
		
		return envInfo;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.confirm:
			report();
//			break;     // fall through
		case R.id.cancel:
			ComponentController.getInstance().performUserFinish();
			break;
		}
	}

	private void report() {
		// TODO Auto-generated method stub
		Intent mailto = new Intent(Intent.ACTION_SENDTO);
		mailto.setData(Uri.parse("mailto:jober_song@126.com"));
		startActivity(mailto);		
	}
}
