package com.chinaso.record.utils;


import com.chinaso.record.base.RecordApplication;
import com.chinaso.record.greendao.gen.DaoMaster;
import com.chinaso.record.greendao.gen.DaoSession;

/**
 * author: zhanghe
 * created on: 2018/6/4 17:35
 * description: 数据库管理类
 */

public class GreenDaoManager {

    private static final String DB_NAME = "photo-db";

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    private GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper
                (RecordApplication.getContext(), DB_NAME, null);
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
