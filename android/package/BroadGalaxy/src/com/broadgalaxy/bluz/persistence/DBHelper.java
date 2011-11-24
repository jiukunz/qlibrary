package com.broadgalaxy.bluz.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "broadgalaxy";
    public static final String MSG_TABLE_NAME= "msg";
    /**
     * ChangeLog
     */
    private static int mVersion = 1;
    
    public DBHelper(Context context) {
        this(context, DB_NAME , null, mVersion);
    }

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DB_NAME , null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createMsgTable = "create table " + MSG_TABLE_NAME + "(" +
                                      IMsg._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      IMsg.COLUMN_FROM_ADDRESS + " TEXT NOT NULL, " + 
                                      IMsg.COLUMN_DEST_ADDRESS + " TEXT NOT NULL, " + 
                                      IMsg.COLUMN_DATA + " TEXT NOT NULL, " + 
                                      IMsg.COLUMN_DATA_LEN + " INTEGER NOT NULL, " + 
                                      IMsg.COLUMN_TIME + " LONG, " + 
                                      IMsg.COLUMN_STATUS + " INTEGER DEFAULT 0 " + 
                                      ")" + 
                                      "";
        db.execSQL(createMsgTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
