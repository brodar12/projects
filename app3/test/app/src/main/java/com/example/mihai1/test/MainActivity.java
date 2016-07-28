package com.example.mihai1.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.*;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {


    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();



    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    ArrayAdapter<String> aray_adapter;

    public OutputStream stream_output;

    ProgressBar progres;
    Button scaning;
    ListView list;
    TextView text_view, text_view1, text_view2;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // public String mad_device="20:15:10:29:04:23";
    public List<String> device_adres = new ArrayList<>();
    public String date_send = "0";
    Thread scan_thread;
    public Handler pod, pod1, pod2;
    public Thread fir3;
Context context;
    Intent intent;
    int val = 0, val1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        scaning = (Button) findViewById(R.id.button);
        list = (ListView) findViewById(R.id.listView);
        text_view = (TextView) findViewById(R.id.textView);
        text_view1 = (TextView) findViewById(R.id.textView2);
        text_view2=(TextView) findViewById(R.id.textView3);
        progres = (ProgressBar) findViewById(R.id.progressBar);

        scaning.setOnClickListener(click);

        intent = new Intent(this,draw_activity.class);


        aray_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, device_adres);//creem adapteru
        list.setAdapter(aray_adapter);//adaugam continutul adapterului in listview
        list.setOnItemClickListener(item_click);//am creat evenimentul setOnclickListener


        pod2 = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 5) {
                    scaning.setClickable(true);
                    list.setVisibility(View.VISIBLE);
                    progres.setVisibility(View.INVISIBLE);
                    text_view1.setVisibility(View.INVISIBLE);
                }
                //conect.setVisibility(View.VISIBLE);
                paried_device();
                aray_adapter.notifyDataSetChanged();

            }
        };

        verificari_conexiune();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(scanare_canal, filter);


    }

    AdapterView.OnItemClickListener item_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            intent.putExtra("adres_device",device_adres.get(position) );
           // conect_device(device_adres.get(position));
            Log.e("conect to:" + device_adres.get(position), "");
            startActivity(intent);
        }
    };


    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button) {

                fir3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.startDiscovery();
                        SystemClock.sleep(8000);
                        adapter.cancelDiscovery();
                        pod2.sendEmptyMessage(5);
                        //  stops2=1;
                    }
                });
                scaning.setClickable(false);
                list.setVisibility(View.INVISIBLE);
                progres.setVisibility(View.VISIBLE);
                text_view1.setVisibility(View.VISIBLE);
                text_view2.setVisibility(View.INVISIBLE);
                aray_adapter.clear();
                device_adres.clear();
                fir3.start();
            }

        }
    };


    public void verificari_conexiune() {
        if (adapter != null) {
            Log.e("tot normla cu bluetooth", "tot normla cu bluetooth");
            if (adapter.isEnabled()) {
                Log.e("este activ bluetooth", "este activ bluetooth");
                Log.e("name:" + adapter.getName(), "addres:" + adapter.getAddress());
                Log.e("state:" + adapter.getState(), "");
            } else {
                Log.e("nu este activ bluetooth", "nu este activ bluetooth");
                Toast.makeText(getApplicationContext(),"Bluetooth not active,device connect automate!!!",Toast.LENGTH_SHORT).show();
                Intent discoverableIntent=new
                        Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
        } else {
            Log.e("nu este bluetooth", "nu este bluetooth");

        }

    }


    public void paried_device() {
        Set<BluetoothDevice> get_parieddevice = adapter.getBondedDevices();
        if (get_parieddevice.size() > 0) {
            for (BluetoothDevice device : get_parieddevice) device_adres.add(device.getAddress());

        }
    }


    public BroadcastReceiver scanare_canal = scanare_canal = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                device_adres.add(device.getAddress());
            }
        }
    };



    @Override
    public void onDestroy() {

        adapter.cancelDiscovery();
        unregisterReceiver(scanare_canal);
        super.onDestroy();
    }



}
