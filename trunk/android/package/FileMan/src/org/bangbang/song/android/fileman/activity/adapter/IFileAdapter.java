package org.bangbang.song.android.fileman.activity.adapter;

import java.io.File;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

public interface IFileAdapter 
	extends ListAdapter, SpinnerAdapter
{
	public void changeRootFile(File rootFile);
}
