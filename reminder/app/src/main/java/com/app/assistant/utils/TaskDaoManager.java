package com.app.assistant.utils;

import android.text.TextUtils;

import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.greendao.gen.ReminderEntityDao;
import com.app.assistant.greendao.gen.TaskEntityDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/7/16 14:54
 * description:
 */

public final class TaskDaoManager {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * 更新
     *
     * @param entity
     */
    public void update(TaskEntity entity) {
        TaskEntityDao dao = GreenDaoManager.getInstance().getSession().getTaskEntityDao();
        dao.update(entity);
    }

    /**
     * 获取今天未完成的任务列表
     */
    public List<TaskEntity> getTodayTask() {
        TaskEntityDao dao = GreenDaoManager.getInstance().getSession().getTaskEntityDao();
        List<TaskEntity> result = new ArrayList<>();
        List<TaskEntity> list = dao.queryBuilder().where
                (TaskEntityDao.Properties.Status.eq(false)).orderDesc(TaskEntityDao.Properties.Id).list();
        for (TaskEntity taskEntity : list) {
            String preDateS = taskEntity.getPreDate();
            String dateS = taskEntity.getDate();
            Date preDate = null;
            Date date = null;
            Date today = null;
            boolean isEffective;
            try {
                date = FORMAT.parse(dateS);
                String todayS = FORMAT.format(new Date());
                today = FORMAT.parse(todayS);
            } catch (ParseException px) {
                px.printStackTrace();
            }
            //该条数据是提前3天提醒
            if (!TextUtils.isEmpty(preDateS)) {
                try {
                    preDate = FORMAT.parse(preDateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                isEffective = CommonUtils.isEffectiveDate(today, preDate, date);
            } else {
                isEffective = CommonUtils.isEffectiveDate(today, date);
            }
            if (isEffective) {
                result.add(taskEntity);
            }
        }
        return result;
    }
}
