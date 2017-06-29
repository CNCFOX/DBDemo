package com.cfox.dbdemo.dbcore;

/**
 * Created by machao14 on 2017/6/29.
 */

public interface IBaseDao<T> {

    long insert(T t);
}
