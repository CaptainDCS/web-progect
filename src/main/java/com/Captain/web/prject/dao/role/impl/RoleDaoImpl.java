package com.Captain.web.prject.dao.role.impl;

import com.Captain.web.prject.dao.role.RoleDao;
import com.Captain.web.prject.domain.Role;
import com.Captain.web.prject.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/25 19:40
 * description:
 */
public class RoleDaoImpl implements RoleDao {
    QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    @Override
    public List<Role> getRoleList() {
        String sql = "select id, roleCode, roleName from smbms_role";

        try {
            return queryRunner.query(sql, new BeanListHandler<>(Role.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
