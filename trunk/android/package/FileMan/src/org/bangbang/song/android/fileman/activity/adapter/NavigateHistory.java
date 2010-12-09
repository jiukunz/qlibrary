package org.bangbang.song.android.fileman.activity.adapter;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;

import android.net.Uri;

public class NavigateHistory implements INavigateHistory {
	private static final String TAG = NavigateHistory.class.getSimpleName();
	private static final boolean DBG = FileManApplication.DBG && true;
	
	private List<Uri> mHistoryList;
	private Uri mCurrentUri;
	
	
	public NavigateHistory() {
		// TODO Auto-generated constructor stub
		mHistoryList = new LinkedList<Uri>();
	}
	
	@Override
	public Uri getCurrentUri(){
		return mCurrentUri;
	}

	@Override
	public void addHistory(Uri uri){
		mHistoryList.add(uri);
		mCurrentUri = uri;
		
		if (DBG){
			Log.d(TAG, dump());
		}
	}
	
	@Override
	public void backward() {
		// TODO Auto-generated method stub
		if (!canBackward()){
			Log.e(TAG, "can not bachward.");
			return;
		}
		

		mCurrentUri = mHistoryList.get(mHistoryList.indexOf(mCurrentUri) - 1);
		
		if (DBG){
			Log.d(TAG, dump());
		}
	}

	@Override
	public boolean canBackward() {
		// TODO Auto-generated method stub
		return mHistoryList.indexOf(mCurrentUri) > 0;
	}

	@Override
	public boolean canForward() {
		// TODO Auto-generated method stub
		return mHistoryList.indexOf(mCurrentUri) < mHistoryList.size();
	}

	@Override
	public boolean canUp() {
		// TODO Auto-generated method stub
		File file = new File(URI.create(mCurrentUri.toString()));
		if (file.getPath().equals("/")){
			return false;
		}
		file = file.getParentFile();
		if (file.getPath().equals("/")){
			return false;
		}
		
		return true;
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		if (!canForward()){
			Log.e(TAG, "can not forward.");
			return;
		}
		
		mCurrentUri = mHistoryList.get(mHistoryList.indexOf(mCurrentUri) + 1);
		
		if (DBG){
			Log.d(TAG, dump());
		}
	}

	@Override
	public void up() {
		// TODO Auto-generated method stub
		if (!canUp()){
			Log.e(TAG, "can not up.");
			return;
		}
		File file = new File(URI.create(mCurrentUri.toString())).getParentFile();
		int index = mHistoryList.indexOf(mCurrentUri);
		mCurrentUri = Uri.fromFile(file);
		mHistoryList.add(index + 1, Uri.fromFile(file));
		
		if (DBG){
			Log.d(TAG, dump());
		}
	}

	// internal function
	private String dump(){
		String dump = "";
		int index = mHistoryList.indexOf(mCurrentUri);
		for (int N = mHistoryList.size(), i = N - 1; i >= 0 ; i --){
			dump += "\n:" + i + "\t " + mHistoryList.get(i).toString() + (i == index ? " (*)" : "");
		}
		return dump;
	}
}
