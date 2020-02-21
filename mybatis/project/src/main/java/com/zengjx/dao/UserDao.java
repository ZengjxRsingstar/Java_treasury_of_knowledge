package com.zengjx.dao;



import com.zengjx.domain.User;

import java.util.List;

public interface UserDao {
    List<User> queryAll();

    /*User findById(Integer id);*/
}
