
package org.bangbang.song.android.mediaplayer;

import android.app.Activity;
import android.os.Bundle;

public class Demo extends Activity {
    private VideoView mVideoV;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mVideoV = (VideoView) findViewById(R.id.videoView);
        mVideoV.setVideoPath("/sdcard/1.mp4");
        mVideoV.start();
    }
}
