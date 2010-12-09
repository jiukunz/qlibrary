package org.bangbang.song.android.fileman.activity;

import java.io.File;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;
import org.bangbang.song.android.fileman.R;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 *
 */
public class FileExplorer extends FileActivity {
	private static final String TAG = FileExplorer.class.getSimpleName();
	private final static boolean DBG = FileManApplication.DBG && true;	
	private static final boolean LOG = FileManApplication.DBG && true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		test();
	}
	
	@Override
	protected void OnFileClick(File clickFile) {
		// TODO Auto-generated method stub
		super.OnFileClick(clickFile);
		if (null == clickFile){
			Log.w(TAG, "clicked file is null.");
			return;
		}
		if (clickFile.isDirectory()){
			changeRootFile(clickFile);
		} else {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.fromFile(clickFile));
			if (DBG){
				Log.d(TAG, "sart activity: " + intent.toString());
			}
			if (canOpenWith(intent.getType())){
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), R.string.no_application_associated_with_this_type_file, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean canOpenWith(String mimeType) {
		Intent intent = new Intent();
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType(mimeType);
		List<ResolveInfo> targets = getPackageManager().queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		
		return !targets.isEmpty();
	}

	private void test() {
		Intent in = new Intent();
		in.setAction(Intent.ACTION_GET_CONTENT);
		in.setType("*/*");
		in.addCategory(Intent.CATEGORY_OPENABLE);
		
		startActivity(Intent.createChooser(in, "ooxx"));
	}
}
