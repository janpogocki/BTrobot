package com.btrobot.btrobot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Mode2Activity extends AppCompatActivity {

    EditText editText;
    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2);

        editText = (EditText) findViewById(R.id.editText);
        button6 = (Button) findViewById(R.id.button6);

        // sprawdzanie wartości odległości
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("") || editText.getText().toString().equals("0") || editText.getText().toString().equals("00") || editText.getText().toString().equals("000")){
                    // wywalamy popup, ze bledna wartosc w formularzu
                    new AlertDialog.Builder( Mode2Activity.this )
                            .setTitle( "Błąd" )
                            .setMessage( "Błędna wartość odległości!" )
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "Positive" );
                                    editText.setText("");
                                }
                            })
                            .show();
                }
                else {
                    // jest ok, wiec przesyłamy do mainactivity i zamykamy
                    Intent resultData = new Intent();
                    resultData.putExtra("mode2val", editText.getText().toString());
                    setResult(Activity.RESULT_OK, resultData);
                    finish();
                }
            }
        });
    }
}
