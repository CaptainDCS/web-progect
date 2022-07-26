package com.Captain.web.prject.dao.user.impl;

import com.Captain.web.prject.dao.user.UserDao;
import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.utils.DataSourceUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/24 10:37
 * description:
 */
public class UserDaoImpl implements UserDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    @Override
    public User getLoginUser(String userCode, String userPassword) {
        String sql = "select * from smbms_user where userCode = ? and userPassword = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), userCode, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public int updatePwd(int id, String userPassword) {
        String sql = "update smbms_user set userPassword = ? where id = ?";

        try {
            return queryRunner.update(sql, userPassword, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getUserCount(String userName, int userRole) {
        StringBuilder sql = new StringBuilder();
        //查询用户记录总数
        sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole = r.id");
        ArrayList<Object> list = new ArrayList<>();

        //动态SQL查询
        if(!StringUtils.isNullOrEmpty(userName)){
            //通过用户名进行模糊/精准查询
            sql.append(" and u.userName like ?");
            list.add("%"+userName+"%");
        }
        if(userRole > 0){
            //通过用户角色进行精准查询
            sql.append(" and u.userRole = ?");
            list.add(userRole);
        }
        //将集合转化为数组
        Object[] params = list.toArray();
//        System.out.println(sql.toString());

        try {
            //ScalarHandler<Long>()--用于查询表记录总数返回一个long类型数值
           return queryRunner.query(sql.toString(), new ScalarHandler<Long>(), params);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getUserList(String userName, int userRole, long currentPageNo, long pageSize) {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.*, r.roleName as userRoleName from smbms_user u, smbms_role r where u.userRole = r.id");
        List<Object> list = new ArrayList<>();

        //动态SQL查询
        if(!StringUtils.isNullOrEmpty(userName)){
            sql.append(" and u.userName like ?");
            list.add("%"+userName+"%");
        }
        if(userRole > 0){
            sql.append(" and u.userRole = ?");
            list.add(userRole);
        }

        //分页
        sql.append(" order by creationDate DESC limit ?,?");
        currentPageNo = (currentPageNo -1)*pageSize;
        list.add(currentPageNo);
        list.add(pageSize);

        Object[] params = list.toArray();
//        System.out.println(sql.toString());

        try {
            return queryRunner.query(sql.toString(), new BeanListHandler<>(User.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int userAdd(User user) {
        String sql = "insert into smbms_user (userCode,userName,userPassword,gender,birthday,phone,address," +
                "userRole,createdBy,creationDate) " +
                "values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(),
                           user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(),
                           user.getCreatedBy(), user.getCreationDate()};

        try {
            //使用DButils工具类的execute方法进行插入操作
            return queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(String id) {
        String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int userDeleteById(int id) {
        String sql = "delete from smbms_user where id = ?";
        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int modify(User user) {
        String sql =  "update smbms_user set userName=?,"+
                      "gender=?,birthday=?,phone=?,address=?,userRole=?," +
                      "modifyBy=?,modifyDate=? where id = ? ";
        Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(), user.getPhone(),
                           user.getAddress(), user.getUserRole(), user.getModifyBy(), user.getModifyDate(),
                           user.getId()};
        try {
            return queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }











}
