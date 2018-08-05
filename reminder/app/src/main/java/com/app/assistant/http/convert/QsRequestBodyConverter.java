package com.app.assistant.http.convert;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2018-03-01 15:11
 */

public class QsRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private Gson mGson = new Gson();

    @Override
    public RequestBody convert(T value) throws IOException {
        String request = mGson.toJson(value);
        return RequestBody.create(MEDIA_TYPE, request);
    }
}
