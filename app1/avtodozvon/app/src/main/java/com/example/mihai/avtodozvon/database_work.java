package com.example.mihai.avtodozvon;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class database_work extends SQLiteOpenHelper
{

    ArrayList<String> lists=new ArrayList<String>();
    ArrayList<String> lists1=new ArrayList<String>();
    ArrayList<String> lists2=new ArrayList<String>();
    public static final String database_name="database_calls1.db";
    public static final String table_name="call_logs";
    public static final String table_name_1="call_history_logs";
    public static final String table_name_1_date="data_history";
    public static final String table_fold="data_fold";
    public static final String table_name_1_data_time="data_time";
    public static final String table_secunde="time_calls";
    public static final String col_1="ID";
    public static final String col_2="number";
    public static final String col_3="time_call";
    public static final String col_4="call_fold";
    public static final String col_5="data";
    public  static final String col_6="time_call_1";

    public String create_table="CREATE TABLE "+table_name+"(ID integer primary key autoincrement, "
            +col_5+" text, "+col_6+" text, "+col_2+" text, "+col_3+" integer, "+col_4+" integer);";

    public String create_table_1="CREATE TABLE "+table_name_1+"(ID integer primary key autoincrement, "
            +table_secunde+" integer, "+table_fold+" text, "+table_name_1_date+" text, "+table_name_1_data_time+" text);";


    public database_work(Context context)
    {
        super(context,database_name,null,1);
        SQLiteDatabase db=this.getWritableDatabase();
        db.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_table);
        db.execSQL(create_table_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_name_1);
        onCreate(db);
    }



    public boolean insertData(String data,String number,String call_time,String call_fold)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_2,number);
        contextValue.put(col_3, "0");
        contextValue.put(col_4, call_fold);
        contextValue.put(col_5, data);
        contextValue.put(col_6, call_time);
        long result=db.insert(table_name,null,contextValue);
        db.close();
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public boolean insertData_1(String data,String number,String secunde,String fold)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(table_name_1_data_time,data);
        contextValue.put(table_name_1_date,number);
        contextValue.put(table_secunde,secunde);
        contextValue.put(table_fold,fold);
        long result=db.insert(table_name_1,null,contextValue);
        db.close();
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    public ArrayList show_data_1()
    {
        int increment=1;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+table_name_1,null);
        while(cursor.moveToNext())
        {
            String dat_tim= cursor.getString(cursor.getColumnIndex(database_work.table_name_1_data_time));
            String dates= cursor.getString(cursor.getColumnIndex(database_work.table_name_1_date));
            String fold=cursor.getString(cursor.getColumnIndex(database_work.table_fold));
            int secunde=cursor.getInt(cursor.getColumnIndex(database_work.table_secunde));

           int sec=secunde-(secunde/3600)*3600-((secunde-(secunde/3600)*3600)/60)*60;
            int min=(secunde-(secunde/3600)*3600)/60;
             int ore=secunde/3600;
            String times=""+ore+":"+min+":"+sec;

            String value=""+increment+" | "+dat_tim+" | "+dates+" | "+times+" | "+fold;
            lists1.add(value);
            ++increment;
        }
        cursor.close();
        db.close();
        return lists1;
    }


    public ArrayList show_data()
    {
        int increment=1;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+table_name,null);
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex(database_work.col_1));
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));
            String time_call=cursor.getString(cursor.getColumnIndex(database_work.col_6));
            int call_fold=cursor.getInt(cursor.getColumnIndex(database_work.col_4));
            String date=cursor.getString(cursor.getColumnIndex(database_work.col_5));
            String value=""+increment+" | "+date+" | "+number+" | "+time_call+" | "+call_fold;
            lists.add(value);
            ++increment;
        }
        cursor.close();
        db.close();
        return lists;
    }

 public ArrayList show_list()
 {
     lists2=(show_data_1());
     lists2.addAll(show_data());
     return lists2;
 }


    public void update_number(String id,String number)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_1,id);
        contextValue.put(col_2, number);
        db.update(table_name, contextValue, "id=?", new String[]{id});
        db.close();
    }



    public void update_call_time(String id,int calltime)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_1,id);
        contextValue.put(col_3,calltime);
        db.update(table_name, contextValue, "id=?", new String[]{id});
        db.close();
    }

    public void update_call_time_1(String id,String calltime)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_1,id);
        contextValue.put(col_6,calltime);
        db.update(table_name, contextValue, "id=?", new String[]{id});
        db.close();
    }

    public void update_call_fold(String id,String callfold)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_1,id);
        contextValue.put(col_4,callfold);
        db.update(table_name, contextValue, "id=?", new String[]{id});
        db.close();
    }

    public void update_date_time(String id,String date)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contextValue=new ContentValues();
        contextValue.put(col_1,id);
        contextValue.put(col_5, date);
        db.update(table_name, contextValue, "id=?", new String[]{id});
        db.close();
    }



    public boolean show_element(String numbers)
    {
        boolean finish=false;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select number from " + table_name,null);
        while(cursor.moveToNext())
        {
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));

            if(numbers.equals(number))
            {
                return true;
            }
            else
            {
                finish=false;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }



    public  void  delete_data(String number)
    {
        String id=""+show_numbers(number);
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_name, "id=?", new String[]{id});
        db.close();
    }

    public  int  delete_data_1(String date_time)
    {
        int row=0;
        while(show_date_1(date_time)!=0)
        {
            String id = "" + show_date_1(date_time);
            SQLiteDatabase db = this.getWritableDatabase();
            Integer id_1=Integer.parseInt(id);
            for(int i=0;i<=id_1;i++) {
                row = db.delete(table_name_1, "id=?", new String[]{""+i});
            }
            db.close();
        }

        return row;
    }



    public int show_date_1(String date_time)
    {
        int finish=0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select ID,data_time from " + table_name_1, null);
        while(cursor.moveToNext())
        {
            String date_tim=cursor.getString(cursor.getColumnIndex(database_work.table_name_1_data_time));
            int id=cursor.getInt(cursor.getColumnIndex(database_work.col_1));
            if(date_time.equals(date_tim))
            {
                return id;
            }
            else
            {
                finish=0;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }



    public int show_numbers(String numbers)
    {
        int finish=0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select ID,number from " + table_name, null);
        while(cursor.moveToNext())
        {
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));
            int id=cursor.getInt(cursor.getColumnIndex(database_work.col_1));
            if(numbers.equals(number))
            {
                return id;
            }
            else
            {
                finish=0;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }

    public int show_call_time(String numbers)
    {
        int finish=0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select time_call,number from " + table_name, null);
        while(cursor.moveToNext())
        {
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));
            int call_time=cursor.getInt(cursor.getColumnIndex(database_work.col_3));
            if(numbers.equals(number))
            {
                return call_time;
            }
            else
            {
                finish=0;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }

    public int show_call_fold(String numbers)
    {
        int finish=0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select call_fold,number from " + table_name, null);
        while(cursor.moveToNext())
        {
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));
            int call_fold=cursor.getInt(cursor.getColumnIndex(database_work.col_4));
            if(numbers.equals(number))
            {
                return call_fold;
            }
            else
            {
                finish=0;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }



    public String show_date_time(String numbers)
    {
        String finish="null";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select data,number from " + table_name, null);
        while(cursor.moveToNext())
        {
            String number=cursor.getString(cursor.getColumnIndex(database_work.col_2));
            String date_time=cursor.getString(cursor.getColumnIndex(database_work.col_5));
            if(numbers.equals(number))
            {
                return date_time;
            }

        }
        cursor.close();
        db.close();
        return finish;
    }

}
