package com.example.mihai.avtodozvon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class statistic_activity extends AppCompatActivity {

    ListView listView2;
    ArrayList<String> list=new ArrayList<String>();
    AlertDialog.Builder mesage1;
    AlertDialog alert;
   database_work myDb;
    Button button2;
    EditText editText6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_activity);
        setTitle("Statistic activity");

        button2=(Button) findViewById(R.id.button2);
        editText6=(EditText) findViewById(R.id.editText6);


        listView2=(ListView) findViewById(R.id.listView2);
         myDb=new database_work(this);

        list=myDb.show_list();


            final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView2.setAdapter(adapter);


        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(editText6.getText().toString().equals(""))
                {
                mesage("Input date_time!!!!");
                }
                else
                {
                    int verif=myDb.delete_data_1(editText6.getText().toString());

                    if(verif==0)
                    {
                        mesage("Date is not delete!!!");
                        editText6.setText("");
                    }
                    else
                    {
                        list.removeAll(list);
                        editText6.setText("");
                        mesage("Date succes delete!!!");
                    }

                }

            }
        });

    }


    public void mesage(String text)
    {
        mesage1=new AlertDialog.Builder(this);
        mesage1.setTitle("Alert!!!!")
                .setMessage(""+text)
                .setCancelable(false)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert=mesage1.create();
        alert.show();
    }


}
