package com.cfox.dbdemo.dbcore;

/**
 * Created by machao14 on 2017/6/29.
 */

public class UserDao extends AbstractBaseDao<User> {
    @Override
    public String createTable() {
        return "create table if not exists tb_user (tb_name varchar(20) , tb_sex varchar(20))";
    }
}
