
package com.broadgalaxy.bluz;

import com.broadgalaxy.bluz.protocol.MessageRequest;

import android.bluetooth.BluetoothDevice;
import android.os.Message;

public interface IChatService {


    public static final int STATE_CONNECTED = 3; // now connected to a remote
    // device
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing
    // connection
    public static final int STATE_LISTEN = 1; // now listening for incoming
    // connections
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0; // we're doing nothing

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * 
     * @param device The BluetoothDevice to connect
     */
    public abstract void connect(BluetoothDevice device);

    /**
     * Return the current connection state.
     */
    public abstract int getState();

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public abstract void start();

    /**
     * Stop all threads
     */
    public abstract void stop();

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * 
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public abstract void write(MessageRequest msg, byte[] out);

}
