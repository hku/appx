package com.app.assistant.utils;

import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.greendao.gen.ReminderEntityDao;

import java.util.List;
import java.util.Random;

/**
 * author: zhanghe
 * created on: 2018/7/5 17:24
 * description: 数据库管理类
 */

public final class ReminderDaoManager {

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

    /**
     * 更新
     *
     * @param entity
     */
    public void update(ReminderEntity entity) {
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        dao.update(entity);
    }

    /**
     * 倒叙查询所有数据
     *
     * @return
     */
    public ReminderEntity queryById(Long id) {
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        List<ReminderEntity> list = dao.queryBuilder().where(ReminderEntityDao.Properties.Id.eq(id)).list();
        ReminderEntity result = null;
        if (list != null && list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }


    /**
     * 倒叙查询所有数据
     *
     * @return
     */
    public List<ReminderEntity> queryAllData() {
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        List<ReminderEntity> list = dao.queryBuilder().orderDesc(ReminderEntityDao.Properties.Id).list();
        return list;
    }

    /**
     * 删除
     *
     * @param entity
     */
    public void del(ReminderEntity entity) {
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        dao.delete(entity);
    }

    /**
     * 获取除当前数据的其他随机某条数据
     *
     * @param entity
     */
    public ReminderEntity getRandomItem(ReminderEntity entity) {
        Long id = entity.getId();
        ReminderEntity randomEntity = null;
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        List<ReminderEntity> list = dao.queryBuilder().where(ReminderEntityDao.Properties.Id.notEq(id)).list();
        if (list != null && list.size() > 0) {
            int length = list.size();
            Random random = new Random();
            int randomInt = random.nextInt(length);
            randomEntity = list.get(randomInt);
        }
        return randomEntity;
    }

    /**
     * 获取数据库中随机某条数据
     */
    public ReminderEntity getRandomItem() {
        ReminderEntity randomEntity = null;
        ReminderEntityDao dao = GreenDaoManager.getInstance().getSession().getReminderEntityDao();
        List<ReminderEntity> list = dao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            int length = list.size();
            Random random = new Random();
            int randomInt = random.nextInt(length);
            randomEntity = list.get(randomInt);
        }
        return randomEntity;
    }
}
