package com.mark.aoplibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : APP页面管理
 *     version: 1.0
 * </pre>
 */
public class ActivityManager {
    private static Stack<Activity> sActivityStack;
    private static ActivityManager sInstance;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (sInstance == null) {
            synchronized (ActivityManager.class) {
                if (sInstance == null) {
                    sInstance = new ActivityManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加Activity到栈
     *
     * @param activity Activity对象
     */
    public void addActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<Activity>();
        }
        sActivityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<Activity>();
        }
        sActivityStack.remove(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     *
     * @return 栈顶Activity对象
     */
    public Activity currentActivity() {
        if (sActivityStack == null || sActivityStack.isEmpty()) {
            return null;
        }
        return sActivityStack.lastElement();
    }

    /**
     * 找Activity对象
     *
     * @param cls Activity对象
     * @return 找到Activity对象则返回对象，没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : sActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = sActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity Activity对象
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param cls Activity对象类名
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (sActivityStack == null) return;
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                activity.finish();
            }
        }
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 恢复当前APP页面
     *
     * @param context 上下文
     * @return
     */
    public boolean resumeApp(Context context) {
        boolean isAppLive = false;
        Activity activity = currentActivity();
        if (activity != null) {
            isAppLive = true;
            Intent intent = new Intent(context, activity.getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
        return isAppLive;
    }
}
