
package com.broadgalaxy.bluz.component;

import com.broadgalaxy.bluz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Navigation extends ListView {

    private ArrayAdapter<String> mAdapter;
    private OnNavClickListener mListener;
    private String[] mData;

    public Navigation(Context context) {
        super(context);
    }

    public Navigation(Context context, AttributeSet attrs) {
        this(context,attrs, android.R.attr.listViewStyle);
    }

    public Navigation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }
    
    private void init() {
        mData = new String[] {
                "new", "inbox", "outbox", "draft"
        };
        mData = getContext().getResources().getStringArray(R.array.navigation);
        mAdapter = new ArrayAdapter<String>(getContext(), R.layout.navigation_item, R.id.item, mData);
        setAdapter(mAdapter);
        
        setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                if (isEnabled()) {
                    onNavClick(position);
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
        /**
         *  keep sync with {@link #mData}
         */
        public static final int NAV_NEW = 0;
        public static final int NAV_INBOX = 1;
        public static final int NAV_OUTBOX = 2;
        public static final int NAV_DRAFT = 3;
        
        public void onNavClick(int navId);
    }

}
