package com.example.mihai1.test;

import android.bluetooth.BluetoothSocket;

import java.io.Serializable;

/**
 * Created by Mihai1 on 15.03.2016.
 */
public class send_objects implements Serializable{

    protected BluetoothSocket socket;
    protected String mesage;

    public send_objects(BluetoothSocket sockets )
    {
        this.socket=sockets;
    }
    public send_objects(String m){this.mesage=m;}

    public  BluetoothSocket get_object()
    {
        return socket;
    }
    public String get_mesage(){return mesage;}
}
