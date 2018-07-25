package com.app.assistant.utils;

import android.content.Context;

/**
 * author: zhanghe
 * created on: 2018/7/5 15:40
 * description: 常量
 */

public class Constant {

    public static final String[] TAG_ARRAY = {"菜根谭", "小常识", "学英语", "冷笑话", "格言佳句", "memo"};
    public static final String[] SEARCH_HOT_ARRAY = {"习近平", "长生医药", "世界杯", "北京大雨", "人工智能", "区块链"};
    public static final String[] SEARCH_WAYS_ARRAY = {"国搜", "百度", "搜狗", "bing", "360"};

    public static final String DELIVER_TAG = "edit_data";

    public final static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }

    public static final String ALARM_CLOCK = "alarm_clock";

    public static final String PACKAGE_NAME = "com.app.reminder";


    public static final String CHINAOSO_URL = "http://m.chinaso.com/page/search.htm?keys=";
    public static final String BAIDU_URL = "https://www.baidu.com/s?wd=";
    public static final String SOUGOU_URL = "https://m.sogou.com/web/searchList.jsp?keyword=";
    public static final String BING_URL = "https://cn.bing.com/search?q=";
    public static final String QIHU_URL = "https://m.so.com/s?q=";
}
