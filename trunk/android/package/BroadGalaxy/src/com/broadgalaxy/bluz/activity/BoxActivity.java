package com.broadgalaxy.bluz.activity;

import com.broadgalaxy.bluz.R;
import com.broadgalaxy.bluz.persistence.ContentProvider;
import com.broadgalaxy.bluz.persistence.DBHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class BoxActivity extends Activity {
    public static final int INBOX = 1;
    public static final int OUTBOX = 2;
    public static final int DRAFTBOX = 3;
    public static final String EXTRA_BOX_TYPE = "box_type";
    private ListView mConversation;
    private int mBoxType;
    protected int mSelectPosition;
    private DBHelper mDB;
    protected static final int DIALOG_DELETE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.box);
        Intent intent = getIntent();
        mBoxType = intent .getIntExtra(EXTRA_BOX_TYPE, INBOX);
        String boxLabel = getLabel(mBoxType);
        
        mConversation = (ListView) findViewById(R.id.conversation);
        TextView footer = new TextView(this);
        footer.setText(boxLabel);
        mConversation.addFooterView(footer);   
        mConversation.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectPosition = position;
                showDialog(DIALOG_DELETE);
            }});
        mDB = new DBHelper(this);
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
                builder.setPositiveButton(android.R.string.ok, new OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedMessage();
                    }});
                builder.setNegativeButton(android.R.string.cancel, new OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }});
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
        
    }
    
    private void deletedAll() {
        
    }    
}
