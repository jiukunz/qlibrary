package org.bangbang.song.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Demo extends Activity {
	private static final String TAG = Demo.class.getSimpleName();
	
	private static ArrayAdapter<String> mAdapter;
	private static ListView mListV;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initAdapter();
    }

	private void initAdapter() {
		mAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, R.id.content);
		
		final int N = 10;
        for (int i = 0 ; i < N ; i++ ) {
        	mAdapter.add("object " + i);
        }
		 mListV = ((ListView)findViewById(android.R.id.list));
		 mListV.setAdapter(mAdapter );
		 
		 mListV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
}