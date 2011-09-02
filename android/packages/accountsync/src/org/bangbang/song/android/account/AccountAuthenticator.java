package org.bangbang.song.android.account;

import org.bangbang.song.andorid.common.debug.Log;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

	private final static String ACCOUNT_NAME = "bangbang.song";
	private final static String ACCOUNT_TYPE = "ooxx";
	private final static String ACTION_ADD_ACCOUNT = "org.bangbang.song.android.ACTION_ADD_ACCOUNT";
	private final static String TAG = AccountAuthenticator.class.getSimpleName();
	
	private AccountManager mAccMgr;

	public AccountAuthenticator(Context context) {
		super(context);
		mAccMgr = AccountManager.get(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response,
			String accountType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,
			String accountType, String authTokenType,
			String[] requiredFeatures, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
//		b.putParcelable(AccountManager.KEY_INTENT, intent);
		Log.d(TAG , "addAccount(). response: " + response + "\taccountType: " + accountType + 
					"\tauthTokenType: " + authTokenType + "\trequiredFeatures: " + requiredFeatures + 
					"\toptions: " + options);
		Intent intent = new Intent();
		intent.setAction(ACTION_ADD_ACCOUNT);
		Bundle b = new Bundle();		
		
		b.putString(AccountManager.KEY_ACCOUNT_NAME, ACCOUNT_NAME);
		b.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
		
//		b.putString(AccountManager.KEY_ERROR_CODE, "" + 0);
//		b.putString(AccountManager.KEY_ERROR_MESSAGE, "error add account.");
		Account acc = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
		mAccMgr.addAccountExplicitly(acc, "123456", null);
		return b;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
			Account account, Bundle options) {
		// TODO Auto-generated method stub
		Log.d(TAG, "confirmCredentials(). response: " + response + "\taccount: " + account + 
					"\toptions: " + options);
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.d(TAG, "getAuthToken(). response: " + response + 
					"\taccount: " + account + "\tauthTokeyType" + authTokenType + 
					"\toptions: " + options);
		return null;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		// TODO Auto-generated method stub
		Log.d(TAG, "getAuthTokenLabel(). autoTokenType: " + authTokenType);
		return null;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options) {
		// TODO Auto-generated method stub
		Log.d(TAG, "updateCredentials(). response: " + response + "\taccount: " + account + 
					"\tauthTokenType: " + authTokenType + "\toptions: " + options);
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response,
			Account account, String[] features) throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.d(TAG, "hasFeature(). response: " + response + "\taccount: " + account + 
					"\tfeatures: " + features);
		return null;
	}
}
