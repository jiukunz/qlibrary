
package org.bangbang.song.android.mediaplayer;

import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;

public class Demo extends Activity {
    
    private static final String VIDEO_URL_STR = "/mnt/sdcard/1.mp4";
    
    private VideoView mVideoV;
    private MediaController mControllerV;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        mVideoV = (VideoView) findViewById(R.id.videoView);
//        mVideoV.setVideoPath(VIDEO_URL_STR);
//        mControllerV = new MediaController(this);
//        mControllerV.setAnchorView(mVideoV);
//        mVideoV.setMediaController(mControllerV);
//        mVideoV.start();
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(VIDEO_URL_STR), "video/*");
        startActivity(intent);
    }
    

}
