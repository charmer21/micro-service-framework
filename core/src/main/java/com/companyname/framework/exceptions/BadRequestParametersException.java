package com.companyname.framework.exceptions;

import lombok.Data;

@Data
public class BadRequestParametersException extends Exception {
    /**
     * 实例化参数错误异常
     * @param parameterName 参数名
     * @param parameterDesc 参数描述
     * @param message 错误信息
     * @param cause
     */
    public BadRequestParametersException(String parameterName, String parameterDesc, String message, Throwable cause) {
        super(message, cause);
        this.parameterName = parameterName;
        this.parameterDesc = parameterDesc;
    }

    private String parameterName;
    private String parameterDesc;
}
