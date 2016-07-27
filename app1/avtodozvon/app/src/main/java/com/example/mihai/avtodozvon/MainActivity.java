package com.example.mihai.avtodozvon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity
{

    Button meniu_list,meniu_seting,meniu_statistic,meniu_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setTitle("Auto");

        meniu_list=(Button) findViewById(R.id.meniu_list);
        meniu_seting=(Button) findViewById(R.id.meniu_seting);
        meniu_statistic=(Button) findViewById(R.id.meniu_statistic);
        meniu_start=(Button) findViewById(R.id.meniu_start);


        meniu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent();
                go.setClass(MainActivity.this, list_activity.class);
                startActivity(go);
            }
        });

        meniu_seting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent go1 = new Intent();
                go1.setClass(MainActivity.this,seting_activity.class);
                startActivity(go1);
            }
        });

        meniu_statistic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent go2 = new Intent();
                go2.setClass(MainActivity.this,statistic_activity.class);
                startActivity(go2);
            }
        });

        meniu_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go3 = new Intent();
                go3.setClass(MainActivity.this, start_activity.class);
                startActivity(go3);
            }
        });

    }

}
