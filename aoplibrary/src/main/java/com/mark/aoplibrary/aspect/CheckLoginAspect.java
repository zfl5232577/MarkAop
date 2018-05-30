package com.mark.aoplibrary.aspect;

import android.content.Intent;
import android.util.Log;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.annotation.CheckLogin;

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
 *     desc   : 检查登陆动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckLoginAspect {

    @Pointcut("@within(com.mark.aoplibrary.annotation.CheckLogin)||@annotation(com.mark.aoplibrary.annotation.CheckLogin)")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log.e("AOP", "aroundJoinPoint: "+method.getName() );
        CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);
        if (checkLogin!=null) {
            Log.e("AOP", "aroundJoinPoint: "+checkLogin );
            Log.e("AOP", "aroundJoinPoint: "+checkLogin.isLogin() );
            if (checkLogin.isLogin()) {
                joinPoint.proceed();
            }else {
                Intent intent = new Intent(MarkAOPHelper.getInstance().getApplication(),
                        MarkAOPHelper.getInstance().getOptions().getLoginActivity());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MarkAOPHelper.getInstance().getApplication().startActivity(intent);
            }
        } else {
            // 不影响原来的流程
            joinPoint.proceed();
        }
    }
}
