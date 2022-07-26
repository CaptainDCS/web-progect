package com.Captain.web.prject.service;

import com.Captain.web.prject.domain.Bill;

import java.util.List;

public interface BillService {
    //获取订单总数
    public long getBillCount(String productName, int providerId, int isPayment);
    //获取订单列表
    public List<Bill> getBillList(String productName, int providerId, int isPayment);
    //添加订单
    public boolean addBill(Bill bill);
    //删除用户
    public boolean deleteBill(int id);
    //通过ID获取订单数据
    public Bill getBillById(String id);
    //修改订单数据
    public boolean modify(Bill bill);
}
