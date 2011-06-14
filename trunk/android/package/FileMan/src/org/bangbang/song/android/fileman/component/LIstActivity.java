
package org.bangbang.song.android.fileman.component;

import org.apache.http.ReasonPhraseCatalog;
import org.bangbang.song.android.fileman.activity.ReportCrashActivity;

import android.app.ListActivity;
import android.app.PendingIntent.OnFinished;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.os.Bundle;

public class LIstActivity extends ListActivity {
    protected boolean hasResumed = false;
    protected boolean hasPaused = false;
    protected ComponentController.IComponentCallback mComponentCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mComponentCallback = new ListActivityComponentCallback();
        ComponentController.getInstance().regComponentCallback(mComponentCallback);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        hasResumed = true;
        hasPaused = false;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        hasPaused = true;
        hasResumed = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ComponentController.getInstance().unRegComponentCallback(mComponentCallback);
    }

    public void onUserFinish() {
        // TODO Auto-generated method stub
        finish();
    }

    public boolean onUnCaughtException(Thread thread, Throwable throwable) {
        // TODO Auto-generated method stub
        startActivity(new Intent(this, ReportCrashActivity.class));
        return false;
    }

    class ListActivityComponentCallback implements ComponentController.IComponentCallback {

        @Override
        public void onUserFinish() {
            // TODO Auto-generated method stub
            LIstActivity.this.finish();
        }

        @Override
        public boolean onUnCaughtException(Thread thread, Throwable throwable) {
            // TODO Auto-generated method stub
            return LIstActivity.this.onUnCaughtException(thread, throwable);
        }

    }
}
