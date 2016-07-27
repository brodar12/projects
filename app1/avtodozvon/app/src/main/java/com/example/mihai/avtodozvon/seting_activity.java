package com.example.mihai.avtodozvon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class seting_activity extends AppCompatActivity
{

    Button button6;
    AlertDialog.Builder mesage1;
   EditText editText0,editText1,editText2,
           editText3,editText4,editText5,editText7,
           editText8,editText9;
    String[] val=new String[12];
    boolean control=true;
    int increments=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting_activity);
        setTitle("Settings");
        button6=(Button) findViewById(R.id.button6);
        editText0=(EditText) findViewById(R.id.editText0);
        editText1=(EditText) findViewById(R.id.editText1);
        editText2=(EditText) findViewById(R.id.editText2);
        editText3=(EditText) findViewById(R.id.editText3);
        editText4=(EditText) findViewById(R.id.editText4);
        editText5=(EditText) findViewById(R.id.editText5);
        editText7=(EditText) findViewById(R.id.editText7);
        editText8=(EditText) findViewById(R.id.editText8);
        editText9=(EditText) findViewById(R.id.editText9);

            citire_fisier();
            editText0.setText(val[0]);
            editText1.setText(val[1]);
            editText2.setText(val[2]);
            editText3.setText(val[3]);
            editText4.setText(val[4]);
            editText5.setText(val[5]);
            editText7.setText(val[6]);
            editText8.setText(val[7]);
            editText9.setText(val[8]);

        // Butonul SAVE SETINGS
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                control=true;
                    val[0] = editText0.getText().toString();
                    val[1] = editText1.getText().toString();
                    val[2] = editText2.getText().toString();
                    val[3] = editText3.getText().toString();
                    val[4] = editText4.getText().toString();
                    val[5] = editText5.getText().toString();
                    val[6] = editText7.getText().toString();
                    val[7] = editText8.getText().toString();
                    val[8] = editText9.getText().toString();


                // Verific daca toate cimpurile sunt completate
                for(int i=0;i<9;i++)
                    {
                        if(val[i].equals(""))
                        {
                            val[i]="0";
                            control=false;
                            ++increments;
                        }
                    }
                Date_transmise.valoare=increments;

            if(control!=false) {
                // Verifcarea cimpurilor la conditii
                if (Integer.parseInt(val[0]) >= Integer.parseInt(val[1])) {
                    editText0.setText("");
                    val[0] = "0";
                    control = false;
                    alert_mesage("Error: Time for call !!! MIN < MAX");
                }
                else if (Integer.parseInt(val[2]) >= Integer.parseInt(val[3])) {
                    editText2.setText("");
                    val[2] = "0";
                    control = false;
                    alert_mesage("Error: Time for all call !!! MIN < MAX");
                }
                else if(Integer.parseInt(val[0]) <13 )
                {
                    editText0.setText("");
                    val[0] = "0";
                    control = false;
                    alert_mesage("Error: min value for time for call !!! min<13");
                }
                else if(Integer.parseInt(val[2]) <13 )
                {
                    editText2.setText("");
                    val[2] = "0";
                    control = false;
                    alert_mesage("Error: min value for time for all call !!! min<13");
                }
                else if(Integer.parseInt(val[4]) <8)
                {
                    editText4.setText("");
                    val[4] = "0";
                    control = false;
                    alert_mesage("Error: min value for time for pause !!! input number<8");
                }
                else if (Integer.parseInt(val[4]) >= Integer.parseInt(val[6])) {
                    editText4.setText("");
                    val[4] = "0";
                    control = false;
                    alert_mesage("Error: Time for all call !!! MIN > MAX");
                }
                else if(Integer.parseInt(val[7]) <20)
                {
                    editText8.setText("");
                    val[7] = "0";
                    control = false;
                    alert_mesage("Error: min value for time for new day !!! input number<20");
                }
                else if(Integer.parseInt(val[7]) >= Integer.parseInt(val[8]))
                {
                    editText8.setText("");
                    val[7] = "0";
                    control = false;
                    alert_mesage("Error: Time new day !!! MIN > MAX");

                }
                if (control != false) {
                    deleteFile("setings2.txt");
                    salvare_fisier();
                    alert_mesage("Settings is Save!!!");
                }

            }
            else {
                alert_mesage("Set all elements!!!!");
            }

                }

        });
    }



    public void citire_fisier()
    {
        int i=0;
        try {
            InputStream citire = openFileInput("setings2.txt");
            InputStreamReader sr = new InputStreamReader(citire);
            BufferedReader reader = new BufferedReader(sr);
            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = reader.readLine()) != null)
            {
                val[i]=str;
                ++i;
            }
            citire.close();
        }
        catch(Throwable t)
        {

        }
    }



    public void salvare_fisier()
    {
        try {

            OutputStream output;
            OutputStreamWriter osw;
                    output = openFileOutput("setings2.txt", MODE_APPEND);
                    osw = new OutputStreamWriter(output);
                    osw.write(editText0.getText().toString()+"\n");
                    osw.write(editText1.getText().toString()+"\n");
                    osw.write(editText2.getText().toString()+"\n");
                    osw.write(editText3.getText().toString()+"\n");
                    osw.write(editText4.getText().toString()+"\n");
                    osw.write(editText5.getText().toString()+"\n");
                    osw.write(editText7.getText().toString()+"\n");
                    osw.write(editText8.getText().toString()+"\n");
                    osw.write(editText9.getText().toString()+"\n");
                    osw.close();
        }
        catch(Throwable t)
        {

        }
    }

    public void alert_mesage(String mesage)
    {
        mesage1=new AlertDialog.Builder(seting_activity.this);
        mesage1.setTitle("Alert!!!!")
                .setMessage(""+mesage)
                .setCancelable(false)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // button6.setEnabled(false);
                        dialog.cancel();
                    }
                });
        AlertDialog alert=mesage1.create();
        alert.show();

    }


}

