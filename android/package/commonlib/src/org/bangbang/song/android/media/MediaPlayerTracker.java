
package org.bangbang.song.android.media;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayerTracker extends android.media.MediaPlayer
        implements
        android.media.MediaPlayer.OnErrorListener,
        android.media.MediaPlayer.OnInfoListener,
        android.media.MediaPlayer.OnBufferingUpdateListener,
        android.media.MediaPlayer.OnCompletionListener,
        android.media.MediaPlayer.OnPreparedListener,
        android.media.MediaPlayer.OnSeekCompleteListener,
        android.media.MediaPlayer.OnVideoSizeChangedListener {

    private VideoPlayerEventTracker mTracker;

    private OnBufferingUpdateListener mSelfBufferingUpdateListerer;
    private OnCompletionListener mSelfOnCompletionListener;
    private OnErrorListener mSelfOnErrorListener;
    private OnInfoListener mSelfOnInfoListener;
    private OnPreparedListener mSelfOnPreparedListener;
    private OnSeekCompleteListener mSelfOnSeekCompleteListener;
    private OnVideoSizeChangedListener mSelfOnVideoSizeChangedListener;

    public MediaPlayerTracker(VideoPlayerEventTracker tracker) {
        super();
        if (null == tracker) {
            throw new IllegalArgumentException("tracker is null.");
        }
        mTracker = tracker;
        registerSuperListeners();
    }

    private void registerSuperListeners() {
        super.setOnBufferingUpdateListener(this);
        super.setOnCompletionListener(this);
        super.setOnErrorListener(this);
        super.setOnInfoListener(this);
        super.setOnPreparedListener(this);
        super.setOnSeekCompleteListener(this);
        super.setOnVideoSizeChangedListener(this);
    }

    @Override
    public void pause() throws IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_PAUSE, null);

        super.pause();
    }

    @Override
    public void start() throws IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_START, null);

        super.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_STOP, null);

        super.stop();
    }

    @Override
    public void seekTo(int msec) throws IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_SEEK_REQUEST, new int[] {
                msec
        });

        super.seekTo(msec);
    }

    @Override
    public void prepare() throws IOException, IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_PREPARE, null);

        super.prepare();
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_PREPARE_SYNC, null);

        super.prepareAsync();
    }

    @Override
    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        mSelfBufferingUpdateListerer = listener;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        mSelfOnCompletionListener = listener;
    }

    @Override
    public void setOnErrorListener(OnErrorListener listener) {
        mSelfOnErrorListener = listener;
    }

    @Override
    public void setOnInfoListener(OnInfoListener listener) {
        mSelfOnInfoListener = listener;
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {
        mSelfOnPreparedListener = listener;
    }

    @Override
    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        mSelfOnSeekCompleteListener = listener;
    }

    @Override
    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
        mSelfOnVideoSizeChangedListener = listener;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_VIDEO_SIZE_CHANGED, new int[] {
                width, height
        });
        if (null != mSelfOnVideoSizeChangedListener) {
            mSelfOnVideoSizeChangedListener.onVideoSizeChanged(mp, width, height);
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_SEEK_COMPLETE, null);
        if (mSelfOnSeekCompleteListener != null) {
            mSelfOnSeekCompleteListener.onSeekComplete(mp);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_PREPARED, null);
        if (null != mSelfOnPreparedListener) {
            mSelfOnPreparedListener.onPrepared(mp);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_COMPLETE, null);
        if (null != mSelfOnCompletionListener) {
            mSelfOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (0 == percent // FIXME
        ) {
            mTracker.track(VideoPlayerEventTracker.EVENT_ON_BUFFERING_START, null);
        } else if (100 == percent) {
            mTracker.track(VideoPlayerEventTracker.EVENT_ON_BUFFERING_COMPLETE, null);
        } else {
            mTracker.track(VideoPlayerEventTracker.EVENT_ON_BUFFERING_PROGRESS, new int[]{percent});
        }
        
        if (null != mSelfBufferingUpdateListerer) {
            mSelfBufferingUpdateListerer.onBufferingUpdate(mp, percent);
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_INFO, new int[] {
                what, extra
        });
        if (null != mSelfOnInfoListener) {
            return mSelfOnInfoListener.onInfo(mp, what, extra);
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mTracker.track(VideoPlayerEventTracker.EVENT_ON_ERR, new int[] {
                what, extra
        });
        if (null != mSelfOnErrorListener) {
            return mSelfOnErrorListener.onError(mp, what, extra);
        }
        return false;
    }
    
    static String buffer2Desc(Object eventParam){
       if (null == eventParam) {
           return null;
       }
       
       String desc = "unparsed buffer progress.";
       desc = ((int[]) eventParam)[0] + "";
       return desc;
    }

    static String info2Desc(Object eventParam) {
        String desc = "unparsed info.";
        if (null == eventParam) {
            return null;
        }
        int info = ((int[]) eventParam)[0];
        switch (info) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                desc = "MEDIA_INFO_BAD_INTERLEAVING";
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
             desc = "MEDIA_INFO_METADATA_UPDATE";
             break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                desc = "MEDIA_INFO_NOT_SEEKABLE";
                break;
            case MediaPlayer.MEDIA_INFO_UNKNOWN:
                desc = "MEDIA_INFO_UNKNOWN";
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                desc = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                break;
            default:
                // empty
        }

        return desc;
    }
    
    static String seek2Desc(Object eventParam){
        String desc = "unparsed seek into.";
        if (null == eventParam){
            return null;
        }
        desc = ((int[])eventParam)[0] + "";
        
        return desc;
    }

    static String error2Desc(Object eventParam) {
        String desc = "unparsed error.";
        if (null == eventParam) {
            return null;
        }
        int error = ((int[]) eventParam )[0];
        switch (error) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                desc = "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK";
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                desc = "MEDIA_ERROR_SERVER_DIED";
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                desc = "MEDIA_ERROR_UNKNOWN";
                break;
            default:
                // empty
        }
        return desc;
    }
    
    public static class VideoPlayerEventTracker {
        public static final int EVENT_ON_ERR = -10;
        public static final int EVENT_ON_PREPARED = 0;
        public static final int EVENT_ON_START = 10;
        public static final int EVENT_ON_BUFFERING_START = 20;
        public static final int EVENT_ON_BUFFERING_PROGRESS = 21;
        public static final int EVENT_ON_BUFFERING_COMPLETE = 30;
        public static final int EVENT_ON_PAUSE = 40;
        public static final int EVENT_ON_SEEK_REQUEST = 50;
        public static final int EVENT_ON_SEEK_COMPLETE = 60;
        public static final int EVENT_ON_STOP = 70;
        public static final int EVENT_ON_INFO = 80;
        public static final int EVENT_ON_COMPLETE = 90;
        public static final int EVENT_ON_PREPARE_SYNC = 100;
        public static final int EVENT_ON_PREPARE = 110;
        public static final int EVENT_ON_VIDEO_SIZE_CHANGED = 120;

        public static final String TAG = "tracker";

        public static final boolean LOG = true;

        public void track(int eventId, Object eventParam) {
            long UTCTime = System.currentTimeMillis();
            if (LOG) {
                Log.d(TAG, "enentId: " + eventId + "\tdesc: " + event2Desc(eventId) + "\tUTCTime: "
                        + UTCTime + "\teventParam: "
                        + genParamDesc(eventId, eventParam));
            }
        }

        private String genParamDesc(int eventId, Object eventParam) {
            // TODO Auto-generated method stub
            String desc = ( (eventParam == null) ? null : "unparsed eventParam.");
            switch (eventId) {
                case EVENT_ON_ERR:
                    desc = MediaPlayerTracker.error2Desc(eventParam);
                    break;
                case EVENT_ON_INFO:
                    desc =  MediaPlayerTracker.info2Desc(eventParam);
                    break;
                case EVENT_ON_SEEK_REQUEST:
                    desc = MediaPlayerTracker.seek2Desc(eventParam);
                    break;
                case EVENT_ON_BUFFERING_PROGRESS:
                    desc = MediaPlayerTracker.buffer2Desc(eventParam);
                    break;
                default:
                    // empty
            }
            
            return desc;
        }

        String event2Desc(int eventId) {
            String desc = "unknow action id.";
            switch (eventId) {
                case EVENT_ON_ERR:
                    desc = "EVENT_ON_ERR";
                    break;
                case EVENT_ON_PREPARED:
                    desc = "EVENT_ON_PREPARED";
                    break;
                case EVENT_ON_START:
                    desc = "EVENT_ON_START";
                    break;
                case EVENT_ON_BUFFERING_START:
                    desc = "EVENT_ON_BUFFERING_START";
                    break;
                case EVENT_ON_BUFFERING_PROGRESS:
                    desc = "EVENT_ONBUFFERING_PROGRESS";
                    break;
                case EVENT_ON_BUFFERING_COMPLETE:
                    desc = "EVENT_ON_BUFFERING_COMPLETE";
                    break;
                case EVENT_ON_PAUSE:
                    desc = "EVENT_ON_PAUSE";
                    break;
                case EVENT_ON_SEEK_REQUEST:
                    desc = "EVENT_ON_SEEK_REQUEST";
                    break;
                case EVENT_ON_SEEK_COMPLETE:
                    desc = "EVENT_ON_SEEK_COMPLETE";
                    break;
                case EVENT_ON_STOP:
                    desc = "EVENT_ON_STOP";
                    break;
                case EVENT_ON_INFO:
                    desc = "EVENT_ON_INFO";
                    break;
                case EVENT_ON_COMPLETE:
                    desc = "EVENT_ON_COMPLETE";
                    break;
                case EVENT_ON_PREPARE:
                    desc = "EVENT_ON_PREPARE";
                    break;
                case EVENT_ON_PREPARE_SYNC:
                    desc = "EVENT_ON_PREPARE_SYNC";
                    break;
                case EVENT_ON_VIDEO_SIZE_CHANGED:
                    desc = "EVENT_ON_VIDEO_SIZE_CHANGED";
                    break;
                default:
                    // empty
            }
            return desc;
        }

        class ActionBean {
            int mActionId;
            long mUTCTime;
            Object mActionParam;

            public ActionBean(int actionId, long UTCTime, Object param) {
                mActionId = actionId;
                mUTCTime = UTCTime;
                mActionParam = param;
            }
        }
    }
}


