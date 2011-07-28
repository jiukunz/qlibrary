package org.bangbang.song.CycleAdapter;

import java.util.ArrayList;
import java.util.Collection;

import org.bangbang.song.andorid.adapter.CycleAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class Demo extends Activity {
	BaseAdapter mAdapter;
	ListView mListV;
	Collection mDataSet;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDataSet = new ArrayList();
        for (int i = 0; i < 15 ; i ++ ){
        	mDataSet.add("string " + i);
        }
        mAdapter = new ArrayAdapter(this, R.layout.item, R.id.text, mDataSet.toArray());
        mAdapter = new CycleAdapter(mAdapter);
        mListV = ((ListView)findViewById(R.id.list));
        mListV.setAdapter(mAdapter);
        mListV.setSelection(((CycleAdapter)mAdapter).getSuggestIniSelectPosition());
    }
}