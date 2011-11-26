
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.broadgalaxy.bluz.Application;
import com.broadgalaxy.bluz.R;
import com.broadgalaxy.util.MiscUtil;

public class UserIdActivity extends Activity {

    private EditText mUserId;
    private View mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_user_id);
        mUserId = ((EditText) findViewById(R.id.user_id));
        mConfirm =
                ((Button) findViewById(R.id.confirm));
        int userid = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE)
                            .getInt(Application.PREF_USER_ID, 0);
        if (0 != userid) {
            mUserId.setText(userid + "");
            mConfirm.setEnabled(true);
        }
        mConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Editor e = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).edit();
                String userId = mUserId.getText().toString();
                int id = 0;
                id = MiscUtil.userid2int(userId);
                e.putInt(Application.PREF_USER_ID, id );
                e.commit();

                Intent activity = new Intent();
                activity.setClass(getApplicationContext(),
                        HomeActivity.class);
                startActivity(activity);
                setResult(RESULT_OK);
                finish();
            }
        });
        ((Button) findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

}
