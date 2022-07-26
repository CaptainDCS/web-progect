package com.Captain.web.prject.dao.bill.impl;

import com.Captain.web.prject.dao.bill.BillDao;
import com.Captain.web.prject.domain.Bill;
import com.Captain.web.prject.utils.DataSourceUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 9:50
 * description:
 */
public class BillDaoImpl implements BillDao {
    QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    @Override
    public long getBillCount(String productName, int providerId, int isPayment) {
        StringBuilder sql = new StringBuilder();
        List<Object> list = new ArrayList<>();

        sql.append("select count(1) as count from smbms_bill b, smbms_provider p where b.providerId = p.id");
        if(!StringUtils.isNullOrEmpty(productName)){
            sql.append(" and productName = ?");
            list.add("%"+productName+"%");
        }
        if(isPayment > 0){
            sql.append(" and isPayment = ?");
            list.add(isPayment);
        }
        if(providerId > 0){
            sql.append(" and providerId = ?");
            list.add(providerId);
        }

        Object[] params = list.toArray();
        try {
            return queryRunner.query(sql.toString(), new ScalarHandler<Long>(), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment) {
        StringBuilder sql = new StringBuilder();
        List<Object> list = new ArrayList<>();

        sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id");
        if(!StringUtils.isNullOrEmpty(productName)){
            sql.append(" and productName like ?");
            list.add("%"+ productName +"%");
        }
        if(providerId > 0){
            sql.append(" and providerId = ?");
            list.add(providerId);
        }
        if(isPayment > 0){
            sql.append(" and isPayment = ?");
            list.add(isPayment);
        }

        Object[] params = list.toArray();
//        System.out.println(sql.toString());

        try {
            return queryRunner.query(sql.toString(), new BeanListHandler<>(Bill.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addBill(Bill bill) {
        String sql = "insert into smbms_bill(billCode,productName,productDesc," +
                     "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate)" +
                     "values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(), bill.getProductUnit(),
                           bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(), bill.getProviderId(),
                           bill.getCreatedBy(), bill.getCreationDate()};
        try {
            return queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteBillById(int id) {
        String sql = "delete from smbms_bill where id = ?";

        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bill getBillById(String id) {
        String sql = "select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id and b.id = ?";

        try {
            return queryRunner.query(sql, new BeanHandler<>(Bill.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int modify(Bill bill) {
        String sql = "update smbms_bill set productName=?,productDesc=?,productUnit=?,productCount=?," +
                     "totalPrice=?,isPayment=?,providerId=?,modifyBy=?,modifyDate=? where id = ?";
        Object[] params = {bill.getProductName(),bill.getProductDesc(),bill.getProductUnit(),bill.getProductCount(),
                           bill.getTotalPrice(),bill.getIsPayment(),bill.getProviderId(),bill.getModifyBy(),
                           bill.getModifyDate(),bill.getId()};
        try {
            return queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
