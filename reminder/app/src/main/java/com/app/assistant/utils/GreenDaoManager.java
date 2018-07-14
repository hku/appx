package com.app.assistant.utils;


import com.app.assistant.base.AssistantApplication;
import com.app.assistant.greendao.gen.DaoMaster;
import com.app.assistant.greendao.gen.DaoSession;

/**
 * author: zhanghe
 * created on: 2018/6/4 17:35
 * description: 数据库管理类
 */

public final class GreenDaoManager {

    private static final String DB_NAME = "reminder-db";

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    private GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper
                (AssistantApplication.getContext(), DB_NAME, null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    static class SingleInstance {
        private static GreenDaoManager instance = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance() {
        return SingleInstance.instance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
