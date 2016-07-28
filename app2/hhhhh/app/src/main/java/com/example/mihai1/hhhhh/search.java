package com.example.mihai1.hhhhh;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class search extends AppCompatActivity {

    Button but;
    TextView text,text1;
    IntentIntegrator scan;
    String barcode,name,surname;
    public Handler pod;
    //adresa trebu de schimbat cu acea a web-service
  private String adres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        but=(Button) findViewById(R.id.button3);
        text=(TextView) findViewById(R.id.textView6);
        text1=(TextView) findViewById(R.id.textView7);


        scan=new IntentIntegrator(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        adres=Arhive.getProperty("adres");

        pod = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    text1.setText("user:"+name+" "+surname);
                }
                else if(msg.what == 2)
                {
                    mesage_toast("Erron user not assgned!!!!");
                }
            }
        };

        but.setOnClickListener(click);
    }


    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button3){
                Log.e("Start buton  search!!", "go!!!");
                scan.initiateScan();
            }
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                text.setText("" + scanContent.toString());
                 barcode=scanContent.toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            search_users(barcode);
                        } catch (IOException e) {
                            Log.e("nah","click:"+e);
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            Log.e("zah","click:"+e);
                            e.printStackTrace();
                        }
                    }
                }).start();

                //Log.e("scan result:","res:" + scanContent.toString());
            } else {
                super.onActivityResult(requestCode, resultCode, intent);
            }
        }
        catch(Exception e){}
    }

    public void search_users(String barcode)throws IOException,XmlPullParserException
    {

        SoapObject request = new SoapObject("http://server.com/", "serch_date_get_1");
        request.addProperty("id", barcode);


        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(request);

        HttpTransportSE transport = new HttpTransportSE(adres);

            transport.call("serch_date_get_1", soapEnvelope);
            SoapObject get=(SoapObject)soapEnvelope.bodyIn;
           // String val= get.getProperty(0).toString();

        try {
          /*  Log.e("Return din search_user", "rez:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(1).toString());
            Log.e("Return din search_user", "rez:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(2).toString());*/
            Log.e("Return din search_user", "rez:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(3).toString());
            Log.e("Return din search_user", "rez:" + ((SoapObject) get.getProperty(0)).getPropertyAsString(4).toString());
            name=((SoapObject) get.getProperty(0)).getPropertyAsString(3).toString().toString();
            surname=((SoapObject) get.getProperty(0)).getPropertyAsString(4).toString().toString();

            pod.sendEmptyMessage(1);

        }catch(Exception e)
        {
            Log.e("Return din search_user","eror"+e);
            pod.sendEmptyMessage(2);
        }
        // mesage_toast("Items add in database!!!");
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
