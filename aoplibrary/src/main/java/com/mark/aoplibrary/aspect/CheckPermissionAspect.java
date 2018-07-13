package com.mark.aoplibrary.aspect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mark.aoplibrary.annotation.CheckPermission;
import com.mark.aoplibrary.utils.ActivityManager;
import com.mark.aoplibrary.utils.MPermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;


/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 检查权限动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckPermissionAspect {
    Object result = null;

    @Pointcut("@within(com.mark.aoplibrary.annotation.CheckPermission)||@annotation(com.mark.aoplibrary.annotation.CheckPermission)")
    public void methodAnnotated() {
    }

    @Around("execution(!synthetic * *(..)) && methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckPermission permission = method.getAnnotation(CheckPermission.class);
        if (permission != null) {
            WeakReference<AppCompatActivity> currentActivity = new WeakReference<AppCompatActivity>((AppCompatActivity) ActivityManager.getInstance().currentActivity());
            MPermissionUtils.requestPermissionsResult(currentActivity.get(), 0x999, permission.value()
                    , new MPermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            try {
                                result =joinPoint.proceed();//获得权限，执行原方法
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onPermissionDenied() {
                            MPermissionUtils.showTipsDialog(currentActivity.get());
                        }
                    });
        } else {
            result = joinPoint.proceed();
        }
        return result;
    }
}
