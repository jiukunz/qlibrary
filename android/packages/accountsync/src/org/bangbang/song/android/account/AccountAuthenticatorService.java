package org.bangbang.song.android.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service {
	
	private AccountAuthenticator mAuthenticator;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mAuthenticator = new AccountAuthenticator(getApplicationContext());
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mAuthenticator.getIBinder();
	}

}
