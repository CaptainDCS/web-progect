package com.Captain.web.prject.servlet.proviser;
/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/31 11:54
 * description:
 */

import com.Captain.web.prject.domain.Provider;
import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.service.ProviderService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jsp/provider.do")
public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if(method.equals("query") && method!=null){
            this.getProviderList(request, response);
        }else if(method.equals("add") && method!=null){
            this.addProvider(request, response);
        }else if(method.equals("delprovider") && method!=null){
            this.deleteProvider(request, response);
        }else if(method.equals("view") && method!=null){
            this.getProviderById(request,response, "providerview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //获取供货商列表
    private void getProviderList(HttpServletRequest request, HttpServletResponse response){
        String queryProCode = request.getParameter("queryProCode");
        String queryProName = request.getParameter("queryProName");

        ProviderService providerService = new ProviderServiceImpl();
        if(queryProCode == null){
            queryProCode = "";
        }
        if(queryProName == null){
            queryProName = "";
        }

        List<Provider> providerList = providerService.getProviderList(queryProName, queryProCode);
        request.setAttribute("providerList", providerList);
        request.setAttribute("queryProCode", queryProCode);
        request.setAttribute("queryProName", queryProName);

        try {
            request.getRequestDispatcher("providerlist.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //添加供货商
    private void addProvider(HttpServletRequest request, HttpServletResponse response){
        String proCode = request.getParameter("proCode");
        String proName = request.getParameter("proName");
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);
        provider.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        ProviderService providerService = new ProviderServiceImpl();
        if(providerService.addProvider(provider)){
            try {
                response.sendRedirect(request.getContextPath()+"/jsp/provider.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                request.getRequestDispatcher("provideradd.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //删除供货商
    private void deleteProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String proid = request.getParameter("proid");
        Integer DelId = 0;
        try {
            DelId = Integer.parseInt(proid);
        } catch (NumberFormatException e) {
            DelId = 0;
        }

        Map<String, String> resultMap = new HashMap<>();
        if(DelId < 0){
            resultMap.put("delResult", "notexist");
        }else {
            ProviderService providerService = new ProviderServiceImpl();
            if(providerService.deleteProvider(DelId)){
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
    //通过ID获取供货商信息
    private void getProviderById(HttpServletRequest request, HttpServletResponse response, String url){
        String id = request.getParameter("proid");
        if(!StringUtils.isNullOrEmpty(id)){
            ProviderService providerService = new ProviderServiceImpl();
            Provider provider = providerService.getProviderById(id);
            request.setAttribute("provider", provider);
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}