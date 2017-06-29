package com.cfox.dbdemo.dbcore;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.R.attr.key;

/**
 * Created by machao14 on 2017/6/29.
 */

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase sqLiteDatabase;
    private Class entityClass;
    private boolean init = false;

    private String tableName;

    private Map<String ,Field> cachMap;

    public void init(SQLiteDatabase sqLiteDatabase , Class entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;

        if (!init) {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.execSQL(createTable());
            }

            DBTable dbTable = (DBTable) entityClass.getAnnotation(DBTable.class);

            if (dbTable != null) {
                tableName = dbTable.value();
            }

            iniCachMap();
            init = true;
        }
    }

    private void iniCachMap() {
        cachMap = new HashMap<>();

        String sql = "select * from " + tableName + " limit 1,0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        String[] columnNames = cursor.getColumnNames();
        Field[] fields = entityClass.getDeclaredFields();

        for (String columnName : columnNames) {
            Field columField = null;
            for (Field field : fields) {
                DBField dbField = field.getAnnotation(DBField.class);
                if (dbField != null) {
                    if (dbField.value().equals(columnName)) {
                        columField = field;
                        break;
                    }
                }
            }

            if (columField != null) {
                cachMap.put(columnName,columField);
            }
        }
        cursor.close();
    }


    @Override
    public long insert(T t) {

        Map<String,String> hashMap = getMap(t);

        ContentValues contentValues = getContentValues(hashMap);

        long result = sqLiteDatabase.insert(tableName,null,contentValues);

        return result;
    }

    private Map<String, String> getMap(T t) {

        Map<String,String> tabMap = new HashMap<>();

        Iterator iterator = cachMap.values().iterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            String key = null;
            String value = null;
            key = field.getAnnotation(DBField.class).value();
            try {

                if (field.get(t) == null) {
                    continue;
                }

                value = field.get(t).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value != null) {
                tabMap.put(key,value);
            }

        }



        return tabMap;
    }

    private ContentValues getContentValues(Map<String, String> hashMap) {

        ContentValues contentValues = new ContentValues();
        Iterator iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = hashMap.get(key);

            contentValues.put(key,value);
        }
        return contentValues;
    }

    public abstract String createTable();

    public String createTables() {

        StringBuffer  createTabSql = new StringBuffer();
        createTabSql.append("create table if not exists " + tableName + " (");

        Iterator iterator = cachMap.values().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            createTabSql.append(key).append(" varchar(20)").append(",");
        }

        createTabSql.deleteCharAt(createTabSql.lastIndexOf(","));
        createTabSql.append(")");

        Log.i("","create sql:" + createTabSql.toString());

        return createTabSql.toString();

    }
}
