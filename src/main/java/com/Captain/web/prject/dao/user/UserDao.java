package com.Captain.web.prject.dao.user;

import com.Captain.web.prject.domain.User;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/24 10:25
 * description:
 */
public interface UserDao {
    //得到要登陆的用户
    public User getLoginUser(String userCode, String userPassword);

    //修改当前用户密码
    public int updatePwd(int id, String userPassword);

    //用户管理--查询用户总数
    public long getUserCount(String userName, int userRole);

    //获取用户列表并分页
    public List<User> getUserList(String userName, int userRole, long currentPageNo, long pageSize);

    //添加用户
    public int userAdd(User user);

    //通过ID获取用户数据
    public User getUserById(String id);

    //删除用户
    public int userDeleteById(int id);

    //修改用户信息
    public  int modify(User user);
}
