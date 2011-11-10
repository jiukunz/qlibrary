
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
        String userid = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE)
                            .getString(Application.PREF_USER_ID, "");
        if (null != userid && userid.length() > 1) {
            mUserId.setText(userid);
            mConfirm.setEnabled(true);
        }
        mConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Editor e = getSharedPreferences(Application.PREF_FILE_NAME, MODE_PRIVATE).edit();
                e.putString(Application.PREF_USER_ID, mUserId.getText().toString());
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
