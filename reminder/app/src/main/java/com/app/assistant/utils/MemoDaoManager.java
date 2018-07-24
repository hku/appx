package com.app.assistant.utils;

import com.app.assistant.entity.MemoEntity;
import com.app.assistant.greendao.gen.MemoEntityDao;

import java.util.List;
import java.util.Random;

/**
 * author: zhanghe
 * created on: 2018/7/5 17:24
 * description: 数据库管理类
 */

public final class MemoDaoManager {

    private MemoDaoManager() {
    }

    static class SingleInstance {
        private static MemoDaoManager instance = new MemoDaoManager();
    }

    public static MemoDaoManager getInstance() {
        return MemoDaoManager.SingleInstance.instance;
    }

    /**
     * 插入
     *
     * @param entity
     */
    public void insert(MemoEntity entity) {
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        dao.insert(entity);
    }

    /**
     * 更新
     *
     * @param entity
     */
    public void update(MemoEntity entity) {
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        dao.update(entity);
    }

    /**
     * 倒叙查询所有数据
     *
     * @return
     */
    public MemoEntity queryById(Long id) {
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        List<MemoEntity> list = dao.queryBuilder().where(MemoEntityDao.Properties.Id.eq(id)).list();
        MemoEntity result = null;
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
    public List<MemoEntity> queryAllData(String tagS) {
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        List<MemoEntity> list = dao.queryBuilder().where(MemoEntityDao.Properties.TagS.eq(tagS)).orderDesc(MemoEntityDao.Properties.Id).list();
        return list;
    }

    /**
     * 删除
     *
     * @param entity
     */
    public void del(MemoEntity entity) {
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        dao.delete(entity);
    }

    /**
     * 获取除当前数据的其他随机某条数据
     *
     * @param entity
     */
    public MemoEntity getRandomItem(MemoEntity entity, String tagS) {
        Long id = entity.getId();
        MemoEntity randomEntity = null;
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        List<MemoEntity> list = dao.queryBuilder().where(MemoEntityDao.Properties.Id.notEq(id),
                MemoEntityDao.Properties.TagS.eq(tagS)).list();
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
    public MemoEntity getRandomItem(String tagS) {
        MemoEntity randomEntity = null;
        MemoEntityDao dao = GreenDaoManager.getInstance().getSession().getMemoEntityDao();
        List<MemoEntity> list = dao.queryBuilder().where(MemoEntityDao.Properties.TagS.eq(tagS)).list();
        if (list != null && list.size() > 0) {
            int length = list.size();
            Random random = new Random();
            int randomInt = random.nextInt(length);
            randomEntity = list.get(randomInt);
        }
        return randomEntity;
    }
}
