package com.btrobot.btrobot;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.OutputStream;

public class BTcommunication {
    BluetoothSocket btSocket = null;

    BTcommunication(BluetoothSocket _btSocket){
        btSocket = _btSocket;
    }

    void send(String msg){
        ConnectedThread BTct = new ConnectedThread(btSocket);
        byte[] msgBytes = msg.getBytes();
        BTct.write(msgBytes);
    }
}


class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final OutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        OutputStream tmpOut = null;

        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mmOutStream = tmpOut;
    }

    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }
}
