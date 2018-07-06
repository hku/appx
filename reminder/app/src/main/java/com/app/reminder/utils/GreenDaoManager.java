package com.app.reminder.utils;


import com.app.reminder.base.ReminderApplication;
import com.app.reminder.greendao.gen.DaoMaster;
import com.app.reminder.greendao.gen.DaoSession;

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
                (ReminderApplication.getContext(), DB_NAME, null);
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
