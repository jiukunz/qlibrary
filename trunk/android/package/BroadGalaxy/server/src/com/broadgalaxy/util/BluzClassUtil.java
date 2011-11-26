
package com.broadgalaxy.util;

import java.lang.reflect.Field;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

public class BluzClassUtil {
    private static final String TAG = BluzClassUtil.class.getSimpleName();

    public static void dump(BluetoothDevice device) {
        String name = device.getName();
        String address = device.getAddress();
        int boundState = device.getBondState();
        String boundStateDesc = ReflecUtil.fieldName(BluetoothDevice.class, "BOND_", boundState);

        String message = "name: " + name + "\taddress: " + address + "\tboundState: " + boundState
                + "\tboundStateDesc: " + boundStateDesc;
        BluetoothClass classs = device.getBluetoothClass();
        
        int deviceClass = classs.getDeviceClass();
        String deviceClassDesc = ReflecUtil.fieldName(BluetoothClass.Device.class, "", deviceClass);
        int majorDeviceClass = classs.getMajorDeviceClass();
        String  majorDeviceClassDesc = ReflecUtil.fieldName(BluetoothClass.Device.Major.class, "", majorDeviceClass);
        String service = determinateService(classs);
        
        message += "\tdeviceClass: " + deviceClass + "\tdeviceClassDesc: " + deviceClassDesc + 
                    "\tmajorDeviceClass: " + majorDeviceClass + "\tmajorDeviceClassDesc: " + majorDeviceClassDesc + 
                    "\tservice: " + service;
        Log.d(TAG, message);
    }

    private static String determinateService(BluetoothClass classs) {
        String service = "";
        int[] services = new int[]{ BluetoothClass.Service.AUDIO,
                                    BluetoothClass.Service.CAPTURE,
                                    BluetoothClass.Service.INFORMATION,
                                    BluetoothClass.Service.LIMITED_DISCOVERABILITY,
                                    BluetoothClass.Service.NETWORKING,
                                    BluetoothClass.Service.OBJECT_TRANSFER,
                                    BluetoothClass.Service.RENDER,
                                    BluetoothClass.Service.TELEPHONY};
        for (int i : services) {
            if (classs.hasService(i)) {
                service += ReflecUtil.fieldName(BluetoothClass.Service.class, "", i) + " ";
            }
        }
        
        if (service.length() < 2 ) {
            service = "un known service";
        }
        
        return service;
    }

}
