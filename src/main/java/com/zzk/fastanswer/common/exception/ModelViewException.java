package com.zzk.fastanswer.common.exception;

/**
 * ModelViewException 用于实现切面编程
 *
 * @author zzk
 * @create 2020-11-05 14:31
 */
public class ModelViewException extends RuntimeException {

    /**
     * 将Exception转换为ModelViewException
     */
    public static ModelViewException transfer(Throwable cause) {
        return new ModelViewException(cause);
    }

    private ModelViewException(Throwable cause) {
        super(cause);
    }
}
