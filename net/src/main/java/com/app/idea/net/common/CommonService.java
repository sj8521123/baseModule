package com.app.idea.net.common;


import com.app.idea.net.download.DwonloadRequest;
import com.app.idea.net.token.RefreshTokenResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载文件的service
 */

public interface CommonService {
    @Streaming
    @GET
        //("download.do")
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载

    @POST("file/download")
    Observable<ResponseBody> downloadFile(@Body DwonloadRequest downloadRequest);

    @FormUrlEncoded
    @POST("file/download")
    Observable<ResponseBody> downloadFile(@FieldMap Map<String, Object> map);

    @GET("refresh_token")
    Observable<RefreshTokenResponse> refreshToken();
}
