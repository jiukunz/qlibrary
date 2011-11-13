package com.broadgalaxy.bluz;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.broadgalaxy.bluz.core.FbkResponse;
import com.broadgalaxy.bluz.core.IccResponse;
import com.broadgalaxy.bluz.core.LocationResponse;
import com.broadgalaxy.bluz.core.MessageResponse;
import com.broadgalaxy.bluz.core.Pack;
import com.broadgalaxy.bluz.core.Response;
import com.broadgalaxy.bluz.core.SigResponse;
import com.broadgalaxy.bluz.core.StsResponse;
import com.broadgalaxy.bluz.core.TimResponse;
import com.broadgalaxy.bluz.core.ZrdResponse;
import com.broadgalaxy.util.Log;

public class LocalService extends android.app.Service implements IChatService {
    private static final String TAG = LocalService.class.getSimpleName();    
    
    private IBinder mBinder = new LocalBinder();
    
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;

    private static final boolean DEBUG = true;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    
    @Override
    public void onCreate() {
        mService.start();
        super.onCreate();
    }
    
    @Override
    public void onDestroy() {
        mService.stop();
        super.onDestroy();
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
        int len = msg.arg1;
        byte[] msgBytes = (byte[]) msg.obj;
        int size = msg.arg1;
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put(msgBytes, 0, size);
        msgBytes = buffer.array();
        byte[] code = new byte[5];
        buffer.position(0);
        buffer.get(code, 0 , 5);
        String codeStr = new String(code);
        Response res = null;
        if (Pack.CODE_MESSGE.equals(codeStr)) {
            res = new MessageResponse(msgBytes);
        } else if (Pack.CODE_LOCATION.equals(codeStr)) {
            res = new LocationResponse(msgBytes);
        } else if (Pack.CODE_FBK.equals(codeStr)) {
            res = new FbkResponse(msgBytes);
        } else if (Pack.CODE_ICC.equals(codeStr)) {
            res = new IccResponse(msgBytes);
        } else if (Pack.CODE_SIG.contains(codeStr)) {
            res = new SigResponse(msgBytes);
        } else if (Pack.CODE_STS.equals(codeStr)) {
            res = new StsResponse(msgBytes);
        } else if (Pack.CODE_TIM.equals(codeStr)) {
            res = new TimResponse(msgBytes);
        } else if (Pack.CODE_ZRd.equals(codeStr)) {
            res  = new ZrdResponse(msgBytes);
        } else {
            Log.e(TAG, "unknown msg. msg: " + codeStr);
        }
        
        for (OnMsgCallBack c : mListener) {
            c.handlReadmsg(res);
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

    private List<OnMsgCallBack> mListener = new ArrayList<OnMsgCallBack>();

    private IChatService mService = new BluetoothChatService(this, mHandler);

    public class LocalBinder extends Binder{
        public LocalService getChatService() {
            return LocalService.this;
        }
    }

    public interface OnMsgCallBack {
        public void handToastmsg(Message msg);
        public void handleDeviceNameMsg(Message msg);
    
        public void handlReadmsg(Response response);
    
        public void handleWriteMsg(Message msg);
    
        public void handlStateChangeMsg(int state);
    }
}
