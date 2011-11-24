
package com.broadgalaxy.bluz.test;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.activity.BoxActivity;
import com.broadgalaxy.bluz.activity.BoxActivity.MessageEntity;
import com.broadgalaxy.bluz.persistence.DBHelper;
import com.broadgalaxy.bluz.persistence.IMsg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import java.util.Random;

public class TestDB extends ApplicationTestCase {
    public TestDB() {
        this(Application.class);
    }

    public TestDB(Class<com.broadgalaxy.bluz.Application> applicationClass) {
        super(applicationClass);
    }

    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
        createApplication();
    }

    public void testInsert() {
        SQLiteDatabase db = new DBHelper(mContext).getWritableDatabase();
        String nullColumnack = null;
        MessageEntity e = new BoxActivity.MessageEntity();
        e.fromAdd = 123 + "";
        e.toAdd = 321 + "";
        e.message = "ooxx" + new Random().nextDouble();
        e.status = IMsg.STATUS_DRAFT;
        ContentValues values = MessageEntity.toValues(e);
        long rawId = db.insert(DBHelper.MSG_TABLE_NAME, nullColumnack, values);
        assertFalse(rawId == -1);
    }

    public void testDelete() {
        SQLiteDatabase db = new DBHelper(mContext).getWritableDatabase();
        String nullColumnack = null;
        MessageEntity e = new BoxActivity.MessageEntity();
        e.fromAdd = "" + 111 + new Random().nextDouble();
        e.toAdd = 321 + "";
        e.message = "ooxx test delete 1";
        e.status = IMsg.STATUS_DRAFT;
        ContentValues values = MessageEntity.toValues(e);
        long rawId = db.insert(DBHelper.MSG_TABLE_NAME, nullColumnack, values);
        assertFalse(rawId == -1);
        String whereClasue = IMsg.COLUMN_FROM_ADDRESS + "=?";
        String[] whereArgs = new String[] {
            e.fromAdd
        };
        rawId = db.delete(DBHelper.MSG_TABLE_NAME, whereClasue, whereArgs);
        assertFalse(rawId == -1);
    }

    public void testUpdate() {
        SQLiteDatabase db = new DBHelper(mContext).getWritableDatabase();
        String nullColumnack = null;
        MessageEntity e = new BoxActivity.MessageEntity();
        e.fromAdd = "" + 11 + new Random().nextDouble() + new Random().nextFloat();
        e.toAdd = 321 + "";
        e.message = "ooxx test delete 1";
        e.status = IMsg.STATUS_DRAFT;
        ContentValues values = MessageEntity.toValues(e);
        long rawId = db.insert(DBHelper.MSG_TABLE_NAME, nullColumnack, values);
        assertFalse(rawId == -1);
        String whereClause = IMsg.COLUMN_FROM_ADDRESS + "=?";
        String[] whereArgs = new String[] {
            e.fromAdd
        };
        
        int newStatus = IMsg.STATUS_SENT;
        values.put(IMsg.COLUMN_STATUS, newStatus);
        rawId = db.update(DBHelper.MSG_TABLE_NAME, values, whereClause, whereArgs);
        assertFalse(rawId == -1);
        String selection = whereClause;
        String[] selectionArgs = whereArgs;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(DBHelper.MSG_TABLE_NAME, null, selection, selectionArgs, groupBy,
                having, orderBy);
        assertTrue(c.getCount() == 1);
        assertTrue(c.moveToFirst());
        int updatedStatus = c.getInt(c.getColumnIndex(IMsg.COLUMN_STATUS));
        assertTrue(updatedStatus == newStatus);
    }

}
