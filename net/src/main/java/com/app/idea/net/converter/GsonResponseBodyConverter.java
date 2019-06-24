/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.idea.net.converter;

import com.app.idea.net.exception.RefreshTokenExpiredException;
import com.app.idea.net.exception.TokenExpiredException;
import com.google.gson.TypeAdapter;
import com.app.idea.net.common.BasicResponse;
import com.app.idea.net.common.ErrorCode;
import com.app.idea.net.exception.NoDataExceptionException;
import com.app.idea.net.exception.RemoteLoginExpiredException;
import com.app.idea.net.exception.ServerResponseException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Response响应报文的统一处理
 * DefaultObserver中的 onError 的方法中进行处理
 *
 * @param <T>
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            BasicResponse response = (BasicResponse) adapter.fromJson(value.charStream());
            if (response.getCode() == ErrorCode.SUCCESS) {
                if (response.getResults() != null) {
                    return response.getResults();
                } else {
                    throw new NoDataExceptionException();
                }
            }
            // 特定 API 的错误，在相应的 DefaultObserver 的 onError 的方法中进行处理
            //服务器内部错误
            else if (response.getCode() == ErrorCode.REMOTE_LOGIN) {
                throw new RemoteLoginExpiredException(response.getCode(), response.getMessage());
            } else if (response.getCode() == 0) {
                throw new TokenExpiredException(response.getCode(), response.getMessage());
            } else if (response.getCode() == 0) {
                throw new RefreshTokenExpiredException(response.getCode(), response.getMessage());
            } else if (response.getCode() != ErrorCode.SUCCESS) {
                throw new ServerResponseException(response.getCode(), response.getMessage());
            }
/*
            if (response.isError()) {
                throw new ServerResponseException(response.getCode(), response.getMessage());
            } else if (!response.isError()) {
                if (response.getResults() != null)
                    return response.getResults();
                else
                    throw new NoDataExceptionException();
            }*/
        } finally {
            value.close();
        }
        return null;
    }
}
