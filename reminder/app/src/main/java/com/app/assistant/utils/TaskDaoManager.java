package com.app.assistant.utils;

import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.greendao.gen.ReminderEntityDao;
import com.app.assistant.greendao.gen.TaskEntityDao;

import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/7/16 14:54
 * description:
 */

public final class TaskDaoManager {

    private TaskDaoManager() {
    }

    static class SingleInstance {
        private static TaskDaoManager instance = new TaskDaoManager();
    }

    public static TaskDaoManager getInstance() {
        return TaskDaoManager.SingleInstance.instance;
    }

    /**
     * 插入
     *
     * @param entity
     */
    public void insert(TaskEntity entity) {
        TaskEntityDao dao = GreenDaoManager.getInstance().getSession().getTaskEntityDao();
        dao.insert(entity);
    }

    /**
     * 倒叙查询所有数据
     *
     * @return
     */
    public List<TaskEntity> queryAllData() {
        TaskEntityDao dao = GreenDaoManager.getInstance().getSession().getTaskEntityDao();
        List<TaskEntity> list = dao.queryBuilder().orderDesc(TaskEntityDao.Properties.Id).list();
        return list;
    }

    /**
     * 删除
     *
     * @param entity
     */
    public void del(TaskEntity entity) {
        TaskEntityDao dao = GreenDaoManager.getInstance().getSession().getTaskEntityDao();
        dao.delete(entity);
    }
}
