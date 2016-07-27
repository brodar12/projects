package com.example.mihai.avtodozvon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class list_activity extends AppCompatActivity {

    ArrayList<String> list=new ArrayList<String>();
    AlertDialog.Builder mesage,mesage1;
    AlertDialog alert;
    ListView listView;
    Button button3,button4;
    EditText editText;
    database_work myDb;
    Date dat=new Date();
    SimpleDateFormat fr;
    boolean stare=false,decision;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //initierea a tuturor elementelor din activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
         setTitle("List numbers");

        myDb=new database_work(this);

        button3=(Button) findViewById(R.id.button3);
        button4=(Button) findViewById(R.id.button4);
        editText=(EditText) findViewById(R.id.editText);
        listView=(ListView) findViewById(R.id.listView);

       //citim din fisier numerele
        citire_fisier();




        //initializam si inscrim numerele in Listview
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,list);
        listView.setAdapter(adapter);


        //butonul add number
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numer = editText.getText().toString();

                //controlam daca a fost introdus vriu caracter in edittext
                if (numer.length() > 0) {
                    //controlam daca numarul introdus nu se repete
                    fr=new SimpleDateFormat("dd.MM.yyyy");
                    String date_1=""+fr.format(dat);
                    if (comparare_elements() == false) {
                        myDb.insertData(date_1,editText.getText().toString(), "00:00:00", "0");
                        Date_transmise.acces4=true;
                        list.add(editText.getText().toString());
                        salvare_fisier(editText.getText().toString(), 1);
                        editText.setText("");
                        adapter.notifyDataSetChanged();
                    } else {
                        //se executa cind numarul introdus se repete
                        mesage("Se repeta numarul:"+editText.getText().toString());
                        editText.setText("");
                    }
                } else {
                    //se executa cint nimic nu introdus in textview
                    mesage("Introduceti un numar!!!!");
                    editText.setText("");
                }
            }
        });

        //cind se apasa pe un element in listview
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stare == true)
                {
                    myDb.delete_data(list.get(index));
                    mesage("Se sterge numarul:" + list.get(index));
                        list.remove(index);
                        deleteFile("number.txt");
                        salvare_fisier(editText.getText().toString(), 0);
                        salvare_fisier(editText.getText().toString(), 2);
                        listView.clearChoices();
                        adapter.notifyDataSetChanged();
                        stare = false;
                    }
                else
                {
                     mesage=new AlertDialog.Builder(list_activity.this);
                    mesage.setTitle("Alert!!!!")
                            .setMessage("Item is not selected!!!")
                            .setCancelable(false)
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                           AlertDialog alert=mesage.create();
                           alert.show();
                }
            }
        });

//butonul delete elements
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (stare != true)
            {
                index = position;
                stare = true;
            }
            else
            {
                listView.setItemChecked(position,false);
                if(position==index)
                {
                    index=-1;
                    stare=false;
                }
            }


        }
    });


    }



    public void citire_fisier()
    {
        try {
            InputStream citire = openFileInput("number.txt");
            InputStreamReader sr = new InputStreamReader(citire);
            BufferedReader reader = new BufferedReader(sr);
            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = reader.readLine()) != null)
            {
                list.add(str);
            }
            citire.close();
        }
        catch(Throwable t)
        {

        }
    }



    public void salvare_fisier(String number_param,int alege)
    {
        try {

            OutputStream output;
            OutputStreamWriter osw;
            switch(alege)
            {
                case 0:
                    output = openFileOutput("number.txt",MODE_PRIVATE);
                    osw = new OutputStreamWriter(output);
                    osw.close();
                    break;
                case 1:
                    output = openFileOutput("number.txt", MODE_APPEND);
                    osw = new OutputStreamWriter(output);
                    osw.write(number_param+"\n");
                    osw.close();
                    break;
                case 2:
                    output = openFileOutput("number.txt", MODE_APPEND);
                    osw = new OutputStreamWriter(output);
                    for(int i=0;i<list.size();i++)osw.write(list.get(i)+"\n");
                    osw.close();
                    break;

            }
        }
        catch(Throwable t)
        {

        }
    }


    public boolean comparare_elements()
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).equals(editText.getText().toString()))return true;
        }
      return false;
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
