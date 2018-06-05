package com.mark.markaop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mark.aoplibrary.utils.SPUtils;


public class LoginActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0x100;
    private final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put("isLogin",true);
                setResult(RESULT_OK,new Intent());
                finish();
            }
        });
    }

}
