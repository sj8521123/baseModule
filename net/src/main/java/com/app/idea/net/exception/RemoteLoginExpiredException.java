
package com.app.idea.net.exception;

import com.app.idea.net.common.ErrorCode;

/**
<<<<<<< HEAD
 * 在其它设备上登录异常（外部条件正常）
 *
=======
 * 在其它设备上登录异常
>>>>>>> 40c600863affe41f5a28670cf8ebfeb64424e8e1
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