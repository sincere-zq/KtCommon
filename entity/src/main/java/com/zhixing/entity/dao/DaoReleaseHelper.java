package com.zhixing.entity.dao;

import android.content.Context;

import com.zhixing.entity.CardBeanDao;
import com.zhixing.entity.CloudFaceBeanDao;
import com.zhixing.entity.CloudLicenceBeanDao;
import com.zhixing.entity.CollectCardBeanDao;
import com.zhixing.entity.CollectFingerBeanDao;
import com.zhixing.entity.CollectPersonBeanDao;
import com.zhixing.entity.DaoMaster;
import com.zhixing.entity.FingerBeanDao;
import com.zhixing.entity.HistoryConsumeBeanDao;
import com.zhixing.entity.PassFaceBeanDao;
import com.zhixing.entity.PersonBeanDao;
import com.zhixing.entity.RecogeRecordBeanDao;

import org.greenrobot.greendao.database.Database;


/**
 * 作者：Deng Donglin
 * 时间：2017/7/21 0021 17:26
 * 描述：
 */
public class DaoReleaseHelper extends DaoMaster.OpenHelper {
    public DaoReleaseHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * @param db 初次创建的时候会，会执行
     */
    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    /**
     * 版本更新的时候处理，但是数据库刚创建的时候，不会创建
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database database, int oldVersion, int newVersion) {
        MigrationHelper.getInstance().migrate(database, CardBeanDao.class, CloudFaceBeanDao.class,
                CollectCardBeanDao.class, CollectFingerBeanDao.class, CollectPersonBeanDao.class, FingerBeanDao.class,
                HistoryConsumeBeanDao.class, PassFaceBeanDao.class, PersonBeanDao.class, RecogeRecordBeanDao.class, CloudLicenceBeanDao.class
        );
        super.onUpgrade(database, oldVersion, newVersion);
    }
}
