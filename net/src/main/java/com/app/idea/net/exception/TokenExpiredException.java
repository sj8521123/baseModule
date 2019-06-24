
package com.app.idea.net.exception;

import com.app.idea.net.common.ErrorCode;

public class TokenExpiredException extends RuntimeException {

    private int errorCode;

    public TokenExpiredException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
