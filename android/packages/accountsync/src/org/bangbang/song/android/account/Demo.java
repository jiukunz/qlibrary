package org.bangbang.song.android.account;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;

public class Demo extends AccountAuthenticatorActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}