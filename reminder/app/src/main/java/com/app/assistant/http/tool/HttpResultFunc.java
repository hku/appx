package com.app.assistant.http.tool;



import com.app.assistant.http.exception.ExceptionConverter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @说明: 异常处理
 * @作者: zhanghe
 * @时间: 2017-11-28 14:47
 */

public class HttpResultFunc<T> implements Function<Throwable, ObservableSource<? extends String>> {

    @Override
    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionConverter.convertException(throwable));
    }
}
