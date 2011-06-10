package org.bangbang.song.android.fileman.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bangbang.song.andorid.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;
import org.bangbang.song.android.fileman.R;
import org.bangbang.song.android.fileman.activity.adapter.INavigateHistory;
import org.bangbang.song.android.fileman.activity.adapter.NavigateHistory;
import org.bangbang.song.android.fileman.activity.adapter.SimplestFileArrayAdapter;
import org.bangbang.song.android.fileman.component.LIstActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 * 
 * <p>
 * provide file(s) to user, user can override {@link #OnFileClick(File)} to
 * do what they want to do.
 *
 */
 public class FileActivity extends LIstActivity {
	private static final String TAG = FileActivity.class.getSimpleName();
	private final static boolean DBG = FileManApplication.DBG && true;	
	private static final boolean LOG = FileManApplication.DBG && true;
	
	public static final String EXTRA_FILE_PATH_REGREX = "org.bangbang.song.fileman.EXTRA_FILE_PATH_REGREX";		
	public static final String DEFAULT_FILE_PATH_REGREX = ".*";
	
	public static final int FILTER_NONE = 1 << 1;
	public static final int FILTER_DIR = 1 << 2;
	public static final int FILTER_NON_DIR = 1 << 3;
	public static final int FILTER_ALL = FILTER_DIR | FILTER_NON_DIR;
	public static final int DEFAULT_FILTER_TYPE = FILTER_NONE;
	/**
	 * dir or no-dir or all.
	 * 
	 * see {@link FileSelector#DIR}, {@link FileSelector#NON_DIR}, and {@link FileSelector#ALL}.
	 */
	public static final String EXTRA_FILE_TYPE = "org.bangbang.song.fileman.EXTRA_FILE_REGREX";
	public static final String DEFAUTL_PREFERENCE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
	public static final String EXTRA_PREFERENCE_ROOT_PATH = "org.bangbang.song.fileman.EXTRA_PREFERENCE_ROOT_PATH";
	
	private String mFilterRegrex;
	private int mFilterType;
	private String mPrefPath;
	
	private SimplestFileArrayAdapter mAdapter;
	private Context mContext;
	
	private INavigateHistory mNavigateHistory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate().");
		mContext = getApplicationContext();
		mNavigateHistory = new NavigateHistory();
//		regListeners();
		parsetIntent();
		initApadter();
		mNavigateHistory.newHistory(Uri.fromFile(new File(mPrefPath)));
	}
	
	public void changeRootFile(File rootFile){
		mNavigateHistory.newHistory(Uri.fromFile(rootFile));
		mPrefPath = rootFile.getPath();
		mAdapter.changeRootFile(rootFile);
	}
	
	private void parsetIntent() {
		Intent intent = getIntent();
		if (null == intent){
//			mOpMode = DEFAULT_OP_MODE;
			mFilterType = DEFAULT_FILTER_TYPE;
			mFilterRegrex = DEFAULT_FILE_PATH_REGREX;
			mPrefPath = DEFAUTL_PREFERENCE_ROOT_PATH;
			return;
		}
		
//		mOpMode = intent.getIntExtra(EXTRA_OP_MODE, DEFAULT_OP_MODE);
		mFilterType = intent.getIntExtra(EXTRA_FILE_TYPE, DEFAULT_FILTER_TYPE);
		mFilterRegrex = intent.getStringExtra(EXTRA_FILE_PATH_REGREX);
		if (null == mFilterRegrex || mFilterRegrex.length() == 0){
			mFilterRegrex = DEFAULT_FILE_PATH_REGREX;
		}
		mPrefPath = intent.getStringExtra(EXTRA_PREFERENCE_ROOT_PATH);
		if (null == mPrefPath || mPrefPath.length() == 0){
			mPrefPath = DEFAUTL_PREFERENCE_ROOT_PATH;
		}
		
		if (DBG){
//			Log.d(TAG, "mOpMode: " + mOpMode);
			Log.d(TAG, "mFilterType: " + mFilterType);
			Log.d(TAG, "mFilterRegrex: " + mFilterRegrex);
			Log.d(TAG, "mPrefPath: " + mPrefPath);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.file_activity_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			showAbout();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void showAbout() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void regListeners() {
		getListView().setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onClick().");
			}});
		getListView().setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "onItemClick().");
			}});
		getListView().setOnItemSelectedListener(new ListView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onItemSeelceted().");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		File clickFile = (File) l.getItemAtPosition(position);
		if (null != clickFile){
			OnFileClick(clickFile);
		}
	}
	
	/**
	 * must call this when you override it.
	 *
	 * @param clickFile
	 */
	protected void OnFileClick(File clickFile){
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode){
		case KeyEvent.KEYCODE_BACK: // fall through
		case KeyEvent.KEYCODE_CLEAR:
			if (mNavigateHistory.canBackward()){
				mNavigateHistory.backward();
				changeRootFile(new File(mNavigateHistory.getCurrentUri().getPath()));
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}
	
	private void initApadter() {
		List<File> initFiles = new ArrayList<File>();
		mAdapter = new SimplestFileArrayAdapter(mContext, 
				R.layout.simple_file_adapter, 
				R.id.fileName, 
//				android.R.layout.simple_list_item_1,
//				android.R.id.text1, 
				initFiles, 
				new File(mPrefPath));	
		setListAdapter(mAdapter);
	}
}
