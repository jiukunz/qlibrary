
package org.bangbang.song.android.fileman;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bangbang.song.andorid.common.debug.Log;
import org.bangbang.song.android.fileman.component.APplication;
import org.bangbang.song.android.fileman.util.Ext2Mime;

import android.os.Environment;

/**
 * @author bangbang.song@gmail.com 2010-11-29
 */
public class FileManApplication extends APplication {
    private static final String TAG = FileManApplication.class.getSimpleName();
    public static final boolean DBG = true;
    public static final boolean LOG = true;

    public static final File APP_EXTERNAL_DIR = new File(Environment.getExternalStorageDirectory(),
            "FileMan");
    public static final File APP_EXTERNAL_NO_MEDIA = new File(APP_EXTERNAL_DIR, ".noMedia");
    public static final File APP_EXTERNAL_ETC_DIR = new File(APP_EXTERNAL_DIR, "etc");
    public static final File APP_EXTERNAL_EXT_2_MIME_MAP_FILE = new File(APP_EXTERNAL_ETC_DIR,
            "ext2mimetype.map");
    public static final File APP_EXTERNAL_MAP_DIR = new File(APP_EXTERNAL_ETC_DIR, "ext2mimetype.d");
    public static final File APP_EXTERNAL_LOG_DIR = new File(APP_EXTERNAL_DIR, "logs");
    public static final File APP_EXTERNAL_CACHE_DIR = new File(APP_EXTERNAL_DIR, "cache");
    public static final File APP_EXTERNAL_CRASH_DIR = new File(APP_EXTERNAL_DIR, "crash");

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        firstInit(); // must follow super.onCreate().
        String logo = genLogoLiteral();
        Log.i(TAG, "Hi, world ...\n" + logo);
    }

    private String genLogoLiteral() {
        String logo = "";
        InputStream in = getResources().openRawResource(R.raw.aboutme);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        try {
            while ((line = reader.readLine()) != null){
                logo += line + "\n";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logo;
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        Log.d(TAG, "bye, bangbang.song@gmail.com");
    }

    private void firstInit() {
        createFileHierachyIfNecessary();
        Log.init(APP_EXTERNAL_LOG_DIR, "log");
        Log.setRootTag("FileMan");
        Log.setLog(true);
        Log.setLog2File(true);
        Ext2Mime.init(getApplicationContext());
    }

    private void createFileHierachyIfNecessary() {
        boolean ok = false;
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            if (!APP_EXTERNAL_DIR.exists()) {
                ok = APP_EXTERNAL_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_DIR.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_NO_MEDIA.exists()) {
                try {
                    ok = APP_EXTERNAL_NO_MEDIA.createNewFile();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                    Log.d(TAG, "can not create file: " + APP_EXTERNAL_NO_MEDIA.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_ETC_DIR.exists()) {
                ok = APP_EXTERNAL_ETC_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_ETC_DIR.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_MAP_DIR.exists()) {
                ok = APP_EXTERNAL_MAP_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_MAP_DIR.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_EXT_2_MIME_MAP_FILE.exists()) {
                try {
                    ok = APP_EXTERNAL_EXT_2_MIME_MAP_FILE.createNewFile();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                    Log.d(TAG, "can not create file: " + APP_EXTERNAL_EXT_2_MIME_MAP_FILE.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_LOG_DIR.exists()) {
                ok = APP_EXTERNAL_LOG_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_LOG_DIR.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_CACHE_DIR.exists()) {
                ok = APP_EXTERNAL_CACHE_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_CACHE_DIR.getPath());
                    return;
                }
            }
            if (!APP_EXTERNAL_CRASH_DIR.exists()) {
                ok = APP_EXTERNAL_CRASH_DIR.mkdirs();
                if (!ok) {
                    Log.d(TAG, "can not mkdir: " + APP_EXTERNAL_CRASH_DIR.getPath());
                    return;
                }
            }
        }
    }
}
