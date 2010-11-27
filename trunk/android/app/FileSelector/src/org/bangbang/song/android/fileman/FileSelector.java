package org.bangbang.song.android.fileman;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Administrator
 *
 */
public class FileSelector extends Activity {
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
	
	public static final String EXTRA_FILE_PATH_REGREX = "org.bangbang.song.fileman.EXTRA_FILE_PATH_REGREX";		
	public static final String DEFAULT_FILE_PATH_REGREX = ".*";
	public static final int DIR = 1 << 1;
	public static final int NON_DIR = 1 << 2;
	public static final int ALL = DIR | NON_DIR;
	public static final int DEFAULT_FILTER_TYPE = NON_DIR;
	/**
	 * dir or no-dir or all.
	 * 
	 * see {@link FileSelector#DIR}, {@link FileSelector#NON_DIR}, and {@link FileSelector#ALL}.
	 */
	public static final String EXTRA_FILE_TYPE = "org.bangbang.song.fileman.EXTRA_FILE_REGREX";
	public static final String DEFAUTL_PREFERENCE_ROOT_PATH = "/";
	public static final String EXTRA_PREFERENCE_ROOT_PATH = "org.bangbang.song.fileman.EXTRA_PREFERENCE_ROOT_PATH";
	
	private int mOpMode;
	private String mFilterRegrex;
	private int mFilterType;
	private String mPrefPath;
	
	private Context mContext;
	private ListView mListV;
	private OnItemClickListener mItemClickListener;
	private SimplestFileArrayAdapter mAdapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = getApplicationContext();
        initWidget();
        parsetIntent();
        initApadter();        
    }

	private void initWidget() {
		mListV = ((ListView)findViewById(android.R.id.list));
		mListV.setClickable(true);	
		mItemClickListener = new ItemClickListener();
        mListV.setOnItemClickListener(mItemClickListener);
	}
    
	private void initApadter() {
		List<File> initFiles = new ArrayList<File>();
		mAdapter = new SimplestFileArrayAdapter(mContext, 
				R.layout.simple_file_adapter, 
				R.id.fileName, 
				initFiles, 
				new File(mPrefPath));	
		mListV.setAdapter(mAdapter);
	}

	private void parsetIntent() {
		Intent intent = getIntent();
		if (null == intent){
			mOpMode = DEFAULT_OP_MODE;
			mFilterType = DEFAULT_FILTER_TYPE;
			mFilterRegrex = DEFAULT_FILE_PATH_REGREX;
			mPrefPath = DEFAUTL_PREFERENCE_ROOT_PATH;
			return;
		}
		
		mOpMode = intent.getIntExtra(EXTRA_OP_MODE, DEFAULT_OP_MODE);
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
			Log.d(TAG, "mOpMode: " + mOpMode);
			Log.d(TAG, "mFilterType: " + mFilterType);
			Log.d(TAG, "mFilterRegrex: " + mFilterRegrex);
			Log.d(TAG, "mPrefPath: " + mPrefPath);
		}
	}
	
	public void doItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			doItemClick(parent, view, position, id);
		}
		
	}
}