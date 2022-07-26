package com.Captain.web.prject.service.impl;

import com.Captain.web.prject.dao.user.UserDao;
import com.Captain.web.prject.dao.user.impl.UserDaoImpl;
import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.service.UserService;
import com.Captain.web.prject.utils.DataSourceUtil;
import org.junit.Test;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/24 11:03
 * description:
 */
public class UserServiceImpl implements UserService {
    //引入业务层
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String userCode, String userPassword) {
        return userDao.getLoginUser(userCode, userPassword);
    }

    @Override
    public boolean updatePwd(int id, String userPassword) {
        boolean flag = false;

        if(userDao.updatePwd(id, userPassword) > 0){
             flag = true;
         }
        return flag;
    }

    @Override
    public long getUserCount(String userName, int userRole) {
        return userDao.getUserCount(userName, userRole);
    }

    @Override
    public List<User> getUserList(String userName, int userRole, long currentPageNo, long pageSize) {
        return userDao.getUserList(userName, userRole, currentPageNo, pageSize);
    }

    @Override
    public boolean userAdd(User user) {
        boolean flag = false;

        try {
            DataSourceUtil.begin();//开启事务
            int updateRows = userDao.userAdd(user);
            DataSourceUtil.commit();//提交事务
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else {
                System.out.println("add failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                DataSourceUtil.rollback();//事务回滚
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public User selectUserCodeExist(String userCode, String userPassword) {
        return userDao.getLoginUser(userCode, userPassword);
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public boolean userDelete(int id) {
        boolean flag = false;

        if(userDao.userDeleteById(id) > 0) flag = true;

        return flag;
    }

    @Override
    public boolean modify(User user) {
        boolean flag = false;

        if(userDao.modify(user) > 0) flag = true;

        return flag;
    }

    @Test
    public void test(){
//        UserServiceImpl userService = new UserServiceImpl();
//        Long userCount = userService.getUserCount(null,1);
//        System.out.println(userCount);



    }
}
