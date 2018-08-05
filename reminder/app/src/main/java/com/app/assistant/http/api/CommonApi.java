package com.app.assistant.http.api;


import com.app.assistant.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @说明: 请求Api
 * @作者: zhanghe
 * @时间: 2018-02-11 15:19
 */

public interface CommonApi {


    //search suggest api
    @GET("/1/suggestionword")
    Observable<List<String>> getSuggestList(@Query("key") String key);
}
