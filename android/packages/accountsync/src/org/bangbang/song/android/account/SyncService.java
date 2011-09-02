package org.bangbang.song.android.account;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

public class SyncService extends Service {
   

    private AbstractThreadedSyncAdapter mSyncAdapter;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        mSyncAdapter = new DummySyncAdpter(this, true);
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mSyncAdapter.getSyncAdapterBinder();
    }
    
    class DummySyncAdpter extends AbstractThreadedSyncAdapter {

        public DummySyncAdpter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority,
                ContentProviderClient provider, SyncResult syncResult) {
            // TODO Auto-generated method stub
            
        }
        
    }
}
