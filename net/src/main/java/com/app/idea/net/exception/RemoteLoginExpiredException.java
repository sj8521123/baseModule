
package com.app.idea.net.exception;

import com.app.idea.net.common.ErrorCode;

/**
 * 在其它设备上登录异常（外部条件正常）
 */
public class RemoteLoginExpiredException extends RuntimeException {
    private int errorCode;

    public RemoteLoginExpiredException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}