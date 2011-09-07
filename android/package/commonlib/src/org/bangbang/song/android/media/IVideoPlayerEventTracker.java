
package org.bangbang.song.android.media;

public interface IVideoPlayerEventTracker {
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
    public static final int EVENT_ON_GET_DURATION = 130;
    public static final int EVENT_ON_PLAY_GROGRESS = 140;

    public abstract void track(int eventId, Object eventParam);

}
