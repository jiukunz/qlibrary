
package org.bangbang.song.android.fileman.activity.adapter;

import android.net.Uri;

public interface INavigateHistory {
    public boolean canUp();

    public void up();

    public boolean canForward();

    public boolean canBackward();

    public void forward();

    public void backward();

    public void newHistory(Uri uri);

    public Uri getCurrentUri();

}
