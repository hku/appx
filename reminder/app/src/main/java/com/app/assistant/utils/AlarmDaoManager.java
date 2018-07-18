package com.app.assistant.utils;


import android.content.IntentFilter;
import android.support.v4.app.NavUtils;

import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.greendao.gen.AlarmEntityDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/6/26 9:42
 * description:闹钟数据库操作类
 */

public final class AlarmDaoManager {

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

    /**
     * 获取与当前时间最接近的未关闭的闹钟
     *
     * @return
     */
    public AlarmEntity getClosestClock() {
        AlarmEntityDao dao = GreenDaoManager.getInstance().getSession().getAlarmEntityDao();
        List<AlarmEntity> notClosedList = dao.queryBuilder().where(
                AlarmEntityDao.Properties.IsOpen.eq(true)).list();
        AlarmEntity resultEntity = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        long dateMil = calendar.getTimeInMillis();
        calendar.setTime(date);
        long minDiff = 0L;
        int resultPosition = 0;

        //初始化minDiff
        for (int i = 0; i < notClosedList.size(); i++) {
            AlarmEntity entity = notClosedList.get(i);
            int hour = entity.getHour();
            int min = entity.getMinute();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            long dbMil = calendar.getTimeInMillis();
            long diff = dbMil - dateMil;
            //数据库中的数据的日期大于或等于当前日期
            if (diff >= 0) {
                minDiff = diff;
                resultPosition = i;
                resultEntity = entity;
                break;
            }
        }


        for (int i = resultPosition + 1; i < notClosedList.size(); i++) {
            AlarmEntity entity = notClosedList.get(i);
            int hour = entity.getHour();
            int min = entity.getMinute();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            long dbMil = calendar.getTimeInMillis();
            long diff = dbMil - dateMil;
            if (diff >= 0 && diff - minDiff < 0) {
                minDiff = diff;
                resultEntity = entity;
            }
        }
        return resultEntity;
    }
}
