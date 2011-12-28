/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.broadgalaxy.bluz.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.BluetoothChatService;
import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.protocol.IccRequest;
import com.broadgalaxy.bluz.protocol.LocationRequest;
import com.broadgalaxy.bluz.protocol.LocationResponse;
import com.broadgalaxy.bluz.protocol.MessageRequest;
import com.broadgalaxy.bluz.protocol.Pack;
import com.broadgalaxy.bluz.protocol.Response;
import com.broadgalaxy.bluz.protocol.SigRequest;
import com.broadgalaxy.util.Log;

/**
 * This is the main Activity that displays the current chat session.
 */
public class LocateActivity extends BluzActivity {

    static final boolean D = true;

    // Debugging
    static final String TAG = "LocateActivity";

    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = "";

    private Button mLocateBtn;

    private TextView mLocationBTextV;

    private TextView mLocationHTextV;

    private TextView mLocationLTextV;

    private TextView mLocationTTextV;

    // Layout Views
    private TextView mTitle;

    private int mUserId;

    private TextView mLocationInfo;

    /**
     * @param msg
     */
    protected void handleDeviceNameMsg(Message msg) {
        // save the connected device's name
        mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
        Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * @param response
     */
    protected void handleReadmsg(Response response) {
        LocationResponse location = null;
        if (response instanceof LocationResponse) {
            location = (LocationResponse) response;
            mLocationLTextV.setText("经度:" + location.getLocationLStr());
            mLocationBTextV.setText("维度:" + location.getLocationBStr());
            mLocationHTextV.setText("高度:" + location.getLocationHStr());
            mLocationTTextV.setText("时间:" + location.getLocationTStr());
//            mLocationInfo.setText("raw data: " + Pack.toHexString(response.getByte()));
        }
    }

    /**
     * @param msg
     */
    protected void handleStateChangeMsg(int state) {
        if (D) {
            Log.i(TAG,
                    "MESSAGE_STATE_CHANGE: " + state + " "
                            + BluetoothChatService.toStateDesc(state));
        }
        switch (state) {
            case IChatService.STATE_CONNECTED:
                mConnectedDeviceName = mService.getConnectDName();
                mTitle.setText(R.string.title_connected);
                mTitle.append(mConnectedDeviceName);
                break;
            case IChatService.STATE_CONNECTING:
                mTitle.setText(R.string.title_connecting);
                break;
            case IChatService.STATE_LISTEN:
            case IChatService.STATE_NONE:
                mTitle.setText(R.string.title_not_connected);
                break;
        }
    }

    /**
     * @param msg
     */
    protected void handToastmsg(Message msg) {
        Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.locate);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        
        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.broadgalaxy);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        mUserId = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).getInt(
                Application.PREF_USER_ID, 0);

        mLocationTTextV = (TextView) findViewById(R.id.location_t);
        mLocationLTextV = (TextView) findViewById(R.id.location_l);
        mLocationBTextV = (TextView) findViewById(R.id.location_b);
        mLocationHTextV = (TextView) findViewById(R.id.location_h);
        mLocationInfo = (TextView) findViewById(R.id.info);
        
        mLocateBtn = (Button) findViewById(R.id.locate);
        mLocateBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                tryLocate();
            }
        });
    }

    protected void tryLocate() {
        // Check that we're actually connected before trying anything
        if (/* mChatService. */getState() != IChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        int fromAddress = Integer.valueOf("000238");
        fromAddress = mUserId; // ox 00 02 38
        Pack m = null;
        m = new LocationRequest(fromAddress, (byte) 0);
        write(null, m.getByte());
    }
    
}
