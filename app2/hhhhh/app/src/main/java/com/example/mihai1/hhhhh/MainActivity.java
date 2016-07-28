package com.example.mihai1.hhhhh;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
public class MainActivity extends AppCompatActivity {

    Button but1,but2;
    ListView list;
    EditText name,barcode,brand,model,serial;
    List<String> ob=new ArrayList<>();
    List<Type> types=new ArrayList<>();
    IntentIntegrator scan;
    String id_item="eorre";
    int vals=0;
    //adresa trebu de schimbat cu acea a web-service si in search
    private String adres;
    public Handler pod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but1=(Button) findViewById(R.id.button);
        but2=(Button) findViewById(R.id.button2);
        list=(ListView) findViewById(R.id.listView);
        name=(EditText) findViewById(R.id.editText);
        barcode=(EditText) findViewById(R.id.editText2);
        brand=(EditText) findViewById(R.id.editText3);
        model=(EditText) findViewById(R.id.editText4);
        serial=(EditText) findViewById(R.id.editText5);

        adres=Arhive.getProperty("adres");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    types=get_all_type();
                    for(int i=0;i<types.size();i++)
                    {
                        ob.add(types.get(i).getType_name());
                        Log.e("elements","val:"+types.get(i).getType_name());
                    }
                } catch (IOException e) {
                    Log.e("nahui","click:"+e);
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    Log.e("zahui","click:"+e);
                    e.printStackTrace();
                }
            }
        }).start();

        scan=new IntentIntegrator(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,ob);


        pod = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    mesage_toast("data add succes!!!");
                    name.setText("");
                    barcode.setText("");
                    brand.setText("");
                    model.setText("");
                    serial.setText("");
                }
                else if(msg.what == 2)
                {
                    mesage_toast("error add succes!!!");
                    name.setText("");
                    barcode.setText("");
                    brand.setText("");
                    model.setText("");
                    serial.setText("");
                }
                else if(msg.what == 3)
                {
                    mesage_toast("Insert text into all rows!!!");
                }

            }
        };


        list.setAdapter(adapter);
        but1.setOnClickListener(click);
        but2.setOnClickListener(click);
        list.setOnItemClickListener(item_click);

    }


    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button){
                Log.e("go inainte", "runs1 threads!!!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            add_users();
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
            else if(v.getId()==R.id.button2){
                scan.initiateScan();
            }

        }
    };

    AdapterView.OnItemClickListener item_click=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           // name.setText(""+ob.get(position));
            for(int i=0;i<types.size();i++){
                if(ob.get(position).equals(types.get(i).getType_name())) id_item=types.get(i).getType_id();
            }

        }
    };

    public void add_users()throws IOException,XmlPullParserException
    {

        if(!name.getText().toString().equals("") && !barcode.getText().toString().equals("") && !brand.getText().toString().equals("")
                && !model.getText().toString().equals("") && !serial.getText().toString().equals("") ) {

            SoapObject request = new SoapObject("http://server.com/", "addItem1");
            request.addProperty("item_name", name.getText().toString());
            request.addProperty("item_barcode", barcode.getText().toString());
            request.addProperty("item_brand", brand.getText().toString());
            request.addProperty("item_model", model.getText().toString());
            request.addProperty("item_serial", serial.getText().toString());
            request.addProperty("type_id", id_item.toString());

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(adres);
            if (!id_item.equals("eorre")) {
                transport.call("addItem1", soapEnvelope);
                SoapObject get = (SoapObject) soapEnvelope.bodyIn;
                String val = get.getProperty(0).toString();
                if (val.equals("true")) pod.sendEmptyMessage(1);
                else pod.sendEmptyMessage(2);

                Log.e("Return din add_users", "rez:" + val);
            }
        }
        else  pod.sendEmptyMessage(3);

        //trebu de adaugat un handlerr!!!
       // mesage_toast("Items add in database!!!");
    }



    public List<Type> get_all_type()throws IOException,XmlPullParserException
    {

        SoapObject request = new SoapObject("http://server.com/","getAllTypes");

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(request);

        HttpTransportSE transport = new HttpTransportSE(adres);
        transport.call("getAllTypes", soapEnvelope);

        List<Type> object1=new ArrayList<>();
        SoapObject get=(SoapObject)soapEnvelope.bodyIn;
      // vals=((SoapObject)get.getProperty(0)).getPropertyAsString(1).length();
        //for(int i=0;i<vals;i++)
        int i=0,incre=0;
        while(i==0)
        {
            Log.e("zdarova:" + incre, "rez:");
            try{
            Type m=new Type();
            m.setType_id(((SoapObject) get.getProperty(incre)).getPropertyAsString(0));
            m.setType_name(((SoapObject) get.getProperty(incre)).getPropertyAsString(1));
            object1.add(m);
            }catch(Exception e){
                i=1;
                Log.e("exceptie","ex:"+e);
                break;
            }
            ++incre;
        }
      return object1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();

                //Log.e("scan result:","res:" + scanContent.toString());
                barcode.setText(""+scanContent.toString());
            } else {
                super.onActivityResult(requestCode, resultCode, intent);
            }
        }
        catch(Exception e){}
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
