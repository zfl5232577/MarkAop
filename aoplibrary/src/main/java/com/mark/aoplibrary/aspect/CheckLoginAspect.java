package com.mark.aoplibrary.aspect;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.utils.ActivityManager;
import com.mark.aoplibrary.utils.SPUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 检查登陆动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckLoginAspect {

    @Pointcut("execution(@com.mark.aoplibrary.annotation.CheckLogin * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        if (SPUtils.getInstance().getBoolean("isLogin", false)) {
            result = joinPoint.proceed();
        } else {
            if (MarkAOPHelper.getInstance().getOptions().getLoginActivity() != null) {
                MarkAOPHelper.getInstance().clear();
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Method method = signature.getMethod();
                MarkAOPHelper.getInstance().getMethodList().add(method);
                MarkAOPHelper.getInstance().getMethodArgs().put(method.getName(), joinPoint.getArgs());
                MarkAOPHelper.getInstance().getTargets().put(method.getName(), joinPoint.getTarget());
                Activity currentActivity = ActivityManager.getInstance().currentActivity();
                Intent intent = new Intent(currentActivity, MarkAOPHelper.getInstance().getOptions().getLoginActivity());
                currentActivity.startActivityForResult(intent, MarkAOPHelper.getInstance().getOptions().getREQUEST_CODE());
            } else {
                Toast.makeText(MarkAOPHelper.getInstance().getApplication(), "暂时未登陆，请登陆", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }
}
