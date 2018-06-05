package com.mark.aoplibrary.aspect;

import android.widget.Toast;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.utils.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 功能暂未开放动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class NotOpenAspect {

    @Pointcut("execution(@com.mark.aoplibrary.annotation.NotOpen * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
            Toast.makeText(MarkAOPHelper.getInstance().getApplication(),
                    "功能暂未开放，敬请期待！",Toast.LENGTH_SHORT).show();
    }
}
