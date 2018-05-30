package com.mark.aoplibrary.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 日志动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class LoggerAspect {

    @Pointcut("execution(@com.mark.aoplibrary.annotation.Logger * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("Logger", "aroundJoinPoint: AOP被切入了" );
        joinPoint.proceed();
    }
}
