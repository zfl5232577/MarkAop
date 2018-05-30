package com.mark.aoplibrary;

import android.app.Activity;
import android.app.Application;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 切面编程帮助类
 *     version: 1.0
 * </pre>
 */
public class MarkAOPHelper {
    private static volatile MarkAOPHelper mInstance;
    private AOPLibraryOptions mOptions;
    private Application mApplication;
    private MarkAOPHelper() {
        mOptions = new AOPLibraryOptions();
    }

    public static MarkAOPHelper getInstance(){
        if (mInstance ==null){
            synchronized (MarkAOPHelper.class){
                if (mInstance ==null){
                    mInstance = new MarkAOPHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application){
        mApplication = application;
    }

    public Application getApplication() {
        return mApplication;
    }

    public AOPLibraryOptions getOptions() {
        return mOptions;
    }
}
