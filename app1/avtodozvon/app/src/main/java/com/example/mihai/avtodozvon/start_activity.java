package com.example.mihai.avtodozvon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class start_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    String numer_thread;
    database_work  myDd;
    final Executor eS = Executors.newSingleThreadExecutor();
    AlertDialog.Builder mesages;
    AlertDialog alert;
    Timer timer=new Timer();
    timer_task task=new timer_task();
    timer_task1 task1=new timer_task1();
    ArrayList<String> num=new ArrayList<String>();
    ArrayList<String> parametri=new ArrayList<String>();
    ArrayList<Integer> randomss=new ArrayList<Integer>();
    Button button;
    Timers tim;
    Timers1 tim1;
    int  time_call_min,time_call_max,time_all_call_max,
            time_all_call_min,time_pause,time_pause_1,call_fold,time_end_day_min,time_end_day_max,
            index=0,random1,random2,random3,random4,vall,vall1,time_date,
            stare_trecere=0,incremenst=0,index1=0;
    long contor=0;
    TextView textView5,textView6,textView7,textView12;
    final static int URL_LOADER=0;
    boolean acces=false,acces1=false,acces2=true,acces3=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);

        setTitle("Start activity");
        myDd=new database_work(this);
        getLoaderManager().initLoader(URL_LOADER, null, this);


        button = (Button) findViewById(R.id.button);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 =  (TextView) findViewById(R.id.textView7);
        textView12 =(TextView) findViewById(R.id.textView12);


        citire_fisier("number.txt", 1);
        if(Date_transmise.valoare>0)
        {
            alert_mesage_base("Input in menu setings all dates!!!!");
        }
        else if(num.size()==0)
        {
            alert_mesage_base("Input in menu list numbers!!!!");
        }
        else if(Date_transmise.valoare==0 && num.size()!=0)
        {

            citire_fisier("setings2.txt", 0);
            time_call_min=Integer.parseInt(parametri.get(0));
            time_call_max=Integer.parseInt(parametri.get(1));
            time_all_call_min = Integer.parseInt(parametri.get(2));
            time_all_call_max = Integer.parseInt(parametri.get(3));
            time_pause = Integer.parseInt(parametri.get(4));
            time_pause_1 = Integer.parseInt(parametri.get(6));
            call_fold = Integer.parseInt(parametri.get(5));
            time_end_day_min=Integer.parseInt(parametri.get(7));
            time_end_day_max=Integer.parseInt(parametri.get(8));



            random1=time_call_min+(int)(Math.random()*((time_call_max-time_call_min)+1));
            random2=time_pause+(int)(Math.random()*(( time_pause_1-time_pause)+1));

            if(Date_transmise.acces4!=false)
            {
                deleteFile("randoms.txt");
                randomss.removeAll(randomss);
                for (int i = 0; i < num.size(); i++) {
                    random3 = time_all_call_min + (int) (Math.random() * ((time_all_call_max - time_all_call_min) + 1));
                    randomss.add(random3);
                    salvare_fisier(random3);
                }
                Date_transmise.acces4=false;
            }
            else
            {
                randomss.removeAll(randomss);
                citire_fisier();
            }


            textView7.setText(""+random1);
            tim = new Timers(((random1) * 1000), 1000);
            tim1 = new Timers1((random2 * 1000), 1000);
            timer.schedule(task, 1000, 5000);
            timer.schedule(task1, 1000, 1000);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tim.start_timer();
                button.setEnabled(false);
            }
        });

    }



    public int endCall()
    {
        Runtime runtime=Runtime.getRuntime();
        int nResp=0;
        try
        {
            runtime.exec("service call phone 5 \n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return nResp;
    }




    public void start_Call()
    {

        if(myDd.show_call_fold(num.get(index))<call_fold && myDd.show_call_time(num.get(index))<randomss.get(index))
        {
            Intent coll = new Intent(Intent.ACTION_CALL);
            coll.setData(Uri.parse("tel:" + num.get(index)));
            startActivity(coll);
            acces1=true;

        }
        else
        {
            for(int i=0;i<num.size();i++)
            {
                if(myDd.show_call_fold(num.get(i))<call_fold && myDd.show_call_time(num.get(i))<randomss.get(i))
                {
                    index=i;
                    index1=index;
                    time_date=myDd.show_call_time(num.get(i));
                    ++incremenst;
                    Intent coll = new Intent(Intent.ACTION_CALL);
                    coll.setData(Uri.parse("tel:" + num.get(i)));
                    startActivity(coll);
                    acces1=true;
                }
            }

            if(incremenst==0)
            {
                tim.stop_timer();
                if(acces3!=false)
                {
                    tim1.stop_timer();
                    acces3=false;
                }
                acces1=false;
            }

        }
        incremenst=0;
        ++index;

    }





    class Timers extends CountDownTimer {


        public Timers(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            textView5.setText("timer:"+millisUntilFinished/1000);
            ++contor;
        }

        @Override
        public void onFinish()
        {

            eS.execute(new Runnable() {
                @Override
                public void run() {
                    endCall();
                }
            });
            stare_trecere=2;
            acces2=false;
            random1=time_call_min+(int)(Math.random()*((time_call_max-time_call_min)+1));
            random2=time_pause+(int)(Math.random()*(( time_pause_1-time_pause)+1));
            textView7.setText(""+random1);
            tim=new Timers(((random1) * 1000), 1000);
            tim1 = new Timers1((random2 * 1000), 1000);
            tim1.start_timer();
            contor=0;
            acces1=false;
        }

        public void start_timer()
        {
            stare_trecere=1;
            acces=true;
            index1=index;
            time_date=myDd.show_call_time(num.get(index));
            super.start();
            start_Call();

        }

        public  void stop_timer()
        {
            super.cancel();
            eS.execute(new Runnable() {
                @Override
                public void run() {
                    endCall();
                }
            });
        }

        public  void stop_timer_1()
        {
            super.cancel();
        }

        public  void stop_timer_primary()
        {
            acces2=false;
            super.cancel();
            eS.execute(new Runnable() {
                @Override
                public void run() {
                    endCall();
                }
            });
        }

        public  void stop_timer_1_second()
        {
            acces2=false;
            super.cancel();
        }

    }







    public void citire_fisier( String name_file,int stare)
    {
        try {
            if(stare==0)
            {
                InputStream citire = openFileInput(name_file);
                InputStreamReader sr = new InputStreamReader(citire);
                BufferedReader reader = new BufferedReader(sr);
                String str;
                StringBuilder builder = new StringBuilder();

                while ((str = reader.readLine()) != null) {
                    parametri.add(str);
                }
                citire.close();

            }
            else if(stare==1)
            {
                InputStream citire = openFileInput(name_file);
                InputStreamReader sr = new InputStreamReader(citire);
                BufferedReader reader = new BufferedReader(sr);
                String str;
                StringBuilder builder = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    num.add(str);
                }
                citire.close();
            }
        }
        catch(Throwable t)
        {

        }
    }



    public void salvare_fisier(int rand)
    {
        try {

            OutputStream output;
            OutputStreamWriter osw;

            output = openFileOutput("randoms.txt", MODE_APPEND);
            osw = new OutputStreamWriter(output);
            osw.write(""+rand+"\n");
            osw.close();
        }
        catch(Throwable t)
        {

        }
    }

    public void citire_fisier()
    {
        try {
            InputStream citire = openFileInput("randoms.txt");
            InputStreamReader sr = new InputStreamReader(citire);
            BufferedReader reader = new BufferedReader(sr);
            String str;
            int rand=0;
            StringBuilder builder = new StringBuilder();
            while ((str = reader.readLine()) != null)
            {
                rand=Integer.parseInt(str);
                randomss.add(rand);
            }
            citire.close();
        }
        catch(Throwable t)
        {

        }
    }





    //primim datele dupa ce sa facut apelu
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        switch(id)
        {
            case URL_LOADER:
                return new CursorLoader(this,CallLog.Calls.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }




    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if(acces!=false) {
            int number = data.getColumnIndex(CallLog.Calls.NUMBER);
            int type = data.getColumnIndex(CallLog.Calls.TYPE);
            int duration = data.getColumnIndex(CallLog.Calls.DURATION);

            data.moveToFirst();
            String phNumber = data.getString(number);
            String callType = data.getString(type);
            String callDuration = data.getString(duration);
            String dir = null;

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;
            }

            vall1 = Integer.parseInt(callDuration);
            numer_thread=phNumber;

            if (myDd.show_element(phNumber) == true)
            {

                vall = myDd.show_call_time(phNumber);
                if (vall1 != 0)
                {
                    int sec=0,min=0,ore=0;
                    vall = vall + vall1;
                    myDd.update_call_time("" + myDd.show_numbers(phNumber), vall);
                    sec=vall-(vall/3600)*3600-((vall-(vall/3600)*3600)/60)*60;
                    min=(vall-(vall/3600)*3600)/60;
                    ore=vall/3600;
                    String times=""+ore+":"+min+":"+sec;
                    myDd.update_call_time_1(""+myDd.show_numbers(phNumber),times);
                }
                else
                {
                    int call_folds=myDd.show_call_fold(phNumber);
                    ++call_folds;
                    myDd.update_call_fold(""+myDd.show_numbers(phNumber),""+call_folds);
                }

            }
            if(acces2!=false) {
                tim.stop_timer_1();
                tim.onFinish();
                acces2=true;
            }



        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //sa terminat lucru cu datele after call








    //timerul 2 care raspunde de finalizarea sunetului
    class Timers1 extends CountDownTimer
    {

        public Timers1(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            textView6.setText("timer1:" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish()
        {
            acces3=true;
            stare_trecere=4;
            try {
                num.get(index);
            } catch (Exception e) {
                index = 0;
            }
            alert.cancel();
            tim.start_timer();
            acces3=false;
        }

        public void start_timer()
        {
            acces3=true;
            stare_trecere=3;
            alert_mesage(" Finish all call !!!!!");
            super.start();
        }

        public  void stop_timer()
        {

            try {
                num.get(index);
            } catch (Exception e) {
                index = 0;
            }
            super.cancel();
        }
        public void stop_timer_1()
        {
            alert.cancel();
            super.cancel();
        }
        public void stop_timer_2()
        {
            super.cancel();
        }

        public void stop_timer_1_aditional()
        {
            alert.cancel();
            acces2=false;
            super.cancel();
        }
        public void stop_timer_2_aditional()
        {
            acces2=false;
            super.cancel();
        }

    }


    //musajele de avertizare
    public void alert_mesage(String mesage)
    {
        mesages=new AlertDialog.Builder(this);
        mesages.setTitle("Alert!!!!")
                .setMessage(""+mesage)
                .setCancelable(false)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tim1.stop_timer();
                        button.setEnabled(true);
                        dialog.cancel();
                    }
                });
        alert=mesages.create();
        alert.show();
    }

    public void alert_mesage_base(String mesage)
    {
        mesages=new AlertDialog.Builder(this);
        mesages.setTitle("Alert!!!!")
                .setMessage(""+mesage)
                .setCancelable(false)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        button.setEnabled(false);
                        dialog.cancel();
                    }
                });
        alert=mesages.create();
        alert.show();

    }


    class timer_task extends TimerTask
    {

        @Override
        public void run()
        {
            Date dat=new Date();
            SimpleDateFormat fr=new SimpleDateFormat("dd.MM.yyyy");
            String date_1=""+fr.format(dat);

            if(myDd.show_date_time(num.get(0)).equals("null")) {}
            else {

                if (myDd.show_date_time(num.get(0)).equals(date_1)) {

                }
                else
                {

                    switch (stare_trecere)
                    {
                        case 1:
                            tim.stop_timer_primary();
                            break;
                        case 2:
                            tim.stop_timer_1_second();
                            break;
                        case 3:
                            tim1.stop_timer_1_aditional();
                            break;
                        case 4:
                            tim1.stop_timer_2_aditional();
                            break;
                    }

                    try
                    {
                        TimeUnit.SECONDS.sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                    }


                    for(int i=0;i<num.size();i++)
                    {
                        int call_time=myDd.show_call_time(num.get(i));
                        int call_fold=myDd.show_call_fold(num.get(i));
                        String date_time=myDd.show_date_time(num.get(i));
                        myDd.insertData_1(date_time,num.get(i),""+call_time,""+call_fold);

                    }

                    for (int i = 0; i < num.size(); i++)
                    {
                        myDd.update_call_time("" + myDd.show_numbers(num.get(i)), 0);
                        myDd.update_call_fold("" + myDd.show_numbers(num.get(i)), "0");
                        myDd.update_date_time("" + myDd.show_numbers(num.get(i)), date_1);
                        myDd.update_call_time_1("" + myDd.show_numbers(num.get(i)), "0:0:0");
                    }

                    // getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);

                    random4=time_end_day_min + (int) (Math.random() * ((time_end_day_max - time_end_day_min) + 1));
                    try
                    {
                        TimeUnit.SECONDS.sleep(random4);
                    }
                    catch (InterruptedException e)
                    {

                    }

                    deleteFile("randoms.txt");
                    randomss.removeAll(randomss);
                    for (int i = 0; i < num.size(); i++)
                    {
                        random3 = time_all_call_min + (int) (Math.random() * ((time_all_call_max - time_all_call_min) + 1));
                        randomss.add(random3);
                        salvare_fisier(random3);
                    }

                    try {
                        num.get(index);
                    } catch (Exception e) {
                        index = 0;
                    }
                    acces2=true;
                    tim.start_timer();

                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    //  alert_mesage_base("sa schimbat data!!!!");
                    Date dat = new Date();
                    SimpleDateFormat fr = new SimpleDateFormat("dd.MM.yyyy");
                    String date_1 = "" + fr.format(dat);
                    textView12.setText("" + date_1);

                }
            });

        }

    }



    class timer_task1 extends TimerTask
    {
        @Override
        public void run()
        {


            if(acces1!=false)
            {

                if (time_date+contor-10 >=randomss.get(index1))
                {
                    eS.execute(new Runnable() {
                        @Override
                        public void run() {
                            endCall();
                        }
                    });
                }


            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }


    }



    @Override
    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();

    }



}







