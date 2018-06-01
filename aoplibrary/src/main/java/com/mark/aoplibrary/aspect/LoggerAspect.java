package com.mark.aoplibrary.aspect;

import android.util.Log;

import com.mark.aoplibrary.MarkAOPHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

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

    @Pointcut("execution(@com.mark.aoplibrary.annotation.Logger *.new(..))")//构造器切入点
    public void constructorAnnotated() {
    }

    @Pointcut("execution(@com.mark.aoplibrary.annotation.Logger * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated() || constructorAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!MarkAOPHelper.getInstance().getOptions().isDebug()){
            return joinPoint.proceed();
        }
        Log.e("Logger",joinPoint.getSignature().toShortString() + " Args : " + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        Log.e("Logger",joinPoint.getSignature().toShortString() + " Result : " + ("void".equalsIgnoreCase(type)?"void":result));
        return result;
    }
}
