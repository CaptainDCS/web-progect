package com.Captain.web.prject.dao.provider.impl;

import com.Captain.web.prject.dao.provider.ProviderDao;
import com.Captain.web.prject.domain.Provider;
import com.Captain.web.prject.utils.DataSourceUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 13:05
 * description:
 */
public class ProviderDaoImpl implements ProviderDao {
    QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    @Override
    public List<Provider> getProviderList(String proName, String proCode) {
        StringBuilder sql = new StringBuilder();
        List<Object> list = new ArrayList<>();

        sql.append("select * from smbms_provider");
        if(!StringUtils.isNullOrEmpty(proName)){
            sql.append(" where proName like ?");
            list.add("%"+ proName +"%");
        }
        if(!StringUtils.isNullOrEmpty(proCode)){
            sql.append(" where proCode like ?");
            list.add("%"+ proCode +"%");
        }

        Object[] params = list.toArray();

        try {
            return queryRunner.query(sql.toString(), new BeanListHandler<>(Provider.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addProvider(Provider provider) {
        String sql = "insert into smbms_provider(proCode, proName, proContact, proPhone, " +
                     "proAddress, proFax, proDesc, createdBy, creationDate)" +
                     "values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {provider.getProCode(), provider.getProName(), provider.getProContact(), provider.getProPhone(),
                           provider.getProAddress(), provider.getProFax(), provider.getProDesc(), provider.getCreatedBy(),
                           provider.getCreationDate()};

        try {
            return queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteProvider(int id) {
        String sql = "delete from smbms_provider where id = ?";
        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Provider getProviderById(String id) {
        String sql = "select * from smbms_provider where id = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Provider.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}






