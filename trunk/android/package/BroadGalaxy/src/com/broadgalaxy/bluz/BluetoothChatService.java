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

package com.broadgalaxy.bluz;

import com.broadgalaxy.bluz.activity.BluzActivity;
import com.broadgalaxy.bluz.activity.ChatActivity;
import com.broadgalaxy.bluz.protocol.MessageRequest;
import com.broadgalaxy.util.ByteUtil;
import com.broadgalaxy.util.Log;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for incoming
 * connections, a thread for connecting with a device, and a thread for
 * performing data transmissions when connected.
 */
public class BluetoothChatService implements IChatService {
    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted (or
     * until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            setName("AcceptThread");
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try {
                UUID uuid = MY_UUID;
                uuid = WELL_KNOWN_UUID;
                String name = NAME;
                Log.d(TAG, "listen rfcommsocket using name: " + name + "\tuuid: " + uuid);
                boolean insecure = false;
                if (!insecure) {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(name, uuid);
                } else {
//                    tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(name, uuid);
                }
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            
            mmServerSocket = tmp;
        }

        public void cancel() {
            if (D) {
                Log.d(TAG, "cancel " + this);
            }
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }

        public void run() {
            if (D) {
                Log.d(TAG, "BEGIN mAcceptThread " + this);
            }
            setName("AcceptThread");
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothChatService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Start the connected thread.
                                onConnected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // Either not ready or already connected.
                                // Terminate new socket.
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
            if (D) {
                Log.i(TAG, "END mAcceptThread");
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection with a
     * device. It runs straight through; the connection either succeeds or
     * fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
    
        private final BluetoothSocket mmSocket;
    
        public ConnectThread(BluetoothDevice device) {
            setName("ConnectThread");
            mmDevice = device;
            BluetoothSocket tmp = null;
    
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                UUID uuid = MY_UUID;
                uuid = WELL_KNOWN_UUID;
                Log.d(TAG, "create rfcommsocket using uuid: " + uuid);
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                Log.e(TAG, "create outgoing connection failed", e);
            }
            mmSocket = tmp;
        }
    
        public void cancel() {
            if (D) {
                Log.d(TAG, "cancel " + this);
            }
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    
        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");
    
            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
    
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                Log.e(TAG, "unable to connect remote device: " + mmDevice.getAddress(), e);
                onConnectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                BluetoothChatService.this.start();
    
                Log.i(TAG, "END mConnectThread");
                return;
            }
    
            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }
    
            // Start the connected thread
            onConnected(mmSocket, mmDevice);
    
            Log.i(TAG, "BEGIN mConnectThread");
        }
    }

    /**
     * This thread runs during a connection with a remote device. It handles all
     * incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;

        private final OutputStream mmOutStream;

        private final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            setName("ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void cancel() {
            if (D) {
                Log.d(TAG, "cancel " + this);
            }
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    if (DEBUG_MSG) {
                        Log.d(TAG, "RCV MSG: " + format(buffer, bytes));
                    }

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(LocalService.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    onConnectionLost();
                    break;
                }
            }

            Log.i(TAG, "END mConnectedThread");
        }

        /**
         * Write to the connected OutStream.
         * 
         * @msg null if not MessageRequest;
         * @param buffer The bytes to write
         */
        public void write(MessageRequest msg, byte[] buffer) {
            try {
                
                if (null != msg) {
                    buffer = msg.getByte();
                }
                    
                mmOutStream.write(buffer);
                if (DEBUG_MSG) {
                    Log.d(TAG, "SND MSG: " + format(buffer, buffer.length));
                }
                
                // Share the sent message back to the UI Activity
                if (null != msg) {
                    mHandler.obtainMessage(LocalService.MESSAGE_WRITE, -1, -1, msg).sendToTarget();
                }
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }
    }

    private static final boolean DEBUG_MSG = true;  
    private static final boolean D = true;

    // Unique UUID for this application
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID WELL_KNOWN_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    private static final UUID TARGET_UUID = UUID.fromString("0018-11-518058");

    // Name for the SDP record when creating server socket
    private static final String NAME = "ChatActivity";

    // Debugging
    private static final String TAG = "BluetoothChatService";

    private AcceptThread mAcceptThread;
    
    // Member fields
    private final BluetoothAdapter mAdapter;

    private ConnectedThread mConnectedThread;

    private ConnectThread mConnectThread;

    private final Handler mHandler;

    private int mState;

    /**
     * Constructor. Prepares a new ChatActivity session.
     * 
     * @param context The UI Activity Context
     * @param handler A Handler to send messages back to the UI Activity
     */
    public BluetoothChatService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }

    private String format(byte[] buffer, int bytes) {
        String hexStr = "0x";
        int count = 0;
        for (byte b : buffer) {
            if (count == bytes) {
                break;
            }
            count++;
            
            hexStr += " " + ByteUtil.byte2HexString(b);
        }
        
        return hexStr;
    }

    /* (non-Javadoc)
     * @see com.broadgalaxy.bluz.IChatService#connect(android.bluetooth.BluetoothDevice)
     */
    @Override
    public synchronized void connect(BluetoothDevice device) {
        if (D)
            Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * 
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    private synchronized void onConnected(BluetoothSocket socket, BluetoothDevice device) {
        if (D)
            Log.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one
        // device
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(LocalService.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BluzActivity.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void onConnectionFailed() {
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(LocalService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void onConnectionLost() {
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(LocalService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /* (non-Javadoc)
     * @see com.broadgalaxy.bluz.IChatService#getState()
     */
    @Override
    public synchronized int getState() {
        return mState;
    }

    /**
     * Set the current state of the chat connection
     * 
     * @param state An integer defining the current connection state
     */
    private synchronized void setState(int state) {
        if (D)
            Log.d(TAG, "setState() " +
                    mState + " " + toStateDesc(mState) +
                    " -> "
                    + state + " " + toStateDesc(state));
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(LocalService.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public static String toStateDesc(int state) {
        String desc = "unknown state";
        Field[] fields = BluetoothChatService.class.getFields();
        for (Field f : fields) {
            if (f.getName().startsWith("STATE_")) {
                try {
                    if (state == f.getInt(null)) {
                        desc = f.getName();
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return desc;
    }

    /* (non-Javadoc)
     * @see com.broadgalaxy.bluz.IChatService#start()
     */
    @Override
    public synchronized void start() {
        if (D)
            Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    /* (non-Javadoc)
     * @see com.broadgalaxy.bluz.IChatService#stop()
     */
    @Override
    public synchronized void stop() {
        if (D)
            Log.d(TAG, "stop");
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }
        setState(STATE_NONE);
    }

    /* (non-Javadoc)
     * @see com.broadgalaxy.bluz.IChatService#write(byte[])
     */
    @Override
    public void write(MessageRequest msg, byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) {
                Log.e(TAG, "ignore write request when not in STATE_CONNECTED.");
                return;
            }
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(msg, out);
    }
}
