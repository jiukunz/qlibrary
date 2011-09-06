
package org.bangbang.song.android.mediaplayer.compatibility;

import java.lang.reflect.InvocationTargetException;

import org.bangbang.song.android.media.MediaPlayerTracker;
import org.bangbang.song.android.media.MediaPlayerTracker.VideoPlayerEventTracker;

/**
 * @author bangbang.song@gmail.com to support compatibility suspend() & resume()
 *         methods.
 */
public class CompatibilityMediaPlayer extends
//        MediaPlayer
        MediaPlayerTracker
        {
    private static final String METHOD_NAME_SUSPEND = "suspend";
    private static final String METHOD_NAME_RESUME = "resume";
    
    public CompatibilityMediaPlayer(){
        this(new MediaPlayerTracker.VideoPlayerEventTracker());
    }
    
    public CompatibilityMediaPlayer(VideoPlayerEventTracker tracker) {
        super(tracker);
        // TODO Auto-generated constructor stub
    }

    public boolean suspend() {
        try {
            return (Boolean) getClass().getMethod(METHOD_NAME_SUSPEND, null).invoke(this, null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean resume() {
        try {
            return (Boolean) getClass().getMethod(METHOD_NAME_RESUME, null).invoke(this, null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
