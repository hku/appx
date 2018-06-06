package com.chinaso.record.utils;

import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.greendao.gen.PhotoEntityDao;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/6/5 14:09
 * description: 图片数据库操作类
 */

public class PhotoDaoManager {

    private PhotoDaoManager() {
    }

    static class SingleInstance {
        private static PhotoDaoManager instance = new PhotoDaoManager();
    }

    public static PhotoDaoManager getInstance() {
        return PhotoDaoManager.SingleInstance.instance;
    }

    /**
     * 插入
     * @param entity
     */
    public void insert(PhotoEntity entity){
        PhotoEntityDao dao = GreenDaoManager.getInstance().getSession().getPhotoEntityDao();
        dao.insert(entity);
    }

    /**
     * 分页查询
     * @param page 页数
     */
    public List<PhotoEntity> query(int page){
        PhotoEntityDao dao = GreenDaoManager.getInstance().getSession().getPhotoEntityDao();
        QueryBuilder<PhotoEntity> queryBuilder = dao.queryBuilder();
        List<PhotoEntity> photoList =  queryBuilder.offset(page * 20).limit(20).
                orderDesc(PhotoEntityDao.Properties.Id).list();
        return photoList;
    }
}
