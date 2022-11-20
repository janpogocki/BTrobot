package com.btrobot.btrobot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class SearchBTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ListView listView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bt);

        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // wyszukujemy urzadzenia
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

        listView.setAdapter(mArrayAdapter);

        // nasluchujemy i jak ktos cos wybierze, to przechwytujemy MAC-a, zamykamy sbta i przechodzimy do ma
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultData = new Intent();
                resultData.putExtra("adresBluetooth", mArrayAdapter.getItem(position).split("\n")[1]);
                setResult(Activity.RESULT_OK, resultData);
                finish();
            }
        });
    }
}