package com.example.mihai1.hhhhh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assign extends AppCompatActivity {

    private String adres;
    public TextView view_item1,view_item2;
    public EditText edit_item1,edit_item2;
    public Button but1;
    public Handler pod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        adres=Arhive.getProperty("adres");

        view_item1=(TextView) findViewById(R.id.textView8);
        view_item2=(TextView) findViewById(R.id.textView9);

        edit_item1=(EditText) findViewById(R.id.editText6);
        edit_item2=(EditText) findViewById(R.id.editText7);

        but1=(Button) findViewById(R.id.button6);
        but1.setOnClickListener(clik);

        pod = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    mesage_toast("Insert text into all rows!!!");
                }
                else if(msg.what==2){
                    mesage_toast("Input surname & lastname in Employee!!!");
                }
                else if(msg.what==3){
                    mesage_toast("Employee Does Not Exist!!!");
                    edit_item2.setText("");
                }
                else if(msg.what==4){
                    mesage_toast("Error assign failed!!!");
                    edit_item2.setText("");
                    edit_item1.setText("");
                }
                else if(msg.what==5){
                    mesage_toast("Item assigned!!!");
                    edit_item2.setText("");
                    edit_item1.setText("");
                }
            }
        };

    }

    View.OnClickListener clik=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        get_user_id();
                    } catch (IOException e) {
                        Log.e("nahui","click:"+e);
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        Log.e("zahui","click:"+e);
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };


    public void get_user_id()throws IOException,XmlPullParserException
    {

       if(!edit_item2.getText().toString().equals("") && !edit_item1.getText().toString().equals("")) {

           String name=edit_item2.getText().toString();
           String[] ns=name.split(" ");
           String id="";
           if(ns.length<=1) pod.sendEmptyMessage(2);
               else{
               Log.e("var1 ns[0]:" + ns[0], "var2 ns[1]:" + ns[1]);


               SoapObject request = new SoapObject("http://server.com/", "emp_items3");

               SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
               request.addProperty("name2", ns[0]);
               request.addProperty("surname2", ns[1]);

               soapEnvelope.setOutputSoapObject(request);

               HttpTransportSE transport = new HttpTransportSE(adres);
               transport.call("emp_items3", soapEnvelope);

               SoapObject get = (SoapObject) soapEnvelope.bodyIn;
               try {
                   Log.e("id user:", "id:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(0));
                   id=((SoapObject) get.getProperty(0)).getPropertyAsString(0).toString();
               } catch (Exception e) {
                   Log.e("Eroor Assign:", ":" + e);
                   pod.sendEmptyMessage(3);
               }


               try {
                   add_assigns(id,edit_item1.getText().toString());
               } catch (IOException e) {
                   Log.e("nahui","click:"+e);
                   e.printStackTrace();
               } catch (XmlPullParserException e) {
                   Log.e("zahui","click:"+e);
                   e.printStackTrace();
               }
           }
        }
        else {
           pod.sendEmptyMessage(1);
       }

        //trebu de adaugat un handlerr!!!
        // mesage_toast("Items add in database!!!");
    }


    public void add_assigns(String id,String barcode)throws IOException,XmlPullParserException
    {

                SoapObject request = new SoapObject("http://server.com/", "execute_insert_AssignCont1");

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                request.addProperty("barcode", barcode);
                request.addProperty("id",id);

                soapEnvelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(adres);

        try {
            transport.call("execute_insert_AssignCont1", soapEnvelope);
          } catch (Exception e) {
            Log.e("Eroor add_Assign:", ":" + e);
            pod.sendEmptyMessage(4);
        }
        pod.sendEmptyMessage(5);

                /*SoapObject get = (SoapObject) soapEnvelope.bodyIn;
                try {
                    Log.e("id user:", "id:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(0));
                } catch (Exception e) {
                    Log.e("Eroor Assign:", ":" + e);
                    pod.sendEmptyMessage(3);
                }*/

    }


    public void mesage_toast(String mesage)
    {
        Context context = getApplicationContext();
        CharSequence text = "Mesage:"+mesage;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
