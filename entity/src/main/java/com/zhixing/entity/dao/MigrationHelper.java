package com.zhixing.entity.dao;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;


import com.zhixing.entity.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 作者：Deng Donglin
 * 时间：2017/7/21 0021 17:26
 * 描述：数据库更新类
 */
public class MigrationHelper {

    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH " +
            "THE CURRENT PARAMETERS";

    public static MigrationHelper getInstance() {
        return NestHolder.INSTANCE;
    }

    private static class NestHolder {
        private static MigrationHelper INSTANCE = new MigrationHelper();
    }

    /**
     * 备份和恢复数据 数据迁移
     *
     * @param db
     * @param daoClasses
     */
    public void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        DaoMaster.dropAllTables(db, true);//把所有的表，都删掉
        DaoMaster.createAllTables(db, false);//创建所有的表
        restoreData(db, daoClasses);//恢复数据
    }


    public void findRecentMessage(Database db, int belongToSbId) {
        String insertSql = "insert  into  HISTORY_MESSAGE (TIME,MESSAGE_TYPE,UID,MSG,SEND_OR_RECEIVE_ID,MEMBER_ID," +
                "IS_HIDE,FILE_TYPE,STATUS) ";
        String sql = insertSql + "select MESSAGE_PACKET.TIME ,MESSAGE_PACKET.MESSAGE_TYPE " +
                " ,MESSAGE_PACKET.UID  ,MESSAGE_PACKET.MSG ,MESSAGE_PACKET.SEND_OR_RECEIVE_ID " +
                ",MESSAGE_PACKET.MEMBER_ID  ,\'false\' " +
                ",MESSAGE_PACKET.FILE_TYPE ,\'0\'  from MESSAGE_PACKET, " +
                "(select max(_id) maxId from MESSAGE_PACKET where UID = " + belongToSbId +
                " group by SEND_OR_RECEIVE_ID ) t1 where t1.maxId= MESSAGE_PACKET._id order by MESSAGE_PACKET.TIME " +
                "desc";
        db.execSQL(sql);


    }


    /**
     * 备份数据
     *
     * @param db
     * @param daoClasses
     */
    private void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

            String divider = "";
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            ArrayList<String> properties = new ArrayList<>();

            StringBuilder createTableStringBuilder = new StringBuilder();

            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

            for (int j = 0; j < daoConfig.properties.length; j++) {//添加字段和它的类型
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName);//保存列名

                    String type = null;

                    try {
                        type = getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception exception) {
                    }

                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);//加上列名和类型

                    if (daoConfig.properties[j].primaryKey) {
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }

                    divider = ",";
                }
            }
            createTableStringBuilder.append(");");

            db.execSQL(createTableStringBuilder.toString());//创建sql表的语句

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tableName).append(";");

            db.execSQL(insertTableStringBuilder.toString());//把另外表的数据填入新表
        }
    }

    /**
     * 恢复数据
     *
     * @param db
     * @param daoClasses
     */
    private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            ArrayList<String> properties = new ArrayList();

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

            StringBuilder dropTableStringBuilder = new StringBuilder();

            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);

            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    /**
     * 根据类型返回我想要的
     *
     * @param type
     * @return
     * @throws Exception 其他类型
     */
    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type
                .toString()));
        throw exception;
    }

    /**
     * 根据表的每一个列
     *
     * @param db
     * @param tableName
     * @return
     */
    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);// limit 1 找到一条就不会往下走，提高效率
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }
}
