package com.app.assistant.http.exception;

import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.NetworkUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * @说明
 * @作者 zhanghe
 * @时间 2017/11/28
 */
public class ExceptionConverter {

    private static final String TAG = "ExceptionConverter";

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static Throwable convertException(Throwable e) {
        Throwable finalThrow;
        if (!NetworkUtils.isConnected()) {
            finalThrow = new Throwable("网络无连接，请检查网络后重试", new Throwable("网络无连接，请检查网络后重试"));
            return finalThrow;
        }
        if (e instanceof HttpException) {//HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    LogUtils.e(TAG + "HttpException code = " + httpException.code());
                    finalThrow = new Throwable("服务器异常，请稍后再试", new Throwable("服务器异常，请稍后再试"));
                    break;
            }
            return finalThrow;
        } else if (e instanceof ServerException) {//服务器返回的错误
            ServerException resultException = (ServerException) e;
            finalThrow = new Throwable(resultException.getCode() + "", new Throwable(resultException.getMsg()));
            return finalThrow;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            LogUtils.e(TAG + "JsonParseException or JSONException or ParseException");
            finalThrow = new Throwable("服务器异常，请稍后再试", new Throwable("服务器异常，请稍后再试"));
            return finalThrow;            //均视为解析错误
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            LogUtils.e(TAG + "ConnectException  or SocketTimeoutException");
            finalThrow = new Throwable("网络无连接，请检查网络后重试", new Throwable("网络无连接，请检查网络后重试"));
            return finalThrow;
        } else if (e instanceof UnknownHostException) {
            LogUtils.e(TAG + "UnknownHostException");
            finalThrow = new Throwable("服务器异常，请稍后再试", new Throwable("服务器异常，请稍后再试"));
            return finalThrow;
        } else {
            LogUtils.e(TAG + "other exception");
            finalThrow = new Throwable("数据请求失败，请稍后再试", new Throwable("数据请求失败，请稍后再试"));
            return finalThrow;
        }
    }

}
