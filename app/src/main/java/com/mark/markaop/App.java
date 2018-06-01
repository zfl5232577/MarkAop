package com.mark.markaop;

import android.app.Application;

import com.mark.aoplibrary.MarkAOPHelper;

import cn.aorise.common.core.util.Utils;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        MarkAOPHelper.getInstance().init(this);
        MarkAOPHelper.getInstance().getOptions().setLoginActivity(LoginActivity.class,LoginActivity.REQUEST_CODE);
    }
}
