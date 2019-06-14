package com.app.idea.net.download;

import okhttp3.ResponseBody;


public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess(ResponseBody responseBody);

    void onFail(String message);

    void onComplete();
}
