package com.app.assistant.controller;

import com.app.assistant.fragment.HomeFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: zhanghe
 * created on: 2018/7/26 17:46
 * description:
 */

public class HomeController {

    private HomeFragment mFragment;

    public HomeController(HomeFragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * 下载服务器上的.json文件
     *
     * @param urlString
     * @return
     */
    public String downloadFileAsString(String urlString) {
        StringBuffer sb = new StringBuffer();
        BufferedReader buffer = null;
        String line = null;
        try {
            //创建URL对象
            URL url = new URL(urlString);

            //创建http连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
