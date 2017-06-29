package com.cfox.dbdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cfox.dbdemo.dbcore.BaseDaoManager;
import com.cfox.dbdemo.dbcore.User;
import com.cfox.dbdemo.dbcore.UserDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insert(View view) {

        User user = new User("张三", "男");


        UserDao userDao = BaseDaoManager.getInstance().getBaseDao(UserDao.class,User.class);
        userDao.insert(user);



    }
}
