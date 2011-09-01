
package org.bangbang.song.android.mediaplayer;

import android.app.Activity;
import android.os.Bundle;

public class Demo extends Activity {
    private VideoView mVideoV;
    private MediaController mControllerV;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mVideoV = (VideoView) findViewById(R.id.videoView);
        mVideoV.setVideoPath("/sdcard/1.mp4");
        mControllerV = new MediaController(this);
        mControllerV.setAnchorView(mVideoV);
        mVideoV.setMediaController(mControllerV);
        mVideoV.start();
    }
}
