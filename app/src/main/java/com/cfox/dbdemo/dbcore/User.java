package com.cfox.dbdemo.dbcore;

/**
 * Created by machao14 on 2017/6/29.
 */

@DBTable(value = "tb_user")
public class User {


    @DBField(value = "tb_name")
    public String name;
    @DBField(value = "tb_sex")
    public String age;

    public User(String age, String name) {
        this.age = age;
        this.name = name;
    }
}
