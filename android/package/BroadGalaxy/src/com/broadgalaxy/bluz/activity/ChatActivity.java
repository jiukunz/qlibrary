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

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.BluetoothChatService;
import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.protocol.MessageRequest;
import com.broadgalaxy.bluz.protocol.MessageResponse;
import com.broadgalaxy.bluz.protocol.Response;
import com.broadgalaxy.util.Log;
import com.broadgalaxy.util.MiscUtil;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main Activity that displays the current chat session.
 */
public class ChatActivity extends BluzActivity {
    private static final int REQUEST_CONTACTS = 1;

    static final boolean D = true;

    // Debugging
    static final String TAG = "ChatActivity";

    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = "";

    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;

    private ListView mConversationView;

    /**
     * @param msg
     */
    protected void handleStateChangeMsg(int state) {
        if (D) {
            Log.i(TAG,
                    "MESSAGE_STATE_CHANGE: " + state + " "
                            + BluetoothChatService.toStateDesc(state));
        }
        mSendButton.setEnabled(IChatService.STATE_CONNECTED == state);
        switch (state) {
            case IChatService.STATE_CONNECTED:
                mConnectedDeviceName = mService.getConnectDName();
                mTitle.setText(R.string.title_connected);
                mTitle.append(mConnectedDeviceName);
                mConversationArrayAdapter.clear();
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
    protected void handlReadmsg(Response response) {
        // byte[] readBuf = (byte[]) response.obj;
        // // construct a string from the valid bytes in the buffer
        String readMessage = "";
        MessageResponse msgRes = null;
        if (response instanceof MessageResponse) {
            msgRes = (MessageResponse) response;
            readMessage = msgRes.getMsg();
            mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
        }
    }

    /**
     * @param msg
     */
    protected void handleWriteMsg(Message msg) {
        // byte[] writeBuf = (byte[]) msg.obj;

        byte[] writeBuf = ((MessageRequest) msg.obj).getmMsg().getBytes();
        // construct a string from the buffer
        String writeMessage = new String(writeBuf);
        mConversationArrayAdapter.add("Me:  " + writeMessage);
    }

    EditText mDestAddressText;

    private EditText mOutEditText;

    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    private Button mSendButton;

    // Layout Views
    private TextView mTitle;

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the
            // message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            if (D)
                Log.i(TAG, "END onEditorAction");
            return true;
        }
    };

    private int mUserId;

    /**
     * 
     */
    protected void onBluetoothEnabled() {
        super.onBluetoothEnabled();
        // Bluetooth is now enabled, so set up a chat session
        setupChat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.broadgalaxy);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        setupChat();
    }

    /**
     * Sends a message.
     * 
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (/* mChatService. */getState() != IChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write

            byte[] send = message.getBytes();
            int fromAddress = mUserId;
            String userId = mDestAddressText.getText().toString();
            int toAddress = MiscUtil.userid2int(userId);
            MessageRequest msgR = new MessageRequest(fromAddress, toAddress,
                    MessageRequest.ENCODE_CODE, message);

            write(msgR, send);
            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        ((Button) findViewById(R.id.select_contact)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pick = new Intent(Intent.ACTION_PICK);
//                pick.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                pick.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pick, REQUEST_CONTACTS);
            }
        });

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        mUserId = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).getInt(
                Application.PREF_USER_ID, 0);
        mDestAddressText = (EditText) findViewById(R.id.dest_address);
        if (mUserId != 0) {
            mDestAddressText.setText(mUserId + "");
        }
        mDestAddressText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                }
            }
        });
        // Initialize the compose field with a listener for the return key
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = view.getText().toString();
                sendMessage(message);
            }
        });

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CONTACTS == requestCode && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d(TAG, "uri: " + uri);
            Cursor c = getContentResolver().query(uri, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    String[] cNames = c.getColumnNames();
                    Log.d(TAG, "column names: " + cNames);
                    break;
                } while (c.moveToNext());
            }
        }
    }

    
}
