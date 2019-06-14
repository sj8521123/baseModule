package com.app.idea.net.exception;

import com.app.idea.net.common.ErrorCode;

/**
 * 服务器内部的异常（外部条件正常）
 */
public class ServerResponseException extends RuntimeException {

    private int errorCode;

    public ServerResponseException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
