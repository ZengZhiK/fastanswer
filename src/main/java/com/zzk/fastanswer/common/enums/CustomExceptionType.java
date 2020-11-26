package com.zzk.fastanswer.common.enums;

/**
 * 自定义异常的code
 *
 * @author zzk
 * @create 2020-11-04 21:15
 */
public enum CustomExceptionType {
    /**
     * 用户输入异常或没有权限
     */
    USER_INPUT_ERROR(400, "您输入的数据错误或您没有权限访问资源！"),
    /**
     * 服务器异常
     */
    SYSTEM_ERROR(500, "系统出现异常，请您稍后再试或联系管理员！"),

    /**
     * 系统未知异常
     */
    OTHER_ERROR(999, "系统出现未知异常，请联系管理员！");

    /**
     * code (200, 400, 500)
     */
    private int code;

    /**
     * 异常描述
     */
    private String desc;

    CustomExceptionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
