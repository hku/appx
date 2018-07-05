package com.app.reminder.utils;

import com.app.reminder.entity.ReminderEntity;
import com.chinaso.record.greendao.gen.ReminderEntityDao;

/**
 * author: zhanghe
 * created on: 2018/7/5 17:24
 * description: 数据库管理类
 */

public class ReminderDaoManager {

    private ReminderDaoManager() {
    }

    static class SingleInstance {
        private static ReminderDaoManager instance = new ReminderDaoManager();
    }

    public static ReminderDaoManager getInstance() {
        return ReminderDaoManager.SingleInstance.instance;
    }

    /**
     * 插入
     *
     * @param entity
     */
    public void insert(ReminderEntity entity) {
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        dao.insert(entity);
    }
}
