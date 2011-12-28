
package com.broadgalaxy.bluz.activity;

import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.persistence.DBHelper;
import com.broadgalaxy.bluz.persistence.IMsg;
import com.broadgalaxy.util.Log;
import com.broadgalaxy.util.ReflecUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoxActivity extends Activity {
    public static final int TYPE_INBOX = 1;
    public static final int TYPE_OUTBOX = 2;
    public static final int TYPE_DRAFTBOX = 3;

    /**
     * type: int
     */
    public static final String EXTRA_BOX_TYPE = "box_type";

    private ListView mConversation;

    private int mBoxType;

    protected int mSelectPosition;

    private DBHelper mDB;

    protected long mSelectId;
    private String TAG = BoxActivity.class.getSimpleName();
    private TextView mTitle;

    protected static final int DIALOG_ITEM_DELETE = 0;

    private static ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mDB = new DBHelper(this);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.box);
        
        Intent intent = getIntent();
        mBoxType = intent.getIntExtra(EXTRA_BOX_TYPE, TYPE_INBOX);
        Log.d(TAG , "boxType: " + toType(mBoxType));
        String boxLabel = getLabel(mBoxType); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.broadgalaxy);
        mTitle = (TextView) findViewById(R.id.title_right_text);
        mTitle.setText(boxLabel);

        mConversation = (ListView) findViewById(R.id.conversation);
        TextView header = new TextView(this);
        header.setText(boxLabel);
        mConversation.addHeaderView(header);
        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.no_data);
        mConversation.setEmptyView(emptyView);
        mConversation.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectPosition = position;
                mSelectId = id;
                showDialog(DIALOG_ITEM_DELETE);
            }
        });
        queryDB();
    }

    private String toType(int mBoxType) {
        String desc = "type: " + mBoxType;
        desc += "\tdesc: " + ReflecUtil.fieldName(getClass(), "TYPE_", mBoxType);
        return desc;
    }

    private void queryDB() {
        List<MessageEntity> messages = new ArrayList<MessageEntity>();
        fillData(messages);
        mAdapter = new MessageAdapter(this, messages);
        mConversation.setAdapter(mAdapter);
    }
    
    private void fillData(List<MessageEntity> messages) {
        String selection = IMsg.COLUMN_STATUS + "=?";
        String[] selectArgs = new String[]{mBoxType + ""};
        Cursor c = mDB.getReadableDatabase().query(DBHelper.MSG_TABLE_NAME, null, selection, selectArgs, null,
                null, null);
        if (c.moveToFirst()) {
            while(!c.isAfterLast()) {
                messages.add(MessageEntity.fromCursor(c));
                c.moveToNext();
            }
        }
    }

    private String getLabel(int boxType) {
        String label = "";
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.navigation);
        label = array[boxType];
        return label;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ITEM_DELETE:                
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dia_delete_msg_title);
                builder.setMessage(R.string.dia_delete_msg_message);
                builder.setPositiveButton(android.R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedMessage();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return builder.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.box_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_thread:
                deletedAll();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void deleteSelectedMessage() {
        String where = IMsg.COLUMN_STATUS + "=? and " + IMsg._ID + "=?";;
        String[] whereArgs = new String[]{mBoxType + "", mSelectId + ""};
        mDB.getWritableDatabase().delete(DBHelper.MSG_TABLE_NAME, where, whereArgs);
        
        queryDB();
    }

    private void deletedAll() {
        String where = IMsg.COLUMN_STATUS + "=?";;
        String[] whereArgs = new String[]{mBoxType + ""};
        mDB.getWritableDatabase().delete(DBHelper.MSG_TABLE_NAME, where, whereArgs);
        queryDB();
    }

    class MessageAdapter extends ArrayAdapter<MessageEntity> {
        private List<MessageEntity> mData;

        public MessageAdapter(final Context context, final List<MessageEntity> data) {
            super(context, R.layout.box_item, R.id.message, data);
            mData = data;
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).id;
        }
    }

    public static class MessageEntity {
        public long id;
        public String fromAdd;
        public String toAdd;
        public String message;
        private int messageLen;
        public long timeTick;
        public int status;
        
        public static MessageEntity fromCursor(Cursor c) {
            MessageEntity e = new MessageEntity();
            e.id = (long) c.getInt(c.getColumnIndex(IMsg._ID));
            e.fromAdd = c.getString(c.getColumnIndex(IMsg.COLUMN_FROM_ADDRESS));
            e.toAdd = c.getString(c.getColumnIndex(IMsg.COLUMN_DEST_ADDRESS));
            e.timeTick = c.getLong(c.getColumnIndex(IMsg.COLUMN_TIME));
            e.message = c.getString(c.getColumnIndex(IMsg.COLUMN_DATA));
            e.messageLen = c.getInt(c.getColumnIndex(IMsg.COLUMN_DATA_LEN));
            e.status = c.getInt(c.getColumnIndex(IMsg.COLUMN_STATUS));
            return e;
        }
        
        public static ContentValues toValues(MessageEntity e){
            ContentValues v = new ContentValues();
            v.put(IMsg.COLUMN_FROM_ADDRESS, e.fromAdd);
            v.put(IMsg.COLUMN_DEST_ADDRESS, e.toAdd);
            v.put(IMsg.COLUMN_DATA, e.message);
            v.put(IMsg.COLUMN_DATA_LEN, e.messageLen);
            v.put(IMsg.COLUMN_TIME, System.currentTimeMillis());
            v.put(IMsg.COLUMN_STATUS, e.status);
            return v;
        }

        @Override
        public String toString() {
            return message;
        }

        
    }
}
