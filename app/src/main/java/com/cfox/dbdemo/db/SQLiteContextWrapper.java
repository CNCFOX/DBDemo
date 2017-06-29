package com.cfox.dbdemo.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

/**
 * Created by machao14 on 2017/6/29.
 */

public class SQLiteContextWrapper extends ContextWrapper {
    public SQLiteContextWrapper(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if(!sdExist){//如果不存在,
            return null;
        }else{//如果存在
            //获取sd卡路径
            String dbDir= "";//FileUtils.getFlashBPath();
            dbDir += "DB";//数据库所在目录
            String dbPath = dbDir+"/"+name;//数据库路径
            //判断目录是否存在，不存在则创建该目录
            File dirFile = new File(dbDir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }

            //数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            //判断文件是否存在，不存在则创建该文件
            File dbFile = new File(dbPath);
            if(!dbFile.exists()){
                try {
                    isFileCreateSuccess = dbFile.createNewFile();//创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                isFileCreateSuccess = true;
            }

            //返回数据库文件对象
            if(isFileCreateSuccess){
                return dbFile;
            }else{
                return null;
            }
        }


        //return super.getDatabasePath(name);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return super.openOrCreateDatabase(name, mode, factory, errorHandler);
    }
}
