package com.nuls.io.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuls.io.dao.UserDao;
import com.nuls.io.model.entity.User;
import com.nuls.io.service.UserService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {

        return userDao.insert(user.getUserName(),user.getPassword(),user.getPhone());
    }

   
}