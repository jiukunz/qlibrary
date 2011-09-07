
package org.bangbang.song.android.media;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * track event related to {@link android.media.MediaPlayer MediaPlayer} such as play/pause/start/buffering and so on.
 * 
 * @author bangbang.song@gmail.com
 *
 */
public class MediaPlayerTracker extends android.media.MediaPlayer
        implements
        android.media.MediaPlayer.OnErrorListener,
        android.media.MediaPlayer.OnInfoListener,
        android.media.MediaPlayer.OnBufferingUpdateListener,
        android.media.MediaPlayer.OnCompletionListener,
        android.media.MediaPlayer.OnPreparedListener,
        android.media.MediaPlayer.OnSeekCompleteListener,
        android.media.MediaPlayer.OnVideoSizeChangedListener {

    private IVideoPlayerEventTracker mTracker;
    private int mDuration;
    private int mCurrentPosition;

    private OnBufferingUpdateListener mSelfBufferingUpdateListerer;
    private OnCompletionListener mSelfOnCompletionListener;
    private OnErrorListener mSelfOnErrorListener;
    private OnInfoListener mSelfOnInfoListener;
    private OnPreparedListener mSelfOnPreparedListener;
    private OnSeekCompleteListener mSelfOnSeekCompleteListener;
    private OnVideoSizeChangedListener mSelfOnVideoSizeChangedListener;

    public MediaPlayerTracker(IVideoPlayerEventTracker policy) {
        super();
        
        if (null == policy) {
            throw new IllegalArgumentException("tracker is null.");
        }
        
        mTracker = policy;
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
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_PAUSE, null);

        super.pause();
    }

    @Override
    public void start() throws IllegalStateException {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_START, null);

        super.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_STOP, null);

        super.stop();
    }

    @Override
    public void seekTo(int msec) throws IllegalStateException {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_SEEK_REQUEST, new int[] {
                msec
        });

        super.seekTo(msec);
    }

    @Override
    public void prepare() throws IOException, IllegalStateException {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_PREPARE, null);

        super.prepare();
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_PREPARE_SYNC, null);

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
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_VIDEO_SIZE_CHANGED, new int[] {
                width, height
        });
        if (null != mSelfOnVideoSizeChangedListener) {
            mSelfOnVideoSizeChangedListener.onVideoSizeChanged(mp, width, height);
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_SEEK_COMPLETE, null);
        if (mSelfOnSeekCompleteListener != null) {
            mSelfOnSeekCompleteListener.onSeekComplete(mp);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mDuration = getDuration();
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_PREPARED, null);
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_GET_DURATION, new int[] {
                mDuration
        });
        if (null != mSelfOnPreparedListener) {
            mSelfOnPreparedListener.onPrepared(mp);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_COMPLETE, null);
        if (null != mSelfOnCompletionListener) {
            mSelfOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (0 == percent // FIXME
        ) {
            mTracker.track(IVideoPlayerEventTracker.EVENT_ON_BUFFERING_START, null);
        } else if (100 == percent) {
            mTracker.track(IVideoPlayerEventTracker.EVENT_ON_BUFFERING_COMPLETE, null);
        } else {
            mTracker.track(IVideoPlayerEventTracker.EVENT_ON_BUFFERING_PROGRESS, new int[] {
                    percent
            });
            mTracker.track(IVideoPlayerEventTracker.EVENT_ON_PLAY_GROGRESS, new int[] {
                    playProgress()
            });
        }

        if (null != mSelfBufferingUpdateListerer) {
            mSelfBufferingUpdateListerer.onBufferingUpdate(mp, percent);
        }
    }

    private int playProgress() {
        // TODO Auto-generated method stub
        return (int) (((float) getCurrentPosition()) / mDuration * 100 % 100);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_INFO, new int[] {
                what, extra
        });
        if (null != mSelfOnInfoListener) {
            return mSelfOnInfoListener.onInfo(mp, what, extra);
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mTracker.track(IVideoPlayerEventTracker.EVENT_ON_ERR, new int[] {
                what, extra
        });
        if (null != mSelfOnErrorListener) {
            return mSelfOnErrorListener.onError(mp, what, extra);
        }
        return false;
    }

    /**
     * @param eventParam
     * @return progress of playing.
     * 
     * @see {@link android.media.MediaPlayer#getCurrentPosition()}
     */
    public static String playProgress2Desc(Object eventParam) {
        if (null == eventParam) {
            return null;
        }

        String desc = "unparsed playProgress progress.";
        desc = ((int[]) eventParam)[0] + "";
        return "playprogress: " + desc;
    }

    /**
     * @param eventParam
     * @return duration
     * 
     * @see android.media.MediaPlayer#getDuration()
     */
    static String duration2Desc(Object eventParam) {
        if (null == eventParam) {
            return null;
        }

        String desc = "unparsed duration progress.";
        desc = ((int[]) eventParam)[0] + "";
        return "duration: " + desc;
    }

    /**
     * @param eventParam
     * @return
     * 
     * @see android.media.MediaPlayer.OnBufferingUpdateListener
     */
    static String buffer2Desc(Object eventParam) {
        if (null == eventParam) {
            return null;
        }

        String desc = "unparsed buffer progress.";
        desc = ((int[]) eventParam)[0] + "";
        return "buffer: " + desc;
    }

    /**
     * @param eventParam
     * @return
     * 
     * @see android.media.MediaPlayer.OnInfoListener
     */
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

        return "info: " + desc + "\t extra: " + ((int[]) eventParam)[1];
    }

    /**
     * @param eventParam
     * @return
     * 
     * @see android.media.MediaPlayer#seekTo(int)
     */
    static String seek2Desc(Object eventParam) {
        String desc = "unparsed seek into.";
        if (null == eventParam) {
            return null;
        }
        desc = "seek: " + ((int[]) eventParam)[0];

        return desc;
    }

    /**
     * @param eventParam
     * @return
     * 
     * @see android.media.MediaPlayer.OnErrorListener
     */
    static String error2Desc(Object eventParam) {
        String desc = "unparsed error.";
        if (null == eventParam) {
            return null;
        }
        int error = ((int[]) eventParam)[0];
        switch (error) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                desc = " MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK";
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
        
        return "error: " + desc + "\textra: " + ((int[]) eventParam)[1];
    }

    /**
     * just log event only.
     * 
     * 
     * @author bangbang.song@gmail.com
     *
     */
    public static class DefaultLogEventTracker implements IVideoPlayerEventTracker {
        public static final String TAG = "tracker";

        public static final boolean LOG = true;
        private boolean mHasComplete = false;

        /* (non-Javadoc)
         * @see org.bangbang.song.android.media.IVideoPlayerEventTracker#track(int, java.lang.Object)
         */
        @Override
        public void track(int eventId, Object eventParam) {
            long UTCTime = System.currentTimeMillis();

            if (LOG
                    && (EVENT_ON_BUFFERING_COMPLETE != eventId || (EVENT_ON_BUFFERING_COMPLETE == eventId && !mHasComplete))) {
                Log.d(TAG, "enentId: " + eventId + "\tdesc: " + event2Desc(eventId) + "\tUTCTime: "
                        + UTCTime + "\teventParam: "
                        + genParamDesc(eventId, eventParam));
            }

            if (!mHasComplete && EVENT_ON_BUFFERING_COMPLETE == eventId) {
                mHasComplete = true;
            }
        }

        private String genParamDesc(int eventId, Object eventParam) {
            // TODO Auto-generated method stub
            String desc = ((eventParam == null) ? null : "unparsed eventParam.");
            switch (eventId) {
                case EVENT_ON_ERR:
                    desc = MediaPlayerTracker.error2Desc(eventParam);
                    break;
                case EVENT_ON_INFO:
                    desc = MediaPlayerTracker.info2Desc(eventParam);
                    break;
                case EVENT_ON_SEEK_REQUEST:
                    desc = MediaPlayerTracker.seek2Desc(eventParam);
                    break;
                case EVENT_ON_BUFFERING_PROGRESS:
                    desc = MediaPlayerTracker.buffer2Desc(eventParam);
                    break;
                case EVENT_ON_GET_DURATION:
                    desc = MediaPlayerTracker.duration2Desc(eventParam);
                    break;
                case EVENT_ON_PLAY_GROGRESS:
                    desc = MediaPlayerTracker.playProgress2Desc(eventParam);
                    break;
                default:
                    // empty
            }

            return desc;
        }

        String event2Desc(int eventId) {
            String desc = "unpased event id.";
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
                    desc = "EVENT_ON_BUFFERING_PROGRESS";
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
                case EVENT_ON_GET_DURATION:
                    desc = "EVENT_ON_GET_DURATION";
                    break;
                case EVENT_ON_PLAY_GROGRESS:
                    desc = "EVENT_ON_PLAY_GROGRESS";
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
