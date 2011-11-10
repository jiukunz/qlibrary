
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.component.Navigation;
import com.broadgalaxy.bluz.component.Navigation.OnNavClickListener;

public class HomeActivity extends Activity {

    private Navigation mNav;
    private Button mConnectBtn;
    private Button mLocateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.navigation);
        mNav = (Navigation) findViewById(R.id.navigation);
        mNav.setOnNavListener(new OnNavClickListener(){

            @Override
            public void onNavClick(int navId) {
                if (OnNavClickListener.NAV_NEW == navId) {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this.getApplicationContext(),
                                    ChatActivity.class);
                    HomeActivity.this.startActivity(intent);
                }
            }});
        mLocateBtn = (Button) findViewById(R.id.locate);
        mLocateBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                tryLocate();
            }});
        mConnectBtn = (Button) findViewById(R.id.connect);
        mConnectBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                tryConnect();
            }});
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
