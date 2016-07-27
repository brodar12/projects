package com.example.mihai.avtodozvon;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.SimpleCursorAdapter;

/*public class call_log implements LoaderManager.LoaderCallbacks<Cursor>
{

   SimpleCursorAdapter adapter;
private static final int LOADER_ID=0;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        switch(id)
        case URL_LOADER:
    return new CursorLoader(this, CallLog.Calls.CONTENT_URI,null,null,null,null);
        default:
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
*/