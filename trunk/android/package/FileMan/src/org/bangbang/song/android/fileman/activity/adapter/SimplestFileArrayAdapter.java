package org.bangbang.song.android.fileman.activity.adapter;

import java.io.File;
import java.util.List;

import org.bangbang.song.andorid.common.debug.Log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 *
 */
public class SimplestFileArrayAdapter extends ArrayAdapter<File> implements IFileAdapter{
	private static final String TAG = SimplestFileArrayAdapter.class.getSimpleName();

	private File mRootDir;
	private LayoutInflater mInflater;
	
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
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (null != objects){
			for (File file : objects){
				add(file);
			}
		}
		initData(rootDir);
	}
	
	public void changeRootFile(File rootFile){
		clear();
		initData(rootFile);
		
		notifyDataSetChanged();
	}

	private void initData(File rootFile) {
		mRootDir = rootFile;
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
		return super.getView(position, convertView, parent);
	}
	
	
}
