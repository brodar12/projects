package com.example.mihai1.hhhhh;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mihai1 on 02.05.2016.
 */
public class Arhive {
    public static final String STORAGE_NAME = "StorageAdres";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init( Context cntxt ){
        context = cntxt;
    }

    private static void init(){
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE );
        editor = settings.edit();
    }
    private static void init1(){
        settings =context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE );
        editor = settings.edit();
    }

    public static void addProperty( String name, String value ){
        if( settings == null ){
            init1();
        }
        editor.putString( name, value );
        editor.commit();
    }

    public static String getProperty( String name ){
        if( settings == null ){
            init();
        }
        return settings.getString( name, null );
    }
}
