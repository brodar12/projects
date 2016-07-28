package com.example.mihai1.test;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class draw_activity extends AppCompatActivity {

    ActionBar actionBar;
    OutputStream stream_output;
    InputStream stream_input;
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket btSocket = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public Handler pod4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Draw dr = new Draw(this);
        setContentView(dr);
        actionBar = getSupportActionBar();
        actionBar.hide();


        dr.pod1 = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    dr.invalidate();
                    Log.e("succes Handlerr", "!!!!!!");
                }

            }

            ;
        };

        dr.pod3 = new Handler() {
            public void handleMessage(Message msg) {
                    Log.e("succes date transmise:"+msg.what, "!!!!!!");
                String[] date={"+S1","+S2","+S3","+S4","+S5"};
                    send_data(date[msg.what]);
            }

            ;
        };

        dr.pod4 = new Handler() {
            public void handleMessage(Message msg) {
                Log.e("succes date transmise:"+msg.what, "!!!!!!");
                String[] date={"-S1","-S2","-S3","-S4","-S5"};
                send_data(date[msg.what]);
            }
        };

        Log.e("serializare1 mesaj:", "mesaj");
        Intent intent = getIntent();

        String data= intent.getStringExtra("adres_device");
        Log.e("intents1 mesage:", ":"+data );
        conect_device(data);

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[1024];
                int val = 0;
                while (true) {
                    Log.e("date primite", "input date!!");
                        try {
                            val = stream_input.read(buffer);
                            for(int i=0;i<2;i++){
                                Log.e("value" + val, ""+buffer[i]);
                                if(buffer[i]=='F' || buffer[i]=='9' )Draw.acess_stare=false;
                                else if(buffer[i]=='A' || buffer[i]=='1')Draw.acess_stare=true;
                            }

                            //if(val>0)pod4.sendEmptyMessage(val);
                        } catch (IOException e) {
                            Log.e("not input data!!!", "");
                           break;
                        }

                    SystemClock.sleep(200);
                }

            }
        }).start();*/

    }




    public void conect_device(String adres) {
        BluetoothDevice devices = adapter.getRemoteDevice(adres);
        try {
            btSocket = devices.createRfcommSocketToServiceRecord(MY_UUID);}
        catch (IOException e)
        {
            Log.e("not ready device!!!","not ready device device!!!");}
        try{
            btSocket.connect();}
        catch (IOException e)
        {
            Log.e("not conect to device!!!","!!!"+e);
           try{
                btSocket.close();
            } catch(IOException closeException){}
        }
        try {
            stream_output = btSocket.getOutputStream();
            stream_input=btSocket.getInputStream();}
        catch (Exception e){
            Log.e("failed","input Stream!!!");
        }
    }


    public void send_data(String message)
    {
        byte[] msgBuffer = message.getBytes();
        Log.e("...transmitem date: " + message, "...");
        try {
                stream_output.write(msgBuffer);
        } catch (IOException e) {
            Log.e("not send data!!!","");
        }
    }


    public void get_data()
    {
        Log.e("date primite","input date!!");
        byte[] buffer=new byte[1024];
        int val=0;

        while (true) {
            try {
                val=stream_input.read(buffer);
                val+=0;
                Log.e("value"+val,"");
                //if(val>0)pod4.sendEmptyMessage(val);
            } catch (IOException e) {
                Log.e("not input data!!!", "");
                break;
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            btSocket.close();
        }
        catch(Exception e){}
        Log.e("onDestroy ","activity_send_data1!!");
    }

}