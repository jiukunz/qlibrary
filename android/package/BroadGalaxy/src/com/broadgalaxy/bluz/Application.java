package com.broadgalaxy.bluz;

import com.broadgalaxy.util.Log;

public class Application extends android.app.Application {
    public static final String PREF_FILE_NAME= "pref_user";
    public static final String PREF_USER_ID = "pref_user_id";
    private static final String TAG = Application.class.getSimpleName();
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Log.d(TAG , "hi world");
    }

}
