package com.broadgalaxy.bluz.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.protocol.SigResponse;

public class SigCom extends FrameLayout {

    private SeekBar mSig1;
    private SeekBar mSig6;
    private SeekBar mSig5;
    private SeekBar mSig4;
    private SeekBar mSig3;
    private SeekBar mSig2;

    public SigCom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SigCom(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        View v = inflate(context, R.layout.sig, this);
        mSig1 = (SeekBar) findViewById(R.id.sig1);
        mSig2 = (SeekBar) findViewById(R.id.sig2);
        mSig3 = (SeekBar) findViewById(R.id.sig3);
        mSig4 = (SeekBar) findViewById(R.id.sig4);
        mSig5 = (SeekBar) findViewById(R.id.sig5);
        mSig6 = (SeekBar) findViewById(R.id.sig6);
//        addView(v, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    }

    public SigCom(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public void setSigResponse(SigResponse sig){
        int s = sig.getSig1();
        mSig1.setProgress(toProgress(s));
        
        s = sig.getSig2();
        mSig2.setProgress(toProgress(s));
        
        s = sig.getSig3();
        mSig3.setProgress(toProgress(s));
        
        s = sig.getSig4();
        mSig4.setProgress(toProgress(s));
        
        s = sig.getSig5();
        mSig5.setProgress(toProgress(s));
        
        s = sig.getSig6();
        mSig6.setProgress(toProgress(s));
    }

    private int toProgress(int s) {
        
        return s;
    }
}
