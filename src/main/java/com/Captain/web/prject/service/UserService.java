package com.Captain.web.prject.service;

import com.Captain.web.prject.domain.User;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/24 11:01
 * description:
 */
public interface UserService {
    //用户登录
    public User login(String userCode, String userPassword);
    //根据用户ID修改密码
    public boolean updatePwd(int id, String userPassword);
    //查询记录数
    public long getUserCount(String userName, int userRole);
    //根据条件查询用户列表
    public List<User> getUserList(String userName, int userRole, long currentPageNo, long pageSize);
    //添加用户
    public boolean userAdd(User user);
    //判断后台数据是否存在
    public User selectUserCodeExist(String userCode, String userPassword);
    //通过ID获取用户数据
    public User getUserById(String id);
    //删除用户
    public boolean userDelete(int id);
    //修改用户信息
    public boolean modify(User user);
}
