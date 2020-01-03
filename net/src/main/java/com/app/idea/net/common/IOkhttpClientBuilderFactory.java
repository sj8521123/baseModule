package com.app.idea.net.common;

import okhttp3.OkHttpClient;

public interface IOkhttpClientBuilderFactory {
    public OkHttpClient.Builder createClient(String type);
}
