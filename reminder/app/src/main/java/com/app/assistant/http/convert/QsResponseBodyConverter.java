package com.app.assistant.http.convert;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2018-03-01 15:14
 */

public class QsResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public QsResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return gson.fromJson(value.string(), type);
        } finally {

        }
    }
}
