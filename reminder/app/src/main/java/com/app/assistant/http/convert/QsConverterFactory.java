package com.app.assistant.http.convert;


import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2018-03-01 15:07
 */

public class QsConverterFactory extends Converter.Factory {

    private final Gson gson;

    private QsConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static QsConverterFactory create() {
        return create(new Gson());
    }

    public static QsConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new QsConverterFactory(gson);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new QsRequestBodyConverter<>();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new QsResponseBodyConverter<>(gson, type);
    }
}
