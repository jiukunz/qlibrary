
package com.broadgalaxy.bluz.activity;

import com.broadgalaxy.bluz.BluetoothChatService;
import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.LocalService;
import com.broadgalaxy.bluz.LocalService.OnMsgCallBack;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.util.Log;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BluzActivity extends Activity {
    private static final String TAG = BluzActivity.class.getSimpleName();
    private boolean D = true;

    public static final String DEVICE_NAME = "device_name";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_DISCOVERABLE = 3;
    private BluetoothAdapter mBluetoothAdapter = null;
    // private IChatService mChatService = null;
    private ServiceConnection mconn;
    private boolean mbound;
    private LocalService mService;
    private OnMsgCallBack mCallback;
    private boolean mBluzEnable;

    protected void handleDeviceNameMsg(Message msg) {
        // TODO Auto-generated method stub

    }

    protected void handleWriteMsg(Message msg) {
        // TODO Auto-generated method stub

    }

    protected void handlReadmsg(Message msg) {
        // TODO Auto-generated method stub

    }

    protected void handlStateChangeMsg(int state) {
        // TODO Auto-generated method stub

    }

    protected void handToastmsg(Message msg) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mCallback = new OnMsgCallBack() {

            @Override
            public void handleWriteMsg(Message msg) {
                BluzActivity.this.handleWriteMsg(msg);
            }

            @Override
            public void handleDeviceNameMsg(Message msg) {
                BluzActivity.this.handleDeviceNameMsg(msg);
            }

            @Override
            public void handlStateChangeMsg(int state) {
                BluzActivity.this.handlStateChangeMsg(state);
            }

            @Override
            public void handlReadmsg(Message msg) {
                BluzActivity.this.handlReadmsg(msg);
            }

            @Override
            public void handToastmsg(Message msg) {
                BluzActivity.this.handToastmsg(msg);
            }
        };
        mconn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mbound = false;
                if (null != mService) {
                    mService.unRegisterOnMsgCallback(mCallback);
                }
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mbound = true;
                mService = ((LocalService.LocalBinder) service).getChatService();
                mService.registerOnMsgCallback(mCallback);
                mService.start();
            }
        };
        Intent service = new Intent();
        service.setClass(this, LocalService.class);
        bindService(service, mconn, BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mbound && mService != null) {
            mService.stop();
        }
        if (D)
            Log.e(TAG, "--- ON DESTROY ---");

        unbindService(mconn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
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
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
        }
        return false;
    }

    void ensureDiscoverable() {
        if (D)
            Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (D)
            Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (D)
            Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            mBluzEnable = true;
            // if (mChatService == null)
            // onBluetoothEnabled();
            //
            // Initialize the BluetoothChatService to perform bluetooth
            // connections
            // mChatService = new BluetoothChatService(this, mHandler);
        }
    }

    protected void onBluetoothEnabled() {
        // TODO Auto-generated method stub
        // Initialize the BluetoothChatService to perform bluetooth connections
        // mChatService = new BluetoothChatService(this, mHandler);
        mBluzEnable = true;

    }

    protected void write(byte[] msg) {

        mService.write(msg);
    }

    protected int getState() {
        return mService.getState();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D)
            Log.d(TAG, "onActivityResult " + resultCode);
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
                        mService.connect(device);
                    } else {
                        Log.e(TAG, "service is NOT bounded.");
                    }
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    onBluetoothEnabled();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
            case REQUEST_DISCOVERABLE:
                if (RESULT_OK == resultCode) {
                    Log.e(TAG, "discoverable ok");
                } else {
                    Log.e(TAG, "discoverable FALSE");
                }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (D)
            Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D)
            Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        if (mService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mService.getState() == IChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mService.start();
            }
        }
    }

}
