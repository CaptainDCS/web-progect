package com.Captain.web.prject.service.impl;

import com.Captain.web.prject.dao.bill.BillDao;
import com.Captain.web.prject.dao.bill.impl.BillDaoImpl;
import com.Captain.web.prject.domain.Bill;
import com.Captain.web.prject.service.BillService;
import com.Captain.web.prject.utils.DataSourceUtil;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 9:53
 * description:
 */
public class BillServiceImpl implements BillService {
    private BillDao billDao = new BillDaoImpl();

    @Override
    public long getBillCount(String productName, int providerId, int isPayment) {
        return billDao.getBillCount(productName, providerId, isPayment);
    }

    @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment) {
        return billDao.getBillList(productName, providerId, isPayment);
    }

    @Override
    public boolean addBill(Bill bill) {
        boolean flag = false;
        try {
            DataSourceUtil.begin();//开启事务
            int updateRows = billDao.addBill(bill);
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
    public boolean deleteBill(int id) {
        boolean flag = false;
        if(billDao.deleteBillById(id) > 0) flag = true;
        return flag;
    }

    @Override
    public Bill getBillById(String id) {
        return billDao.getBillById(id);
    }

    @Override
    public boolean modify(Bill bill) {
        boolean flag = false;
        if(billDao.modify(bill) > 0) flag = true;
        return flag;
    }

}
