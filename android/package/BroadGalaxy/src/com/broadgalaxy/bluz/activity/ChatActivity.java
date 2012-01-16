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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.BluetoothChatService;
import com.broadgalaxy.bluz.IChatService;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.activity.BoxActivity.MessageEntity;
import com.broadgalaxy.bluz.persistence.DBHelper;
import com.broadgalaxy.bluz.persistence.IMsg;
import com.broadgalaxy.bluz.protocol.MessageRequest;
import com.broadgalaxy.bluz.protocol.MessageResponse;
import com.broadgalaxy.bluz.protocol.Response;
import com.broadgalaxy.util.Log;
import com.broadgalaxy.util.MiscUtil;

/**
 * This is the main Activity that displays the current chat session.
 */
public class ChatActivity extends BluzActivity {
    class MessageAdapter extends ArrayAdapter<MessageEntity> {
        private List<MessageEntity> mData;

        public MessageAdapter(final Context context, final List<MessageEntity> data) {
            super(context, R.layout.box_item, R.id.message, data);
            mData = data;
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout l = null;
            if (null != convertView) {
                l = (LinearLayout) convertView;
            } else {
                l = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.box_item,
                        null);
            }

            MessageEntity e = getItem(position);
            try {
                if (mUserId == Integer.parseInt(e.toAdd)) {
//                    l.setBackgroundColor(R.color.message_bk);
                    l.setBackgroundResource(R.drawable.message_bk_color);
                } else {
//                    l.setBackgroundColor(R.drawable.message_bk_I);
                    l.setBackgroundResource(0);
                }
            } catch (NumberFormatException ne) {
                Log.d(TAG, "NumberFormatException", ne);
            }
            ((TextView) l.findViewById(R.id.address)).setText("发件人：" + e.fromAdd + "\n收件人： " + e.toAdd);
            ((TextView) l.findViewById(R.id.message)).setText(
//                    "信息：" +
                    e.message);
            CharSequence time = new Date(e.timeTick).toLocaleString();
            ((TextView) l.findViewById(R.id.time)).setText("时间：" + time);

            return l;
        }
    }

    public static class MessageEntity {
        public static MessageEntity fromCursor(Cursor c) {
            MessageEntity e = new MessageEntity();
            e.id = (long) c.getInt(c.getColumnIndex(IMsg._ID));
            e.fromAdd = c.getString(c.getColumnIndex(IMsg.COLUMN_FROM_ADDRESS));
            e.toAdd = c.getString(c.getColumnIndex(IMsg.COLUMN_DEST_ADDRESS));
            e.timeTick = c.getLong(c.getColumnIndex(IMsg.COLUMN_TIME));
            e.message = c.getString(c.getColumnIndex(IMsg.COLUMN_DATA));
            e.messageLen = c.getInt(c.getColumnIndex(IMsg.COLUMN_DATA_LEN));
            e.status = c.getInt(c.getColumnIndex(IMsg.COLUMN_STATUS));
            return e;
        }

        public static ContentValues toValues(MessageEntity e) {
            ContentValues v = new ContentValues();
            v.put(IMsg.COLUMN_FROM_ADDRESS, e.fromAdd);
            v.put(IMsg.COLUMN_DEST_ADDRESS, e.toAdd);
            v.put(IMsg.COLUMN_DATA, e.message);
            v.put(IMsg.COLUMN_DATA_LEN, e.messageLen);
            v.put(IMsg.COLUMN_TIME, System.currentTimeMillis());
            v.put(IMsg.COLUMN_STATUS, e.status);
            return v;
        }

        public String fromAdd;
        public long id;
        public String message;
        private int messageLen;
        public int status;

        public long timeTick;

        public String toAdd;

        @Override
        public String toString() {
            return message;
        }

    }

    static final boolean D = true;

    private static final int REQUEST_CONTACTS = 1;

    // Debugging
    static final String TAG = "ChatActivity";

    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = "";

    // Array adapter for the conversation thread
    private MessageAdapter mConversationArrayAdapter;

    private ListView mConversationView;

    private DBHelper mDB;

    EditText mDestAddressText;

    private EditText mOutEditText;

    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    private Button mSendButton;

    // Layout Views
    private TextView mTitle;

    private int mUserId;

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

    private Button mReciptBtn;

    /**
     * 
     */
    protected void fillDataFromDB() {
        List<MessageEntity> historyData = new ArrayList<MessageEntity>();
        // Initialize the array adapter for the conversation thread
        String selection = IMsg.COLUMN_FROM_ADDRESS + "=? or " + IMsg.COLUMN_DEST_ADDRESS + "=?";
        String[] selectArgs = new String[] {
                mUserId + "", mUserId + ""
        };
        Cursor c = mDB.getReadableDatabase().query(DBHelper.MSG_TABLE_NAME, null, selection,
                selectArgs, null,
                null, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                historyData.add(MessageEntity.fromCursor(c));
                c.moveToNext();
            }
        }

        mConversationArrayAdapter = new MessageAdapter(this, historyData);
        mConversationView.setAdapter(mConversationArrayAdapter);
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
    protected void handleReadmsg(Response response) {
        // byte[] readBuf = (byte[]) response.obj;
        // // construct a string from the valid bytes in the buffer
        String readMessage = "";
        MessageResponse msgRes = null;
        if (response instanceof MessageResponse) {
            msgRes = (MessageResponse) response;
            readMessage = msgRes.getMsg();
            // mConversationArrayAdapter.add(mConnectedDeviceName + ":  " +
            // readMessage);
            fillDataFromDB();
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
        mSendButton.setEnabled(IChatService.STATE_CONNECTED == state);
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
    protected void handleWriteMsg(Message msg) {
        // byte[] writeBuf = (byte[]) msg.obj;

        byte[] writeBuf = ((MessageRequest) msg.obj).getmMsg().getBytes();
        // construct a string from the buffer
        String writeMessage = new String(writeBuf);
        // mConversationArrayAdapter.add("Me:  " + writeMessage);
        fillDataFromDB();
    }

    /**
     * @param msg
     */
    protected void handToastmsg(Message msg) {
        Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                .show();
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
                    long id = c.getLong(c.getColumnIndex(ContactsContract.Contacts._ID));
                    Log.d(TAG, "column names: " + cNames);
                    c = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                            null, 
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                            new String[]{id + ""}, null);
                            while (c.moveToNext()) {
                                String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                
                                try {
                                    int phoneNumberInt = Integer.parseInt(phoneNumber);
                                    mDestAddressText.setText(phoneNumber);
                                } catch (NumberFormatException e){
                                    Toast.makeText(this, "电话号码无效！！！", Toast.LENGTH_LONG).show();
                                }
                                
                                Log.d(TAG, "select phoneNumber: " + phoneNumber);
                            } 
                    cNames = c.getColumnNames();
                    break;
                } while (c.moveToNext());
            }
        }
    }

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

        mDB = new DBHelper(this);

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
                    MessageRequest.ENCODE_CODE, //
                    message);

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
                // pick.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                pick.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pick, REQUEST_CONTACTS);
            }
        });

        mConversationView = (ListView) findViewById(R.id.in);

        mUserId = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).getInt(
                Application.PREF_USER_ID, 0);
        fillDataFromDB();

        mReciptBtn = (Button) findViewById(R.id.receiver);
        mReciptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pick = new Intent(Intent.ACTION_PICK);
                // pick.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                pick.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pick, REQUEST_CONTACTS);
            }
        });
        mDestAddressText = (EditText) findViewById(R.id.dest_address);
        if (mUserId != 0) {
            mDestAddressText.setText(mUserId + "");
        }
        mDestAddressText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

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
}
