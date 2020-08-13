package com.zhixing.entity.dao;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.zhixing.entity.CardBean;
import com.zhixing.entity.CardBeanDao;
import com.zhixing.entity.CloudFaceBean;
import com.zhixing.entity.CloudFaceBeanDao;
import com.zhixing.entity.CloudLicenceBean;
import com.zhixing.entity.CloudLicenceBeanDao;
import com.zhixing.entity.CollectCardBean;
import com.zhixing.entity.CollectCardBeanDao;
import com.zhixing.entity.CollectFaceBean;
import com.zhixing.entity.CollectFaceBeanDao;
import com.zhixing.entity.CollectFingerBean;
import com.zhixing.entity.CollectFingerBeanDao;
import com.zhixing.entity.CollectPersonBean;
import com.zhixing.entity.CollectPersonBeanDao;
import com.zhixing.entity.DaoMaster;
import com.zhixing.entity.DaoSession;
import com.zhixing.entity.FingerBean;
import com.zhixing.entity.FingerBeanDao;
import com.zhixing.entity.HistoryConsumeBean;
import com.zhixing.entity.HistoryConsumeBeanDao;
import com.zhixing.entity.PassFaceBean;
import com.zhixing.entity.PassFaceBeanDao;
import com.zhixing.entity.PersonBean;
import com.zhixing.entity.PersonBeanDao;
import com.zhixing.entity.RecogeRecordBean;
import com.zhixing.entity.RecogeRecordBeanDao;
import com.zhixing.entity.config.FileConfig;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Sqlite管理类
 */
public class DaoManager {
    private static final String DB_NAME = "intelligent_terminal.db";

    private volatile static DaoManager sInstance = null;
    private DaoMaster.OpenHelper openHelper;
    private DaoMaster daoWriteMaster, daoReadMaster;

    private static Context mContext;

    private DaoManager() {
        openHelper = new DaoReleaseHelper(new ContextWrapper(mContext) {
            /**
             * 获得数据库路径，如果不存在，则创建对象对象
             *
             * @param name
             */
            @Override
            public File getDatabasePath(String name) {
                // 判断是否存在sd卡
                boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
                        .getExternalStorageState());
                if (!sdExist) {// 如果不存在,
                    Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
                    return null;
                } else {// 如果存在
                    // 获取sd卡路径
                    String dbDir = FileConfig.FILE_DATABASE;

                    String dbPath = dbDir + File.separator + name;// 数据库路径
                    // 判断目录是否存在，不存在则创建该目录
                    File dirFile = new File(dbDir);
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    // 数据库文件是否创建成功
                    boolean isFileCreateSuccess = false;
                    // 判断文件是否存在，不存在则创建该文件
                    File dbFile = new File(dbPath);
                    if (!dbFile.exists()) {
                        try {
                            isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        isFileCreateSuccess = true;
                    }
                    // 返回数据库文件对象
                    if (isFileCreateSuccess) {
                        return dbFile;
                    } else {
                        return super.getDatabasePath(name);
                    }
                }
            }

            /**
             * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
             *
             * @param name
             * @param mode
             * @param factory
             */
            @Override
            public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory
                    factory) {
                return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
            }

            /**
             * Android 4.0会调用此方法获取数据库。
             *
             * @see ContextWrapper#openOrCreateDatabase(String,
             *      int,
             *      SQLiteDatabase.CursorFactory,
             *      DatabaseErrorHandler)
             * @param name
             * @param mode
             * @param factory
             * @param errorHandler
             */
            @Override
            public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory
                    factory, DatabaseErrorHandler errorHandler) {
                return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
            }
        }, DB_NAME);
        daoWriteMaster = new DaoMaster(openHelper.getWritableDb());
        daoReadMaster = new DaoMaster(openHelper.getReadableDb());
    }

    public synchronized static DaoManager getInstance(Context context) {
        if (sInstance == null) {
            mContext = context.getApplicationContext();
            sInstance = new DaoManager();
        }
        return sInstance;
    }

    /**
     * 获取云从授权码
     *
     * @return
     */
    public CloudLicenceBean getCloudLicence() {
        CloudLicenceBeanDao dao = getReadSession().getCloudLicenceBeanDao();
        List<CloudLicenceBean> licenceBeans = dao.loadAll();
        return licenceBeans == null || licenceBeans.size() == 0 ? null : licenceBeans.get(0);
    }

    /**
     * 保存云从授权码
     *
     * @param licence
     */
    public void saveCloudLicence(String licence) {
        if (!TextUtils.isEmpty(licence)) {
            getWriteSession().getCloudLicenceBeanDao().deleteAll();
            CloudLicenceBean licenceBean = new CloudLicenceBean();
            licenceBean.setLicence(licence);
            getWriteSession().getCloudLicenceBeanDao().insert(licenceBean);
        }
    }

    /**
     * 根据人员编号获取人员信息
     *
     * @param personNo
     * @return
     */
    public PersonBean getPerson(String personNo) {
        PersonBeanDao personDao = getReadSession().getPersonBeanDao();
        return personDao.queryBuilder().where(PersonBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 根据人员姓名获取人员信息
     *
     * @return
     */
    public List<PersonBean> getPersonByName(String name) {
        PersonBeanDao personDao = getReadSession().getPersonBeanDao();
        return personDao.queryBuilder().where(PersonBeanDao.Properties.PersonName.eq(name)).list();
    }

    /**
     * 获取白名单人数
     *
     * @return
     */
    public long getWhitePersonNum() {
        PersonBeanDao personDao = getReadSession().getPersonBeanDao();
        List<PersonBean> personBeanList = personDao.queryBuilder().where(PersonBeanDao.Properties.White.eq(true)).list();
        return personBeanList == null ? 0 : personBeanList.size();
    }

    /**
     * 根据人员编号获取采集人员信息
     *
     * @param personNo
     * @return
     */
    public CollectPersonBean getCollecPerson(String personNo) {
        CollectPersonBeanDao personDao = getReadSession().getCollectPersonBeanDao();
        return personDao.queryBuilder().where(CollectPersonBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 插入人员信息
     *
     * @return
     */
    public void inserPerson(PersonBean newPersonBean) {
        PersonBeanDao personDao = getWriteSession().getPersonBeanDao();
        PersonBean personBean = personDao.queryBuilder().where(PersonBeanDao.Properties.PersonNo.eq(newPersonBean.getPersonNo())).unique();
        if (personBean != null) {
            newPersonBean.setWhite(personBean.isWhite());
            personDao.delete(personBean);
        }
        personDao.insert(newPersonBean);
    }

    /**
     * 删除人员
     */
    public void deletePerson(PersonBean person) {
        if (person != null) {
            PersonBeanDao personBeanDao = getWriteSession().getPersonBeanDao();
            personBeanDao.delete(person);
        }
    }

    /**
     * 删除白名单人员
     */
    public void deleteWhitePerson(PersonBean personBean) {
        personBean.setWhite(false);
        getWriteSession().getPersonBeanDao().update(personBean);
    }

    /**
     * 插入白名单人员
     */
    public void inserWhitePerson(PersonBean personBean) {
        personBean.setWhite(true);
        getWriteSession().getPersonBeanDao().update(personBean);
    }

    /**
     * 根据人员编号删除人员
     *
     * @param personNo
     */
    public void deletePersonByPersonNo(String personNo) {
        PersonBean person = getPerson(personNo);
        if (person != null) {
            PersonBeanDao personBeanDao = getWriteSession().getPersonBeanDao();
            personBeanDao.delete(person);
        }
    }

    /**
     * 插入采集人员信息
     *
     * @return
     */
    public void inserPerson(CollectPersonBean newPersonBean) {
        CollectPersonBeanDao personDao = getWriteSession().getCollectPersonBeanDao();
        CollectPersonBean personBean = personDao.queryBuilder().where(CollectPersonBeanDao.Properties.PersonNo.eq(newPersonBean.getPersonNo())).unique();
        if (personBean != null) {
            personDao.delete(personBean);
        }
        personDao.insert(newPersonBean);
    }

    /**
     * 根据特征路径获取人脸
     *
     * @return
     */
    public CloudFaceBean getFaceBeanByFacePath(String facePath) {
        CloudFaceBeanDao faceBeanDao = getReadSession().getCloudFaceBeanDao();
        return faceBeanDao.queryBuilder().where(CloudFaceBeanDao.Properties.FeaturePath.eq(facePath)).unique();
    }

    /**
     * 根据人员编号路径获取人脸
     *
     * @return
     */
    public CloudFaceBean getFaceBeanByPersonNo(String personNo) {
        CloudFaceBeanDao faceBeanDao = getReadSession().getCloudFaceBeanDao();
        return faceBeanDao.queryBuilder().where(CloudFaceBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 删除人脸
     */
    public void deleteFace(CloudFaceBean cloudFaceBean) {
        CloudFaceBeanDao faceBeanDao = getWriteSession().getCloudFaceBeanDao();
        faceBeanDao.delete(cloudFaceBean);
    }

    /**
     * 删除人脸
     */
    public void deleteFaceByPersonNo(String personNo) {
        CloudFaceBean cloudFaceBeanList = getReadSession().getCloudFaceBeanDao().queryBuilder().where(CloudFaceBeanDao.Properties.PersonNo.eq(personNo)).unique();
        if (cloudFaceBeanList != null) {
            CloudFaceBeanDao faceBeanDao = getWriteSession().getCloudFaceBeanDao();
            faceBeanDao.delete(cloudFaceBeanList);
        }
    }

    /**
     * 删除人脸
     */
    public void deleteFaceByFeatureId(String featureId) {
        CloudFaceBean cloudFaceBeanList = getReadSession().getCloudFaceBeanDao().queryBuilder().where(CloudFaceBeanDao.Properties.FeaturePath.eq(featureId)).unique();
        if (cloudFaceBeanList != null) {
            CloudFaceBeanDao faceBeanDao = getWriteSession().getCloudFaceBeanDao();
            faceBeanDao.delete(cloudFaceBeanList);
        }
    }

    /**
     * 根据人员编号获取采集人脸
     *
     * @param personNo
     * @return
     */
    public CollectFaceBean getCollectFaceInfo(String personNo) {
        CollectFaceBeanDao faceBeanDao = getReadSession().getCollectFaceBeanDao();
        return faceBeanDao.queryBuilder().where(CollectFaceBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 根据特征路径获取人员编号
     *
     * @param featurePath
     * @return
     */
    public String getPersonNoByPath(String featurePath) {
        CloudFaceBeanDao faceBeanDao = getReadSession().getCloudFaceBeanDao();
        CloudFaceBean cloudFaceBean = faceBeanDao.queryBuilder().where(CloudFaceBeanDao.Properties.FeaturePath.eq(featurePath)).unique();
        return cloudFaceBean == null ? "" : cloudFaceBean.getPersonNo();
    }

    /**
     * 根据特征路径获取采集人员编号
     *
     * @param featurePath
     * @return
     */
    public String getCollectPersonNoByPath(String featurePath) {
        CollectFaceBeanDao faceBeanDao = getReadSession().getCollectFaceBeanDao();
        CollectFaceBean faceBean = faceBeanDao.queryBuilder().where(CollectFaceBeanDao.Properties.FeaturePath.eq(featurePath)).unique();
        return faceBean == null ? "" : faceBean.getPersonNo();
    }

    /**
     * 插入人脸信息
     *
     * @return
     */
    public void inserFaceInfo(CloudFaceBean cloudFaceBean) {
        CloudFaceBeanDao faceBeanDao = getWriteSession().getCloudFaceBeanDao();
        CloudFaceBean cloudFaceBean1 = faceBeanDao.queryBuilder().where(CloudFaceBeanDao.Properties.PersonNo.eq(cloudFaceBean.getPersonNo())).unique();
        if (cloudFaceBean1 != null) {
            faceBeanDao.delete(cloudFaceBean1);
        }
        faceBeanDao.insert(cloudFaceBean);
    }

    /**
     * 插入人脸信息
     *
     * @return
     */
    public void updateFaceInfo(CloudFaceBean cloudFaceBean) {
        CloudFaceBeanDao faceBeanDao = getWriteSession().getCloudFaceBeanDao();
        if (cloudFaceBean != null) {
            faceBeanDao.update(cloudFaceBean);
        }
    }

    /**
     * 插入采集人脸信息
     *
     * @return
     */
    public void inserFaceInfo(CollectFaceBean faceBean) {
        CollectFaceBeanDao faceBeanDao = getWriteSession().getCollectFaceBeanDao();
        CollectFaceBean faceBean1 = faceBeanDao.queryBuilder().where(CollectFaceBeanDao.Properties.PersonNo.eq(faceBean.getPersonNo())).unique();
        if (faceBean1 != null) {
            faceBeanDao.delete(faceBean1);
        }
        faceBeanDao.insert(faceBean);
    }

    /**
     * 根据手机号查询采集人员信息
     */
    public CollectPersonBean getCollectPersonByPhone(String phone) {
        CollectPersonBeanDao dao = getReadSession().getCollectPersonBeanDao();
        return dao.queryBuilder().where(CollectPersonBeanDao.Properties.Phone.eq(phone)).unique();
    }

    /**
     * 根据输入条件模糊查询采集人员信息
     */
    public List<CollectPersonBean> getCollectPersonBySearchText(String searchText) {
        QueryBuilder queryBuilder = getReadSession().getCollectPersonBeanDao().queryBuilder();
        return queryBuilder.where(queryBuilder.or(CollectPersonBeanDao.Properties.Phone.like("%" + searchText + "%"),
                CollectPersonBeanDao.Properties.PersonName.like("%" + searchText + "%"), CollectPersonBeanDao.Properties.Code.like("%" + searchText + "%")))
                .list();
    }

    /**
     * 插入采集人员信息
     */
    public void insertCollectPerson(CollectPersonBean personBean) {
        CollectPersonBeanDao dao = getWriteSession().getCollectPersonBeanDao();
        CollectPersonBean collectPersonBean = getCollectPersonByPhone(personBean.getPhone());
        if (collectPersonBean != null) {
            dao.delete(collectPersonBean);
        }
        dao.insert(personBean);
    }

    /**
     * 根据人员编号查询卡
     */
    public CardBean getCardByPersonNo(String personNo) {
        CardBeanDao dao = getReadSession().getCardBeanDao();
        return dao.queryBuilder().where(CardBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 根据人员编号删除卡号
     *
     * @param personNo
     */
    public void deleteCardByPersonNo(String personNo) {
        CardBean cardBean = getCardByPersonNo(personNo);
        if (cardBean != null) {
            CardBeanDao cardBeanDao = getWriteSession().getCardBeanDao();
            cardBeanDao.delete(cardBean);
        }
    }

    /**
     * 根据卡号查询卡
     */
    public CardBean getCardByCardNo(String cardNo) {
        CardBeanDao dao = getReadSession().getCardBeanDao();
        return dao.queryBuilder().where(CardBeanDao.Properties.CardNumber.eq(cardNo)).unique();
    }

    /**
     * 根据人员编号查询采集卡
     */
    public CollectCardBean getCollecCardByPersonNo(String personNo) {
        CollectCardBeanDao dao = getReadSession().getCollectCardBeanDao();
        return dao.queryBuilder().where(CollectCardBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 根据卡号查询采集卡
     */
    public CollectCardBean getCollecCardByCardNo(String cardNo) {
        CollectCardBeanDao dao = getReadSession().getCollectCardBeanDao();
        return dao.queryBuilder().where(CollectCardBeanDao.Properties.CardNumber.eq(cardNo)).unique();
    }

    /**
     * 插入ic卡号
     *
     * @param cardBean
     */
    public void insertCard(CardBean cardBean) {
        CardBean card = getCardByPersonNo(cardBean.getPersonNo());
        CardBeanDao dao = getWriteSession().getCardBeanDao();
        if (card != null) {
            dao.delete(card);
        }
        dao.insert(cardBean);
    }

    /**
     * 更新卡号
     *
     * @param cardBean
     */
    public void updateCard(CardBean cardBean) {
        if (cardBean != null) {
            getWriteSession().getCardBeanDao().update(cardBean);
        }
    }

    /**
     * 插入ic卡号
     *
     * @param cardBean
     */
    public void insertCard(CollectCardBean cardBean) {
        CollectCardBean card = getCollecCardByPersonNo(cardBean.getPersonNo());
        CollectCardBeanDao dao = getWriteSession().getCollectCardBeanDao();
        if (card != null) {
            dao.delete(card);
        }
        dao.insert(cardBean);
    }

    /**
     * 通过人员编号获取指纹
     *
     * @param personNo
     * @return
     */
    public List<FingerBean> getFingerBeanByNo(String personNo) {
        FingerBeanDao fingerBeanDao = getReadSession().getFingerBeanDao();
        return fingerBeanDao.queryBuilder().where(FingerBeanDao.Properties.PersonNo.eq(personNo)).list();
    }

    /**
     * 根据指纹路径获取指纹
     *
     * @param fingerPath
     * @return
     */
    public FingerBean getFingerByFingerPath(String fingerPath) {
        FingerBeanDao dao = getReadSession().getFingerBeanDao();
        return dao.queryBuilder().where(FingerBeanDao.Properties.FingerPath.eq(fingerPath)).unique();
    }

    /**
     * 根据指纹获取人员信息
     *
     * @param fingerPath
     * @return
     */
    public PersonBean getPersonByFingerPath(String fingerPath) {
        FingerBean fingerBean = getFingerByFingerPath(fingerPath);
        if (fingerBean == null) {
            return null;
        } else {
            return getPerson(fingerBean.getPersonNo());
        }
    }

    /**
     * 通过人员编号获取采集指纹
     *
     * @param personNo
     * @return
     */
    public List<CollectFingerBean> getCollectFingerBeanByNo(String personNo) {
        CollectFingerBeanDao fingerBeanDao = getReadSession().getCollectFingerBeanDao();
        return fingerBeanDao.queryBuilder().where(CollectFingerBeanDao.Properties.PersonNo.eq(personNo)).list();
    }

    /**
     * 插入指纹
     */
    public void insertFinger(FingerBean fingerBean) {
        FingerBean fingerBean1 = getFingerByFingerPath(fingerBean.getFingerPath());
        FingerBeanDao dao = getWriteSession().getFingerBeanDao();
        if (fingerBean1 != null) {
            dao.delete(fingerBean1);
        }
        dao.insert(fingerBean);
    }

    /**
     * 删除指纹
     */
    public void deleteFinger(FingerBean fingerBean) {
        FingerBeanDao fingerBeanDao = getWriteSession().getFingerBeanDao();
        fingerBeanDao.delete(fingerBean);
    }

    /**
     * 删除指纹
     */
    public void deleteFingerByPersonNo(String personNo) {
        List<FingerBean> fingerBeanList = getReadSession().getFingerBeanDao().queryBuilder().where(FingerBeanDao.Properties.PersonNo.eq(personNo)).list();
        FingerBeanDao fingerBeanDao = getWriteSession().getFingerBeanDao();
        for (FingerBean fingerBean : fingerBeanList) {
            fingerBeanDao.delete(fingerBean);
        }
    }

    /**
     * 插入采集指纹
     *
     * @param fingerBean
     */
    public void insertFinger(CollectFingerBean fingerBean) {
        CollectFingerBeanDao dao = getWriteSession().getCollectFingerBeanDao();
        dao.insert(fingerBean);
    }

    /**
     * 插入本地历史消费记录
     *
     * @param historyConsume
     */
    public void insertHistoryConume(HistoryConsumeBean historyConsume) {
        HistoryConsumeBeanDao dao = getWriteSession().getHistoryConsumeBeanDao();
        dao.insert(historyConsume);
    }

    /**
     * 插入本地历史消费记录
     */
    public HistoryConsumeBean getHistoryConumeById(String id) {
        HistoryConsumeBeanDao dao = getReadSession().getHistoryConsumeBeanDao();
        return dao.load(Long.parseLong(id));
    }

    /**
     * 查询1条消费记录
     *
     * @return
     */
    public List<HistoryConsumeBean> getHistoryConsume() {
        return getReadSession().getHistoryConsumeBeanDao().queryBuilder().limit(1).list();
    }

    /**
     * 删除历史消费记录
     *
     * @param historyConsume
     */
    public void deleteHistoryConsume(HistoryConsumeBean historyConsume) {
        getWriteSession().getHistoryConsumeBeanDao().delete(historyConsume);
    }

    /**
     * 删除历史消费记录
     */
    public void deleteHistoryConsumeByid(String id) {
        HistoryConsumeBean consume = getReadSession().getHistoryConsumeBeanDao().queryBuilder().where(HistoryConsumeBeanDao.Properties.ConsumeId.eq(id)).unique();
        if (consume != null) {
            getWriteSession().getHistoryConsumeBeanDao().delete(consume);
        }
    }

    /**
     * 根据faceToken获取人脸信息
     */
    public void insertPassFace(PassFaceBean passFaceBean) {
        getWriteSession().getPassFaceBeanDao().insert(passFaceBean);
    }

    /**
     * 根据faceToken获取人脸信息
     */
    public void updatePassFace(PassFaceBean passFaceBean) {
        getWriteSession().getPassFaceBeanDao().update(passFaceBean);
    }

    /**
     * 根据faceId删除人脸信息
     */
    public void deletePassFaceByFaceId(String faceId) {
        PassFaceBean passFaceBean = getPassFaceByFaceId(faceId);
        if (passFaceBean != null) {
            getWriteSession().getPassFaceBeanDao().delete(passFaceBean);
        }
    }

    /**
     * 根据faceToken获取人脸信息
     */
    public PassFaceBean getPassFaceByFaceToken(String faceToken) {
        return getReadSession().getPassFaceBeanDao().queryBuilder().where(PassFaceBeanDao.Properties.FaceToken.eq(faceToken)).unique();
    }

    /**
     * 根据faceId获取人脸信息
     */
    public PassFaceBean getPassFaceByFaceId(String faceId) {
        return getReadSession().getPassFaceBeanDao().queryBuilder().where(PassFaceBeanDao.Properties.FaceId.eq(faceId)).unique();
    }

    /**
     * 根据personNo获取人脸信息
     */
    public PassFaceBean getPassFaceByPersonNo(String personNo) {
        return getReadSession().getPassFaceBeanDao().queryBuilder().where(PassFaceBeanDao.Properties.PersonNo.eq(personNo)).unique();
    }

    /**
     * 根据faceToken获取旷视人员信息
     */
    public PersonBean getPersonByFaceToken(String faceToken) {
        return getReadSession().getPersonBeanDao().queryBuilder().where(PersonBeanDao.Properties.PersonNo
                .eq(getPassFaceByFaceToken(faceToken).getPersonNo())).unique();
    }

    /**
     * 插入识别记录
     */
    public void inserRecogeRecord(RecogeRecordBean record) {
        if (record != null) {
            getWriteSession().getRecogeRecordBeanDao().insert(record);
        }
    }

    /**
     * 获取识别记录
     */
    public RecogeRecordBean getRecogeRecord() {
        List<RecogeRecordBean> datas = getReadSession().getRecogeRecordBeanDao().loadAll();
        if (datas != null && !datas.isEmpty()) {
            return datas.get(0);
        }
        return null;
    }

    /**
     * 根据识别记录Id删除记录
     */
    public void delOffIdentifyRecordById(String identifyId) {
        RecogeRecordBean datas = getReadSession().getRecogeRecordBeanDao().queryBuilder().where(RecogeRecordBeanDao.Properties.IdentifyId.eq(identifyId)).unique();
        if (datas != null) {
            getWriteSession().getRecogeRecordBeanDao().delete(datas);
        }
    }

    /**
     * 清空人脸、指纹、人员、卡数据
     */
    public void clear() {
        getWriteSession().getCloudFaceBeanDao().deleteAll();
        getWriteSession().getCardBeanDao().deleteAll();
        getWriteSession().getFingerBeanDao().deleteAll();
        getWriteSession().getPersonBeanDao().deleteAll();
        getWriteSession().getPassFaceBeanDao().deleteAll();
    }

    public DaoSession getWriteSession() {
        return daoWriteMaster.newSession();
    }

    public DaoSession getReadSession() {
        return daoReadMaster.newSession();
    }

}
