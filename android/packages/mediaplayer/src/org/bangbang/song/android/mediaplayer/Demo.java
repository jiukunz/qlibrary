
package org.bangbang.song.android.mediaplayer;

import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Demo extends Activity {
    
    private static final String VIDEO_URL_STR = "http://221.130.190.3:80/work/51/098/673/367/51.mp4";
    
    private VideoView mVideoV;
    private MediaController mControllerV;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mVideoV = (VideoView) findViewById(R.id.videoView);
        mVideoV.setVideoPath(VIDEO_URL_STR);
//        mControllerV = new MediaController(this);
//        mControllerV.setAnchorView(mVideoV);
        mVideoV.setMediaController(mControllerV);
        mVideoV.start();
        
//        ((ViewGroup)findViewById(R.id.container)).addView(makeViewFormExternalResource(), 0);
    }
    
    View makeViewFormExternalResource(){
        View v = null;
        PackageManager pm = getPackageManager();
        final String packageName = "org.bangbang.song.andorid.common";
        final LayoutInflater inflater = LayoutInflater.from(this);
//        try {
//            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//            XmlPullParser parser = appInfo.loadXmlMetaData(pm, "lib");
//            parser = pm.getXml(packageName, 0x7f030000, null);
//            Resources res = pm.getResourcesForApplication(packageName);
//            v = inflater.inflate(parser, null);
//        } catch (NameNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new InputStreamReader(getResources().openRawResource(R.raw.lib)));
            AttributeSet set = Xml.asAttributeSet(parser);
            v = inflater.inflate(parser, null);
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return v;
    }
}
