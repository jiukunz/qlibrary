
package com.broadgalaxy.bluz.component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.broadgalaxy.bluz.R;

public class UserId extends FrameLayout {

    private Button mCancelBtn;

    private Button mConfirmBtn;

    private EditText mUserId;

    public UserId(Context context) {
        super(context);
    }

    public UserId(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserId(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        View child = inflate(getContext(), R.layout.component_user_id, null);
        addView(child );
        mUserId = ((EditText) findViewById(R.id.user_id));
        mUserId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (isValid(s)) {
                    mConfirmBtn.setEnabled(true);
                } else {
                    mConfirmBtn.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }
        });
        mConfirmBtn = ((Button) findViewById(R.id.confirm));
        mConfirmBtn.setEnabled(false);
        mCancelBtn = ((Button) findViewById(R.id.cancel));
    }

    protected boolean isValid(Editable s) {
        // TODO Auto-generated method stub
        return null != s && s.length() > 0;
    }

}
