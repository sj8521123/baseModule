
package com.app.idea.net.exception;

import com.app.idea.net.common.ErrorCode;

public class RefreshTokenExpiredException extends RuntimeException {
    private int errorCode;

    public RefreshTokenExpiredException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
