
package com.broadgalaxy.bluz.activity;

import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.persistence.DBHelper;
import com.broadgalaxy.bluz.persistence.IMsg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoxActivity extends Activity {
    public static final int INBOX = 1;

    public static final int OUTBOX = 2;

    public static final int DRAFTBOX = 3;

    /**
     * type: int
     */
    public static final String EXTRA_BOX_TYPE = "box_type";

    private ListView mConversation;

    private int mBoxType;

    protected int mSelectPosition;

    private DBHelper mDB;

    protected long mSelectId;

    protected static final int DIALOG_DELETE = 0;

    private static ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mDB = new DBHelper(this);
        setContentView(R.layout.box);
        Intent intent = getIntent();
        mBoxType = intent.getIntExtra(EXTRA_BOX_TYPE, INBOX);
        String boxLabel = getLabel(mBoxType);

        mConversation = (ListView) findViewById(R.id.conversation);
        TextView footer = new TextView(this);
        footer.setText(boxLabel);
        mConversation.addFooterView(footer);
        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.no_data);
        mConversation.setEmptyView(emptyView);
        mConversation.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectPosition = position;
                mSelectId = id;
                showDialog(DIALOG_DELETE);
            }
        });
        queryDB();
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
            case DIALOG_DELETE:
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
        String where = IMsg.COLUMN_STATUS + "=? android " + IMsg._ID + "=?";;
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
            this(context, R.layout.box_item, data);
        }

        public MessageAdapter(Context context, int resource, List<MessageEntity> objects) {
            super(context, resource, objects);
            mData = objects;
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).id;
        }
        
        

    }

    static class MessageEntity {
        public long id;
        int fromAdd;
        int toAdd;
        String message;
        long timeTick;
        
        public static MessageEntity fromCursor(Cursor c) {
            MessageEntity e = new MessageEntity();
            e.id = (long) c.getInt(c.getColumnIndex(IMsg._ID));
            e.fromAdd = c.getInt(c.getColumnIndex(IMsg.COLUMN_FROM_ADDRESS));
            e.toAdd = c.getInt(c.getColumnIndex(IMsg.COLUMN_DEST_ADDRESS));
            e.timeTick = c.getLong(c.getColumnIndex(IMsg.COLUMN_TIME));
            e.message = c.getString(c.getColumnIndex(IMsg.COLUMN_DATA));
            return e;
        }

    }
}
