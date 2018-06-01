package com.mark.aoplibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.mark.aoplibrary.utils.ActivityManager;

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
        mApplication.registerActivityLifecycleCallbacks(sLifecycleCallbacks);
    }

    public Application getApplication() {
        return mApplication;
    }

    public AOPLibraryOptions getOptions() {
        return mOptions;
    }

    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().finishActivity(activity);
        }
    };
}
