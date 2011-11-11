
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
        Log.d(TAG , "name: " + name + "\taddress: " + address + "\tboundState: " + boundState
                + "\tboundState desc: " + boundStateDesc);
        BluetoothClass classs = device.getBluetoothClass();
        
        int deviceClass = classs.getDeviceClass();
        String deviceClassDesc = ReflecUtil.fieldName(BluetoothClass.Device.class, "", deviceClass);
        int majorDeviceClass = classs.getMajorDeviceClass();
        String  majorDeviceClassDesc = ReflecUtil.fieldName(BluetoothClass.Device.Major.class, "", majorDeviceClass);
        String service = determinateService(classs);
        
        Log.d(TAG, "deviceClass: " + deviceClass + "\tdeviceClassDesc: " + deviceClassDesc + 
                    "majorDeviceClass: " + majorDeviceClass + "\tmajorDeviceClassDesc" + majorDeviceClassDesc + 
                    service);
    }

    private static String determinateService(BluetoothClass classs) {
        String service = "";
        int[] services = new int[]{};
        for (int i : services) {
            if (classs.hasService(i)) {
                service = ReflecUtil.fieldName(BluetoothClass.Service.class, "", i);
                break;
            }
        }
        
        return service;
    }

}
