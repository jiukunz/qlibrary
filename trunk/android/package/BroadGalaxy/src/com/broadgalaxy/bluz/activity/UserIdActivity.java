
package com.broadgalaxy.bluz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.broadgalaxy.bluz.R;

public class UserIdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_user_id);

        ((Button) findViewById(R.id.confirm)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent activity = new Intent();
                activity.setClass(getApplicationContext(), HomeActivity.class);
                startActivity(activity);
            }
        });
        ((Button) findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

}
