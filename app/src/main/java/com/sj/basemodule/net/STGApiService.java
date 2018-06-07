package com.sj.basemodule.net;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface STGApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    // TODO: 2018/5/29 写入每个接口
    /**
     * 文件下载
     */
    @GET
    Call<ResponseBody> loadPdfFile(@Url String fileUrl);
}


