
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.LocalService;
import com.broadgalaxy.bluz.LocalService.OnMsgCallBack;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.core.Response;
import com.broadgalaxy.util.Log;

public class BluzActivity extends Activity {
    private static final String TAG = BluzActivity.class.getSimpleName();
    private boolean D = true;

    public static final String DEVICE_NAME = "device_name";

    public static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_DISCOVERABLE = 3;
    protected BluetoothAdapter mBluetoothAdapter = null;
    // private IChatService mChatService = null;
    private ServiceConnection mconn;
    protected boolean mbound;
    protected LocalService mService;
    private OnMsgCallBack mCallback;
    private boolean mBluzEnable;

    protected void handleDeviceNameMsg(Message msg) {
        // TODO Auto-generated method stub
    }

    protected void handleWriteMsg(Message msg) {
        // TODO Auto-generated method stub        

    }

    protected void handlReadmsg(Response response) {
        // TODO Auto-generated method stub
    }

    private void handleReadmsg(int len, byte[] msgBytes) {
      
    }

    protected void handleStateChangeMsg(int state) {
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

        bindServer();
    }

    /**
     * 
     */
    protected void bindServer() {
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
                BluzActivity.this.handleStateChangeMsg(state);
            }

            @Override
            public void handlReadmsg(Response response) {
                BluzActivity.this.handlReadmsg(response);
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
                BluzActivity.this.onServiceConnected(mService);
                handleStateChangeMsg(mService.getState());
//                mService.start();
            }

        };
        Intent service = new Intent();
        service.setClass(this, LocalService.class);
        bindService(service, mconn, BIND_AUTO_CREATE);
    }
    

    protected void onServiceConnected(LocalService mService) {
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mbound && mService != null) {
//            mService.stop();
        }
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }

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
//            case R.id.about:
//                startAbout();
//                break;                
        }
        
        return false;
    }

    private void startAbout() {
//        Intent intent = new Intent(AboutIntents.ACTION_SHOW_ABOUT_DIALOG);
//
//        intent.putExtra(AboutIntents.EXTRA_PACKAGE_NAME, getPackageName());
//        
//        //Supply the image.
//        /*//alternative 2b: Put the image resId into the provider.
//        Bitmap image = BitmapFactory.decodeResource(getResources(), 
//                R.drawable.icon);//lossy
//        String uri = Images.Media.insertImage(getContentResolver(), image,
//                getString(R.string.about_logo_title), 
//                getString(R.string.about_logo_description));
//        intent.putExtra(AboutIntents.EXTRA_LOGO, uri);*/
//        
//        //alternative 3: Supply the image name and package.
//        intent.putExtra(AboutIntents.EXTRA_ICON_RESOURCE, getResources()
//                .getResourceName(R.drawable.ic_menu_info_details));
//        Log.i(TAG, "package for icon: " + getResources()
//                .getResourcePackageName(R.drawable.ic_menu_info_details));
//
//        intent.putExtra(AboutIntents.EXTRA_APPLICATION_LABEL,
//                getString(R.string.app_name));
//        
//        //Get the app version
//        String version = "?";
//        try {
//                PackageInfo pi = getPackageManager().getPackageInfo(
//                        getPackageName(), 0);
//                version = pi.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//                Log.e(TAG, "Package name not found", e);
//        }
//        intent.putExtra(AboutIntents.EXTRA_VERSION_NAME, version);
//        
//        intent.putExtra(AboutIntents.EXTRA_COMMENTS,
//                getString(R.string.about_comments));
//        intent.putExtra(AboutIntents.EXTRA_COPYRIGHT,
//                getString(R.string.about_copyright));
//        intent.putExtra(AboutIntents.EXTRA_WEBSITE_LABEL,
//                getString(R.string.about_website_label));
//        intent.putExtra(AboutIntents.EXTRA_WEBSITE_URL,
//                getString(R.string.about_website_url));
//        intent.putExtra(AboutIntents.EXTRA_AUTHORS, getResources()
//                .getStringArray(R.array.about_authors));
//        intent.putExtra(AboutIntents.EXTRA_DOCUMENTERS, getResources()
//                .getStringArray(R.array.about_documenters));
//        intent.putExtra(AboutIntents.EXTRA_TRANSLATORS, getResources()
//                .getStringArray(R.array.about_translators));
//        intent.putExtra(AboutIntents.EXTRA_ARTISTS, getResources()
//                .getStringArray(R.array.about_artists));
//
//        // Supply resource name of raw resource that contains the license:
//        intent.putExtra(AboutIntents.EXTRA_LICENSE_RESOURCE, getResources()
//                .getResourceName(R.raw.license_short));
//        
//        // Start about  Needs to be "forResult" with requestCode>=0
//        // because the About dialog may call elements from your Manifest by your
//        // package name.
//        startActivityForResult(intent, 0);
    }

    void ensureDiscoverable() {
        if (D) {
            Log.d(TAG, "ensure discoverable");
        }
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (D) {
            Log.e(TAG, "-- ON STOP --");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (D) {
            Log.e(TAG, "++ ON START ++");
        }

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
        if (D) {
            Log.e(TAG, "- ON PAUSE -");
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D) {
            Log.e(TAG, "+ ON RESUME +");
        }

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
