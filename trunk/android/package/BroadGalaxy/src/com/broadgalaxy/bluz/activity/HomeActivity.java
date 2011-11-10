
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.component.Navigation;
import com.broadgalaxy.bluz.component.Navigation.OnNavClickListener;

public class HomeActivity extends BluzActivity {

    private Navigation mNav;
    private Button mConnectBtn;
    private Button mLocateBtn;
    private String mUserId;
    private static int REQUEST_CODE_USER_ID = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation);
        mNav = (Navigation) findViewById(R.id.navigation);
        mNav.setOnNavListener(new OnNavClickListener() {

            @Override
            public void onNavClick(int navId) {
                if (OnNavClickListener.NAV_NEW == navId) {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this,
                                    ChatActivity.class);
                    HomeActivity.this.startActivity(intent);
                }
            }
        });
        mLocateBtn = (Button) findViewById(R.id.locate);
        mLocateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tryLocate();
            }
        });
        mConnectBtn = (Button) findViewById(R.id.connect);
        mConnectBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tryConnect();
            }
        });
        
//        assureUserId();
    }

    /**
     * 
     */
    protected void assureUserId() {
        mUserId = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).getString(
                Application.PREF_USER_ID, "");
        if ("".equals(mUserId)){
            Intent intent = new Intent();
            intent.setClass(this, UserIdActivity.class);
            startActivityForResult(intent, REQUEST_CODE_USER_ID);
        } else {
            
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
//        if (RESULT_OK == resultCode) {
//            
//        } else {
//            finish();
//        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        return false ;// super.onCreateOptionsMenu(menu);
    }

    protected void tryLocate() {
        // TODO Auto-generated method stub

    }

    protected void tryConnect() {
        // TODO Auto-generated method stub

    }

    private void onLoacted(Object locateInfo) {

    }

    private void onConnected() {
        mNav.setEnabled(true);
        mLocateBtn.setEnabled(true);
        mConnectBtn.setVisibility(View.GONE);
    }

    private void onDisConnected() {
        mNav.setEnabled(false);
        mLocateBtn.setEnabled(false);
        mConnectBtn.setVisibility(View.VISIBLE);
    }

}
