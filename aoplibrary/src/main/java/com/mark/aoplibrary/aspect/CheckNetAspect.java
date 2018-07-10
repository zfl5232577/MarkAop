package com.mark.aoplibrary.aspect;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.annotation.CheckNet;
import com.mark.aoplibrary.utils.Utils;
import com.mark.aoplibrary.utils.reflect.Reflect;
import com.mark.aoplibrary.utils.reflect.ReflectException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 检查网络动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckNetAspect {

    @Pointcut("execution(@com.mark.aoplibrary.annotation.CheckNet * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        if (Utils.isConnected()) {
            result = joinPoint.proceed();
        }else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            CheckNet checkNet = method.getAnnotation(CheckNet.class);

            if (checkNet==null){
                Toast.makeText(MarkAOPHelper.getInstance().getApplication(),"网络暂时不可用，请检查网络",Toast.LENGTH_SHORT).show();
                return result;
            }

            String notNetMethod = checkNet.notNetMethod();
            if (!TextUtils.isEmpty(notNetMethod)) {
                try {
                    Reflect.on(joinPoint.getTarget()).call(notNetMethod);
                } catch (ReflectException e) {
                    e.printStackTrace();
                    Log.e("mark","no method "+notNetMethod);
                }
            }else {
                Toast.makeText(MarkAOPHelper.getInstance().getApplication(),"网络暂时不可用，请检查网络",Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }
}
