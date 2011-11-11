package com.broadgalaxy.bluz;

import java.io.File;

import android.os.Environment;

import com.broadgalaxy.util.Log;

public class Application extends android.app.Application {
    public static final String PREF_FILE_NAME= "pref_user";
    public static final String PREF_USER_ID = "pref_user_id";
    private static final String TAG = Application.class.getSimpleName();
    private File appExternalLogDir = new File(Environment.getExternalStorageDirectory(), "broadgalaxy/logs");
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Log.setLog2File(true);
        Log.init(appExternalLogDir);
        Log.d(TAG , "hi world");
    }

}
