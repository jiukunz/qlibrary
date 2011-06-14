
package org.bangbang.song.android.fileman.component;

import android.app.Application;

public class APplication extends Application {
    protected ComponentController.IComponentCallback mComponentCallback;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mComponentCallback = new ApplicationComponentCallback();
        ComponentController.getInstance().regComponentCallback(mComponentCallback);
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        ComponentController.getInstance().regComponentCallback(mComponentCallback);
    }

    public void onUserFinish() {
        // TODO Auto-generated method stub
        onTerminate();
    }

    public boolean onUnCaughtException(Thread thread, Throwable throwable) {
        return false;
    }

    class ApplicationComponentCallback implements ComponentController.IComponentCallback {

        @Override
        public void onUserFinish() {
            // TODO Auto-generated method stub
            APplication.this.onTerminate();
        }

        @Override
        public boolean onUnCaughtException(Thread thread, Throwable throwable) {
            // TODO Auto-generated method stub
            return APplication.this.onUnCaughtException(thread, throwable);
        }

    }
}
