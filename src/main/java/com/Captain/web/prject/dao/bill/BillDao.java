package com.Captain.web.prject.dao.bill;

import com.Captain.web.prject.domain.Bill;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 9:50
 * description:
 */
public interface BillDao {
    //获取订单总数
    public long getBillCount(String productName, int providerId, int isPayment);
    //获取订单列表
    public List<Bill> getBillList(String productName, int providerId, int isPayment);
    //添加订单
    public int addBill(Bill bill);
    //删除订单
    public int deleteBillById(int id);
    //通过ID获取订单数据
    public Bill getBillById(String id);
    //修改订单信息
    public int modify(Bill bill);
}
