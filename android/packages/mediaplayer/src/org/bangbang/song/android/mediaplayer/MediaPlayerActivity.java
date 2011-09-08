
package org.bangbang.song.android.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MediaPlayerActivity extends Activity {
    private static final String tag = MediaPlayerActivity.class.getSimpleName();
    
    private VideoView mVideoV;
    private Uri mVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.player);
        mVideoV = (VideoView) findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(mVideoV);
        mVideoV.setMediaController(controller);
        
        parsetIntent();    
    }
    
    private void parsetIntent() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        mVideoUri = intent.getData();
        if (null == mVideoUri) {
            throw new IllegalArgumentException("no uri data.");
        }
        
        mVideoV.setVideoURI(mVideoUri);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        
        if (!mVideoV.isPlaying()) {
            mVideoV.start();
        }
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        
        if (mVideoV.isPlaying()) {
            mVideoV.pause();
        }
    }

}
