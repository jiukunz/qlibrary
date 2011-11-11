package com.broadgalaxy.bluz;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class LocalService extends android.app.Service implements IChatService {
    
    
    public class LocalBinder extends Binder{
        public LocalService getChatService() {
            return LocalService.this;
        }
    }
    
    public interface OnMsgCallBack {
        public void handToastmsg(Message msg);
        public void handleDeviceNameMsg(Message msg);

        public void handlReadmsg(Message msg);

        public void handleWriteMsg(Message msg);

        public void handlStateChangeMsg(int state);
    }
    
    
    
    private IBinder mBinder = new LocalBinder();
    
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                        handlStateChangeMsg(msg.arg1);
                        break;
                    case MESSAGE_WRITE:
                        handleWriteMsg(msg);
                        break;
                    case MESSAGE_READ:
                        handlReadmsg(msg);
                        break;
                    case MESSAGE_DEVICE_NAME:
                        handleDeviceNameMsg(msg);
                        break;
                    case MESSAGE_TOAST:
                        handToastmsg(msg);
                        break;
                }
            }
    };
    private IChatService mService = new BluetoothChatService(this, mHandler);;

    private List<OnMsgCallBack> mListener = new ArrayList<OnMsgCallBack>();
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    protected void handToastmsg(Message msg) {
        for (OnMsgCallBack c : mListener) {
            c.handToastmsg(msg);
        }
    }

    protected void handleDeviceNameMsg(Message msg) {
        for (OnMsgCallBack c : mListener) {
            c.handleDeviceNameMsg(msg);
        }
    }

    protected void handlReadmsg(Message msg) {
        for (OnMsgCallBack c : mListener) {
            c.handlReadmsg(msg);
        }
    }

    protected void handleWriteMsg(Message msg) {
        for (OnMsgCallBack c : mListener) {
            c.handleWriteMsg(msg);
        }        
    }

    protected void handlStateChangeMsg(int state) {
        for (OnMsgCallBack c : mListener) {
            c.handlStateChangeMsg(state);
        }        
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }
    @Override
    public void connect(BluetoothDevice device) {
        mService.connect(device);
    }
    @Override
    public int getState() {
        return mService.getState();
    }
    
    @Override
    public void start() {
        mService.start();
    }
    @Override
    public void stop() {
        mService.stop();
    }
    @Override
    public void write(byte[] out) {
        mService.write(out);
    }

    public void registerOnMsgCallback(OnMsgCallBack callback) {
        mListener.add(callback);
    }

    public void unRegisterOnMsgCallback(OnMsgCallBack callback) {
        mListener.remove(callback);
    }
}
