package com.broadgalaxy.bluz;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

import android.os.Environment;

import com.broadgalaxy.bluz.core.LocationRequest;
import com.broadgalaxy.bluz.core.MessageRequest;
import com.broadgalaxy.bluz.core.Pack;
import com.broadgalaxy.util.Log;

public class Application extends android.app.Application implements UncaughtExceptionHandler {
    public static final String PREF_FILE_NAME= "pref_user";
    public static final String PREF_USER_ID = "pref_user_id";
    private static final String TAG = Application.class.getSimpleName();
    private File appExternalLogDir = new File(Environment.getExternalStorageDirectory(), "broadgalaxy/logs");
    
    public static final boolean RELEASE_MODE = true;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        if (RELEASE_MODE) {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        
        Log.setLog2File(true);
        Log.init(appExternalLogDir);
        Log.d(TAG , "hi world. @@@");
        
        tst();
    }

    private void tst() {
        // TODO Auto-generated method stub
        int fromAddress = 1;
        int toAddress = 3;
        Pack m= new MessageRequest(fromAddress, toAddress, Pack.ENCODE_CODE, "this is my tst");
        Log.d(TAG, "msg: " + m.toHexString());
        
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "uncaughtException in thread: " + thread, ex);         
        Log.e(TAG, "bye bye world. ###");
        
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);        
    }

}
