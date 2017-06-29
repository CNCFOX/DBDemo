package com.cfox.dbdemo.dbcore;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by machao14 on 2017/6/29.
 */
public class BaseDaoManager {
    private static BaseDaoManager ourInstance = new BaseDaoManager();

    private SQLiteDatabase sqLiteDatabase;

    public static BaseDaoManager getInstance() {
        return ourInstance;
    }

    private BaseDaoManager() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
                new File(Environment.getExternalStorageDirectory(),"mydb.db"),null);

    }


    public synchronized <T extends AbstractBaseDao<M>, M> T getBaseDao(Class<T> baseDaoClass , Class<M> entityClass) {

        AbstractBaseDao baseDao = null;
        try {
            baseDao = baseDaoClass.newInstance();
            baseDao.init(sqLiteDatabase,entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) baseDao;
    }
}
