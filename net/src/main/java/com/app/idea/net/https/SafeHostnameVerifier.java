package com.app.idea.net.https;


import com.app.idea.net.common.Constants;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;



public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        if (Constants.IP.equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
            return true;
        }
        return false;
    }
}
