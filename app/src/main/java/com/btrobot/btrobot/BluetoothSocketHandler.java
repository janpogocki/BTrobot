package com.btrobot.btrobot;

import android.bluetooth.BluetoothSocket;

public class BluetoothSocketHandler {
    private static BluetoothSocket socket;

    public static synchronized BluetoothSocket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(BluetoothSocket socket){
        BluetoothSocketHandler.socket = socket;
    }
}
