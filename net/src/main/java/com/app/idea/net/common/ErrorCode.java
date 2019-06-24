
package com.app.idea.net.common;


import com.app.idea.R;

import androidx.annotation.StringRes;
import basemodule.sj.com.basic.util.Util;

/**
 * 服务内部错误的code码集合
 */
public class ErrorCode {
    /**
     * request success
     */
    public static final int SUCCESS = 1000;
    /**
     * Wrong verify code
     */
    public static final int VERIFY_CODE_ERROR = 110011;
    /**
     * Verify code is invalid
     */
    public static final int VERIFY_CODE_EXPIRED = 110010;
    /**
     * User is not register
     */
    public static final int ACCOUNT_NOT_REGISTER = 110009;
    /**
     * Wrong password or username
     */
    public static final int PASSWORD_ERROR = 110012;

    /**
     * Wrong old password
     */
    public static final int OLD_PASSWORD_ERROR = 110015;

    public static final int USER_REGISTERED = 110006;

    public static final int PARAMS_ERROR = 19999;
    /**
     * 异地登录
     */
    public static final int REMOTE_LOGIN = 91011;
    /**
     * token失效
     */
    public static final int TOKEN_EXPIRED = 1011;
    /**
     * refreshToken失效
     */
    public static final int REFRESH_TOKEN_EXPIRED = 1012;


    /**
     * get error message with error code
     *
     * @param errorCode error code
     * @return error message
     */
    public static String getErrorMessage(int errorCode) {
        String message;
        switch (errorCode) {
            case VERIFY_CODE_ERROR:
                message = getString(R.string.verify_code_error);
                break;
            case VERIFY_CODE_EXPIRED:
                message = getString(R.string.verify_code_expired);
                break;
            case ACCOUNT_NOT_REGISTER:
                message = getString(R.string.not_register);
                break;
            case PASSWORD_ERROR:
                message = getString(R.string.wrong_pwd_username);
                break;
            case USER_REGISTERED:
                message = getString(R.string.user_registered);
                break;
            case OLD_PASSWORD_ERROR:
                message = getString(R.string.wrong_password);
                break;
            case PARAMS_ERROR:
                message = getString(R.string.parameters_exception);
                break;
            case REMOTE_LOGIN:
                message = getString(R.string.remote_login);
                break;
            case TOKEN_EXPIRED:
                message = getString(R.string.token_expired);
                break;
            case REFRESH_TOKEN_EXPIRED:
                message = getString(R.string.refresh_token_expired);
                break;
            default:
                message = getString(R.string.request_error) + errorCode;
                break;
        }
        return message;
    }

    private static String getString(@StringRes int resId) {
        return Util.getContext().getString(resId);
    }
}
