package com.bangbangsong.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class TestView extends TextView {
	
	public TestView(Context context){
		this(context, null);
	}

	public TestView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	
	public TestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TestView, defStyle, 0);
		String mystr = a.getString(R.styleable.TestView_ooxx);
	}

}
