package com.broadgalaxy.bluz;

import java.io.File;

import android.os.Environment;

import com.broadgalaxy.bluz.core.Location;
import com.broadgalaxy.bluz.core.Msg;
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
        
        tst();
    }

    private void tst() {
        // TODO Auto-generated method stub
        int fromAddress = 1;
        int toAddress = 4;
        Location l = new Location(fromAddress, (byte)1);
        Log.e(TAG, "location: " + l.toHexString());
        Msg m= new Msg(fromAddress, toAddress, Msg.ENCODE_CODE, "this is my tst");
        Log.d(TAG, "msg: " + m.toHexString());
        
    }

}
