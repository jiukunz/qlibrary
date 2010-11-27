package org.bangbang.song.android.fileman;

import java.io.File;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SimplestFileArrayAdapter extends ArrayAdapter<File> {
	private static final String TAG = SimplestFileArrayAdapter.class.getSimpleName();

	private File mRootDir;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public SimplestFileArrayAdapter(Context context, int resource,
			int textViewResourceId, List<File> objects, File rootDir) {
		super(context, resource, textViewResourceId, objects);
		mRootDir = rootDir;
		
		if (null != objects){
			for (File file : objects){
				add(file);
			}
		}
		initData(rootDir);
	}
	
	public void setRootFile(File rootFile){
		clear();
		initData(rootFile);
	}

	private void initData(File rootFile) {
		if (null ==  mRootDir){
			mRootDir = new File("/");
			Log.d(TAG, "mRootDir is null, default to " + mRootDir.getPath());
		}
		File[] files = mRootDir.listFiles();
		for (File file: files){
			add(file);
		}	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		view = super.getView(position, convertView, parent);
		view.setFocusableInTouchMode(true);
		view.setFocusable(true);
		view.setClickable(true);
		
		return view;
	}
	
	
}
