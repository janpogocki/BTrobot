package com.btrobot.btrobot;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Mode4Activity extends AppCompatActivity {

    Button button7, button8, button9, button10, button11;
    TextView textView2;
    SeekBar seekBar;
    EditText editText2;

    BTcommunication btcommunication = new BTcommunication(BluetoothSocketHandler.getSocket());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode4);

        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        textView2 = (TextView) findViewById(R.id.textView2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editText2 = (EditText) findViewById(R.id.editText2);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                NumberTo3Digit n23d = new NumberTo3Digit(Integer.toString(progress+1));
                textView2.setText("Prędkość: " + n23d.number);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //nasluchiwanie gory do nacisniecia
        button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NumberTo3Digit n23d = new NumberTo3Digit(Integer.toString(seekBar.getProgress()+1));
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    StringBuilder sb = new StringBuilder("w" + n23d.number);
                    btcommunication.send(sb.toString());
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btcommunication.send("p");

                }
                return false;
            }
        });

        //nasluchiwanie dolu
        button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NumberTo3Digit n23d = new NumberTo3Digit(Integer.toString(seekBar.getProgress()+1));
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    StringBuilder sb = new StringBuilder("s" + n23d.number);
                    btcommunication.send(sb.toString());
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btcommunication.send("p");

                }
                return false;
            }
        });

        //nasluchiwanie lewackiego xd
        button10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NumberTo3Digit n23d = new NumberTo3Digit(Integer.toString(seekBar.getProgress()+1));
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    StringBuilder sb = new StringBuilder("a" + n23d.number);
                    btcommunication.send(sb.toString());
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btcommunication.send("p");

                }
                return false;
            }
        });

        //nasluchiwanie prawego <3
        button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NumberTo3Digit n23d = new NumberTo3Digit(Integer.toString(seekBar.getProgress()+1));
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    StringBuilder sb = new StringBuilder("d" + n23d.number);
                    btcommunication.send(sb.toString());
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btcommunication.send("p");

                }
                return false;
            }
        });

        //nasluchiwanie strzalu
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText2.getText().toString().equals("") || Integer.parseInt(editText2.getText().toString()) < 100 || Integer.parseInt(editText2.getText().toString()) > 300){
                    // wywalamy popup, ze bledna wartosc w formularzu
                    new AlertDialog.Builder( Mode4Activity.this )
                            .setTitle( "Błąd" )
                            .setMessage( "Błędna wartość odległości!" )
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "Positive" );
                                    editText2.setText("");
                                }
                            })
                            .show();
                }
                else {
                    // jest ok, wiec przesyłamy do bt
                    StringBuilder sb = new StringBuilder("x" + editText2.getText().toString());
                    btcommunication.send(sb.toString());
                    Toast.makeText(Mode4Activity.this, "Wysłano komunikat: x" + editText2.getText().toString(), Toast.LENGTH_SHORT).show();
                    editText2.setText("");
                }
            }
        });
    }

    //nasluchiwanie back buttona
    @Override
    public void onBackPressed() {
        btcommunication.send("b");
        Toast.makeText(Mode4Activity.this, "Opuszczono tryb 4", Toast.LENGTH_SHORT).show();
        finish();
    }
}
