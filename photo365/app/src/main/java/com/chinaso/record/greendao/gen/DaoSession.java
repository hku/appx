package com.chinaso.record.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.entity.AlarmEntity;

import com.chinaso.record.greendao.gen.PhotoEntityDao;
import com.chinaso.record.greendao.gen.AlarmEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig photoEntityDaoConfig;
    private final DaoConfig alarmEntityDaoConfig;

    private final PhotoEntityDao photoEntityDao;
    private final AlarmEntityDao alarmEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        photoEntityDaoConfig = daoConfigMap.get(PhotoEntityDao.class).clone();
        photoEntityDaoConfig.initIdentityScope(type);

        alarmEntityDaoConfig = daoConfigMap.get(AlarmEntityDao.class).clone();
        alarmEntityDaoConfig.initIdentityScope(type);

        photoEntityDao = new PhotoEntityDao(photoEntityDaoConfig, this);
        alarmEntityDao = new AlarmEntityDao(alarmEntityDaoConfig, this);

        registerDao(PhotoEntity.class, photoEntityDao);
        registerDao(AlarmEntity.class, alarmEntityDao);
    }
    
    public void clear() {
        photoEntityDaoConfig.clearIdentityScope();
        alarmEntityDaoConfig.clearIdentityScope();
    }

    public PhotoEntityDao getPhotoEntityDao() {
        return photoEntityDao;
    }

    public AlarmEntityDao getAlarmEntityDao() {
        return alarmEntityDao;
    }

}
