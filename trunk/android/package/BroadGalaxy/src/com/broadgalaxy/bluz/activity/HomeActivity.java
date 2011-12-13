
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.BluetoothChatService;
import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.LocalService;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.component.Navigation;
import com.broadgalaxy.bluz.component.Navigation.OnNavClickListener;
import com.broadgalaxy.bluz.protocol.IccRequest;
import com.broadgalaxy.bluz.protocol.IccResponse;
import com.broadgalaxy.bluz.protocol.LocationRequest;
import com.broadgalaxy.bluz.protocol.LocationResponse;
import com.broadgalaxy.bluz.protocol.MessageRequest;
import com.broadgalaxy.bluz.protocol.Pack;
import com.broadgalaxy.bluz.protocol.Response;
import com.broadgalaxy.bluz.protocol.SigRequest;
import com.broadgalaxy.bluz.protocol.SigResponse;
import com.broadgalaxy.util.Log;
import com.broadgalaxy.util.MiscUtil;

public class HomeActivity extends BluzActivity {

    private static final boolean D = true;

    private static final String TAG = HomeActivity.class.getSimpleName();

    private Navigation mNav;

    private Button mConnectBtn;

    private Button mLocateBtn;
    private Button mSigBtn;

    private String mUserId;

    private TextView mTitle;

    private TextView mLocationTextV;

    private String mConnectedDeviceName;

    private TextView mIccTextV;

    private int mUserAddress;

    private Button mMessageBtn;

    private TextView mSigTextV;

    private static int REQUEST_CODE_USER_ID = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.navigation);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.broadgalaxy);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        mLocationTextV = (TextView) findViewById(R.id.location);
        mLocationTextV.setText(formatLocation(0, 0, 0, 0, 0));
        mIccTextV = (TextView) findViewById(R.id.user_id);
        mSigTextV = (TextView)findViewById(R.id.sig_info);
        mNav = (Navigation) findViewById(R.id.navigation);
        mNav.setEnabled(true);// FIXME
        mNav.setOnNavListener(new OnNavClickListener() {

            @Override
            public void onNavClick(int navId) {
                if (OnNavClickListener.NAV_NEW == navId) {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, ChatActivity.class);
                    HomeActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, BoxActivity.class);
                    intent.putExtra(BoxActivity.EXTRA_BOX_TYPE, navId);
                    HomeActivity.this.startActivity(intent);
                }
            }
        });
        mLocateBtn = (Button) findViewById(R.id.locate);
        mLocateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                tryLocate();

                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, LocateActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
        mConnectBtn = (Button) findViewById(R.id.connect);
        mConnectBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tryConnect();
            }
        });
        mSigBtn = (Button) findViewById(R.id.sig);
        mSigBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                trySig();
            }
        });
        mMessageBtn = (Button) findViewById(R.id.message);
        mMessageBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, ChatActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            case R.id.locate:
                tryLocate();
                return true;
            case R.id.power:
                trySig();
                return true;
        }

        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) {
            Log.d(TAG, "onActivityResult " + resultCode);
        }
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    if (mbound) {
                        // mService.start();
                        mService.connect(device);
                    } else {
                        Log.e(TAG, "service is NOT bounded.");
                    }
                }
                break;
        }
    }

    @Override
    protected void onServiceConnected(LocalService mService) {
        super.onServiceConnected(mService);

        // tryConnect();
    }

    protected void tryLocate() {
        // Check that we're actually connected before trying anything
        if (/* mChatService. */getState() != IChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        int fromAddress = Integer.valueOf("000238");
        fromAddress = 568; // ox 00 02 38
        int toAddress = 3;
        Pack m = new LocationRequest(fromAddress, (byte) 0);
        m = new SigRequest(fromAddress, (byte) 0);
//        write(null, m.getByte());
        m = new MessageRequest(fromAddress, fromAddress, "kk");
//        write(null, m.getByte());
        m = new LocationRequest(fromAddress, (byte) 0);
//        write(null, m.getByte());
//        m = new IccRequest();
        // Log.e(TAG, "sig: " + m.toHexString());
        write(null, m.getByte());
    }
    

    private void tryIcc() {
        // Check that we're actually connected before trying anything
        if (/* mChatService. */getState() != IChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        Pack m = null;
        m = new IccRequest();
        // Log.e(TAG, "sig: " + m.toHexString());
        write(null, m.getByte());        
    }

    protected void trySig() {
        // Check that we're actually connected before trying anything
        if (/* mChatService. */getState() != IChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        int fromAddress = 0;
        fromAddress = mUserAddress; // ox 00 02 38
        Pack m = new LocationRequest(fromAddress, (byte) 0);
        m = new SigRequest(fromAddress, (byte) 0);
        // m = new MessageRequest(fromAddress, fromAddress, "kk");
        // m = new LocationRequest(fromAddress, (byte)0);
        // Log.e(TAG, "sig: " + m.toHexString());
        write(null, m.getByte());

    }

    protected void tryConnect() {
        // Launch the DeviceListActivity to see devices and do scan
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    /**
     * @param msg
     */
    protected void handleDeviceNameMsg(Message msg) {
        // save the connected device's name
        mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
    }

    @Override
    protected void handleStateChangeMsg(int state) {
        if (D) {
            Log.i(TAG,
                    "MESSAGE_STATE_CHANGE: " + state + " "
                            + BluetoothChatService.toStateDesc(state));
        }
        // mSendButton.setEnabled(IChatService.STATE_CONNECTED == state);
        switch (state) {
            case IChatService.STATE_CONNECTED:
                mTitle.setText(R.string.title_connected);

                mConnectedDeviceName = mService.getConnectDName();
                mTitle.append(mConnectedDeviceName);
                // mConversationArrayAdapter.clear();
                onConnected();
                break;
            case IChatService.STATE_CONNECTING:
                mTitle.setText(R.string.title_connecting);
                break;
            case IChatService.STATE_LISTEN:
            case IChatService.STATE_NONE:
                mTitle.setText(R.string.title_not_connected);

              mLocateBtn.setEnabled(false);
              mSigBtn.setEnabled(false);
              mMessageBtn.setEnabled(false);
              break;
        }
    }

    protected void handleReadmsg(Response response) {
        if (response instanceof SigResponse) {
            SigResponse s = (SigResponse) response;
            
            CharSequence level = null;
            
            int sig = s.getSig1();
            s.getPayload();
            
            level = sig2Level(sig);
            
            mSigTextV.setText(level + "\traw data: " + Pack.toHexString(s.getByte()));
//            Log.d(TAG, "sig: " + s);
        } else if (response instanceof LocationResponse) {
            LocationResponse l = (LocationResponse) response;
            String location = "";            
            location = formatLocation(l.getLocationT(), l.getLocationL(), l.getLocationB(), l.getLocationH(), l.getLocationX());
            mLocationTextV.setText(location);
        } else if (response instanceof IccResponse) {
            IccResponse icc = (IccResponse) response;
            
            mUserAddress = icc.getUserAddress();
            Editor e = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).edit();
            e.putInt(Application.PREF_USER_ID, mUserAddress);
            e.commit();
            
            mIccTextV.setText("用户ID： " + mUserAddress);
            
            // enable all
            mSigBtn.setEnabled(true);
            mLocateBtn.setEnabled(true);
            mMessageBtn.setEnabled(true);
            
            trySig();
        }
    }

    private CharSequence sig2Level(int sig) {
        String level = "unknow level.";
        
        if (sig < -158) {
            
        }
        return "功率： " + level;
    }

    String formatLocation(int t, int l, int b, int h, int x) {
        return "T: " + t +
                "  L: " + l +
                "  B: " + b +
                "  H: " + h +
                "  X: " + x;
    }

    private void onLoacted(Object locateInfo) {

    }

    private void onConnected() {
//        tryLocate();
        tryIcc();
    }


    private void onDisConnected() {
        mNav.setEnabled(false);
        mLocateBtn.setEnabled(false);
        mSigBtn.setEnabled(false);
        mConnectBtn.setVisibility(View.VISIBLE);
    }

}
