
package com.broadgalaxy.bluz.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.broadgalaxy.bluz.R;

public class Navigation extends ListView {

    private ArrayAdapter<String> mAdapter;
    private OnNavClickListener mListener;
    private String[] mData;

    public Navigation(Context context) {
        super(context);
    }

    public Navigation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Navigation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        mData = new String[] {
                "new", "inbox", "outbox", "draft"
        };
        mAdapter = new ArrayAdapter<String>(getContext(), R.layout.navigation_item, mData);
        setAdapter(mAdapter);
        
        setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView labelV = (TextView) arg1;
                String label = (String) labelV.getText();
                int index = 0;
                for (String s : mData) {
                    if (null != s && s.equalsIgnoreCase(label)) {
                        onNavClick(index);
                        break;
                    }
                }
            }});
    }
    
    protected void onNavClick(int navId) {
        if (null != mListener) {
            mListener.onNavClick(navId);
        }
    }
    
    public void setOnNavListener(OnNavClickListener l) {
        mListener = l;
    }
    
    public interface OnNavClickListener {
        // keep sync with {@link #mData}
        public static final int NAV_NEW = 0;
        public static final int NAV_INBOX = 1;
        public static final int NAV_OUTBOX = 2;
        public static final int NAV_DRAFT = 3;
        
        public void onNavClick(int navId);
    }

}
