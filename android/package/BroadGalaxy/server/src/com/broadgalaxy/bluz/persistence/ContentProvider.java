package com.broadgalaxy.bluz.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContentProvider 
//extends android.content.ContentProvider 
{
    
    private static ContentProvider sInstance;
    private DBHelper mDBHelper;


    private ContentProvider(Context context){
        mDBHelper = new DBHelper(context);
    }
    
    public static ContentProvider getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new ContentProvider(context);
        }
        
        return sInstance;
    }
 
    public boolean onCreate() {
        return true;
    }


    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        return db.query(DBHelper.MSG_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }


    public String getType(Uri uri) {
        return null;
    }


    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id =  db.insert(DBHelper.MSG_TABLE_NAME, null, values);
        
        return uri.withAppendedPath(uri, id + "");
    }


    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.delete(DBHelper.MSG_TABLE_NAME, selection, selectionArgs);
    }


    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.update(DBHelper.MSG_TABLE_NAME, values, selection, selectionArgs);
    }

}
