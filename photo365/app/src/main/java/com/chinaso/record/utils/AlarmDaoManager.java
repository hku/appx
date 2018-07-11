package com.chinaso.record.utils;

import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.greendao.gen.AlarmEntityDao;

import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/6/26 9:42
 * description:闹钟数据库操作类
 */

public class AlarmDaoManager {

    private AlarmDaoManager() {
    }

    static class SingleInstance {
        private static AlarmDaoManager instance = new AlarmDaoManager();
    }

    public static AlarmDaoManager getInstance() {
        return AlarmDaoManager.SingleInstance.instance;
    }

    /**
     * 插入
     *
     * @param entity
     */
    public long insert(AlarmEntity entity) {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        return dao.insert(entity);
    }

    /**
     * 查找 倒叙排列
     */
    public List<AlarmEntity> queryDesc() {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        List<AlarmEntity> result = dao.queryBuilder().orderDesc(AlarmEntityDao.Properties.Id).list();
        return result;
    }

    /**
     * 根据id 查询数据
     *
     * @param id
     * @return
     */
    public AlarmEntity query(int id) {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        List<AlarmEntity> result = dao.queryBuilder().where(AlarmEntityDao.Properties.Id.eq(id)).list();
        AlarmEntity entity = null;
        if (result != null && result.size() > 0) {
            entity = result.get(0);
        }
        return entity;
    }

    /**
     * 更新
     */
    public void update(AlarmEntity entity) {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        dao.update(entity);
    }

    /**
     * 删除
     *
     * @param entity 要删除的实体类
     */
    public void remove(AlarmEntity entity) {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        List<AlarmEntity> alarmList = dao.queryBuilder().where(AlarmEntityDao.Properties.Id.eq(entity.getId())).list();
        if (alarmList != null && alarmList.size() > 0) {
            dao.delete(entity);
        }
    }

    /**
     * 根据小时和分钟查询当前的数据
     *
     * @param hour
     * @param minute
     */
    public AlarmEntity queryByTime(int hour, int minute) {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        List<AlarmEntity> alarmList = dao.queryBuilder().where(AlarmEntityDao.Properties.Hour.eq(hour),
                AlarmEntityDao.Properties.Minute.eq(minute)).list();
        AlarmEntity entity = null;
        if (alarmList != null && alarmList.size() > 0) {
            entity = alarmList.get(0);
        }
        return entity;
    }
}
