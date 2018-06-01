package com.mark.aoplibrary;

import android.app.Activity;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 组件参数类
 *     version: 1.0
 * </pre>
 */
public class AOPLibraryOptions {
    private boolean mDebug = true;
    private int REQUEST_CODE;
    private Class<? extends Activity> mLoginActivity;
    private Class<? extends Activity> mNoNETActivity;

    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    public Class<? extends Activity> getLoginActivity() {
        return mLoginActivity;
    }

    public void setLoginActivity(Class<? extends Activity> loginActivity,int requestCode) {
        mLoginActivity = loginActivity;
        REQUEST_CODE = requestCode;
    }

    public int getREQUEST_CODE() {
        return REQUEST_CODE;
    }

    public Class<? extends Activity> getNoNETActivity() {
        return mNoNETActivity;
    }

    public void setNoNETActivity(Class<? extends Activity> noNETActivity) {
        mNoNETActivity = noNETActivity;
    }
}
