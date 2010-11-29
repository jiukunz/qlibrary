package org.bangbang.song.android.fileman.activity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 *
 */
public class FileSelector extends FileActivity {
	private final static String TAG = FileSelector.class.getSimpleName();
	private final static boolean DBG = FileManApplication.DBG && true;	
	private static final boolean LOG = FileManApplication.DBG && true;
	
	public static final int OP_MODE_STD = 1 << 1;
	/**
	 * alias for {@link #OP_MODE_STD}
	 */
	public static final int OP_MODE_SINGLE = OP_MODE_STD;
	public static final int OP_MODE_BATCH = 1 << 2;	
	public static final int DEFAULT_OP_MODE = OP_MODE_STD;
	/**
	 * which operation mode we are in.
	 * 
	 * see {@link #OP_MODE_BATCH}, {@link #OP_MODE_SINGLE}, and {@link #OP_MODE_STD}
	 */
	public static final String EXTRA_OP_MODE= "org.bangbang.song.fileman.EXTRA_OP_MODE";
	
	private int mOpMode;
	
	private Context mContext;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void OnFileClick(File clickFile) {
		if (null == clickFile){
			Log.w(TAG, "clicked file is null.");
			return;
		}
		if (clickFile.isDirectory()){
			
			changeRootFile(clickFile);
		} else {
			Intent result = new Intent();
			result.setData(Uri.fromFile(clickFile));
			if (DBG){
				Log.d(TAG, "return result: " + result.toString());
			}
			setResult(RESULT_OK, result);
			finish();
		}
	}
}