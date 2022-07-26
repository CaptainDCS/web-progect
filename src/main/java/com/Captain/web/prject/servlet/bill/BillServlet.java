package com.Captain.web.prject.servlet.bill;
/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 9:53
 * description:
 */

import com.Captain.web.prject.domain.Bill;
import com.Captain.web.prject.domain.Provider;
import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.service.BillService;
import com.Captain.web.prject.service.ProviderService;
import com.Captain.web.prject.service.impl.BillServiceImpl;
import com.Captain.web.prject.service.impl.ProviderServiceImpl;
import com.Captain.web.prject.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jsp/bill.do")
public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if(method.equals("query") && method!=null){
            this.getBillList(request, response);
        }else if(method.equals("add") && method!=null){
            this.addBill(request, response);
        }else if(method.equals("getproviderlist") && method!=null){
            this.getProviderList(request, response);
        }else if(method.equals("delbill") && method!=null){
            this.deleteBillById(request, response);
        }else if(method.equals("modify") && method!=null){
            this.getBillId(request,response,"billmodify.jsp");
        }else if(method.equals("modifysave") && method!=null){
            this.modify(request,response);
        }else  if(method.equals("view") && method!=null){
            this.getBillId(request, response, "billview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //获取订单列表
    private void getBillList(HttpServletRequest request, HttpServletResponse response){
        String queryProductName = request.getParameter("queryProductName");//订单名称
        String temp1 = request.getParameter("queryProviderId");//供货商ID
        String temp2 = request.getParameter("queryIsPayment");//付款状态
        //设置默认值“--请选择--”
        int queryProviderId = 0;
        int queryIsPayment = 0;

        //获取订单列表
        BillService billService = new BillServiceImpl();
        //获取供应商列表
        ProviderService providerService = new ProviderServiceImpl();


        if(queryProductName == null){
            queryProductName = "";
        }
        if(temp1!=null && !temp1.equals("")){
            queryProviderId = Integer.parseInt(temp1);
        }
        if(temp2!=null && !temp2.equals("")){
            queryIsPayment = Integer.parseInt(temp2);
        }



        //获取订单列表展示
        List<Bill> billList = billService.getBillList(queryProductName, queryProviderId, queryIsPayment);
        request.setAttribute("billList", billList);
        request.setAttribute("queryProductName", queryProductName);
        request.setAttribute("queryIsPayment", queryIsPayment);

        //获取供货商列表展示
        List<Provider> providerList = providerService.getProviderList(null,null);
        request.setAttribute("providerList", providerList);
        request.setAttribute("queryProviderId", queryProviderId);

        try {
            request.getRequestDispatcher("billlist.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //获取供应商列表
    private void getProviderList(HttpServletRequest request, HttpServletResponse response){
        List<Provider> providerList = null;
        ProviderService providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList(null, null);
        //把roleList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
            outPrintWriter.write(JSONArray.toJSONString(providerList));
            outPrintWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outPrintWriter.close();
        }
    }
    //添加订单
    private void addBill(HttpServletRequest request, HttpServletResponse response){
        String billCode = request.getParameter("billCode");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");

        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());

        BillService billService = new BillServiceImpl();
        if(billService.addBill(bill)){
            try {
                response.sendRedirect(request.getContextPath()+"/jsp/bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                request.getRequestDispatcher("billadd.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    //删除订单
    private void deleteBillById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String billid = request.getParameter("billid");
        Integer delId = 0;
        try {
            delId = Integer.parseInt(billid);
        } catch (Exception e) {
            delId = 0;
        }

        Map<String, String> resultMap = new HashMap<>();
        if(delId < 0){
            resultMap.put("delResult", "notexist");
        }else {
            BillService billService = new BillServiceImpl();
            if(billService.deleteBill(delId)){
                resultMap.put("delResult", "true");
            }else {
                resultMap.put("delResult", "false");
            }
        }

        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    //获取订单ID
    private void getBillId(HttpServletRequest request, HttpServletResponse response, String url){
        String id = request.getParameter("billid");
        if(!StringUtils.isNullOrEmpty(id)){
            BillService billService = new BillServiceImpl();
            Bill bill = billService.getBillById(id);
            request.setAttribute("bill", bill);
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //修改订单信息
    private void modify(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");
        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setId(Integer.valueOf(id));
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());

        BillService billService = new BillServiceImpl();
        if(billService.modify(bill)){
            try {
                response.sendRedirect(request.getContextPath()+"/jsp/bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                request.getRequestDispatcher("billmodify.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}