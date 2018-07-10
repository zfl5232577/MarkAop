package com.mark.aoplibrary.utils.reflect;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/07/10
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class ReflectException extends RuntimeException{

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
