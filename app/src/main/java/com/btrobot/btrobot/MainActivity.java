package com.btrobot.btrobot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private Button button, button2, button3, button4, button5;
    String adresBluetooth = "";
    BluetoothSocket mmSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // sprawdzamy czy urzadzenia ma bt
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(getApplicationContext(), "Urządzenie nie wspiera technologii Bluetooth!", Toast.LENGTH_SHORT).show();
                    }

                    // wlaczamy bt
                    button.setEnabled(false);
                    button.setText("Trwa łączenie...");
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 0);
                    }
                    else{
                        openSearchBTActivity();
                    }
                }
            });
        }

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BTcommunication btcommunication = new BTcommunication(mmSocket);
                btcommunication.send("1");
                Toast.makeText(MainActivity.this, "Wysłano komunikat: 1", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mode2 = new Intent(getApplicationContext(), Mode2Activity.class);
                startActivityForResult(mode2, 2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BTcommunication btcommunication = new BTcommunication(mmSocket);
                btcommunication.send("3");
                Toast.makeText(MainActivity.this, "Wysłano komunikat: 3", Toast.LENGTH_SHORT).show();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BTcommunication btcommunication = new BTcommunication(mmSocket);
                btcommunication.send("4");
                Toast.makeText(MainActivity.this, "Wysłano komunikat: 4", Toast.LENGTH_SHORT).show();

                Intent mode4 = new Intent(getApplicationContext(), Mode4Activity.class);
                startActivity(mode4);
            }
        });
    }

    private void openSearchBTActivity() {
        Intent sbta = new Intent(getApplicationContext(), SearchBTActivity.class);
        startActivityForResult(sbta, 1);
    }

    private void restartApp() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            // activity z wyszukiwaniem urządzen BT
            case 0:
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled())
                    openSearchBTActivity();
                else
                    restartApp();
                break;

            // powrot z wyszukiwarki
            case 1:
                if (resultCode == RESULT_OK) {
                    adresBluetooth = data.getStringExtra("adresBluetooth");
                    button.setText("Połączono z robotem");
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button4.setEnabled(true);
                    button5.setEnabled(true);


                    // ustanawiamy polaczenie przez sockety
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(adresBluetooth);
                    BluetoothSocket tmp = null;

                    try {
                        tmp = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                        tmp = (BluetoothSocket) m.invoke(device, 1);
                        mmSocket = tmp;
                        mmSocket.connect();
                        BluetoothSocketHandler.setSocket(mmSocket);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Błąd z połączeniem Bluetooth.\nAplikacja została zrestartowana.", Toast.LENGTH_SHORT).show();
                        restartApp();
                    }

                }
                else
                    restartApp();
                break;

            // powrót z modułu 2
            case 2:
                if (resultCode == RESULT_OK){
                    String mode2val = data.getStringExtra("mode2val");
                    NumberTo3Digit n23d = new NumberTo3Digit(mode2val);
                    BTcommunication btcommunication = new BTcommunication(mmSocket);
                    StringBuilder toSend = new StringBuilder("2" + n23d.number);
                    btcommunication.send(toSend.toString());
                    Toast.makeText(MainActivity.this, "Wysłano komunikat: 2" + n23d.number, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

