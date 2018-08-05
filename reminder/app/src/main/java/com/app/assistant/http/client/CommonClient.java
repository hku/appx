package com.app.assistant.http.client;

import com.app.assistant.activity.SearchNewActivity;
import com.app.assistant.base.BaseResponse;
import com.app.assistant.http.api.CommonApi;
import com.app.assistant.http.base.BaseApiClient;
import com.app.assistant.http.tool.HttpResultFunc;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2018-02-11 15:18
 */

public class CommonClient extends BaseApiClient {

    private CommonApi commonWebApi;

    private static class SingletonHolder {
        private static final CommonClient INSTANCE = new CommonClient();
    }

    public static CommonClient getInstance() {
        return CommonClient.SingletonHolder.INSTANCE;
    }

    public CommonClient() {
        commonWebApi = getRetrofit().create(CommonApi.class);
    }

    /**
     * @param activity
     * @param key      关键词
     */
    public void addComment(String key, final SearchNewActivity activity) {
        commonWebApi.getSuggestList(key)
                .onErrorResumeNext(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> response) throws Exception {
                        activity.getSuggestionListSuc(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
