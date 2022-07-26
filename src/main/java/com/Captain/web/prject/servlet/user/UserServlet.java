package com.Captain.web.prject.servlet.user;
/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/25 10:52
 * description:
 */

import com.Captain.web.prject.domain.Role;
import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.service.RoleService;
import com.Captain.web.prject.service.UserService;
import com.Captain.web.prject.service.impl.RoleServiceImpl;
import com.Captain.web.prject.service.impl.UserServiceImpl;
import com.Captain.web.prject.utils.Constants;
import com.Captain.web.prject.utils.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/jsp/user.do")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if(method.equals("savepwd") && method!=null){
            this.updatePwd(request,response);
        }else if(method.equals("pwdmodify") && method!=null){
            this.pwdModify(request,response);
        }else if(method.equals("query") && method!=null){
            this.query(request,response);
        }else if(method.equals("add") && method!=null){
            this.add(request,response);
        }else if(method.equals("getrolelist")){
            this.getRoleList(request, response);
        }else if(method.equals("ucexist") && method!=null){
            this.userCodeExist(request, response);
        }else if(method.equals("deluser") && method!=null){
            this.delete(request, response);
        }else if(method.equals("modify") && method!=null){
            this.getUserId(request, response, "usermodify.jsp");
        }else if(method.equals("view") && method!=null){
            this.getUserId(request, response, "userview.jsp");
        }else if(method.equals("modifyexe") && method!=null){
            this.modify(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //代码复用
    //修改密码
    private void updatePwd(HttpServletRequest request, HttpServletResponse response) {
        String Message = "message";
        //从Session中获取ID;
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = request.getParameter("newpassword");

        boolean flag = false;
        //StringUtils.isNullOrEmpty(newpassword)--String的工具类，判断变量是否为空
        if(o!=null && !StringUtils.isNullOrEmpty(newpassword)){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if(flag){
                //显示密码修改成功
                request.setAttribute(Message,"密码修改成功，请退出登录！");
                //密码修改成功后，清除Session
                request.getSession().removeAttribute(Constants.USER_SESSION);
            }else{
                request.setAttribute(Message,"密码修改失败");
            }
        }else{
            request.setAttribute(Message,"新密码有问题");
        }

        try {
            request.getRequestDispatcher("pwdmodify.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //验证旧密码，session中有用户的密码
    private void pwdModify(HttpServletRequest request, HttpServletResponse response){
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = request.getParameter("oldpassword");

        HashMap<String, String> resultMap = new HashMap<>();
        if(o==null){
            resultMap.put("result","sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){
            resultMap.put("result", "error");
        }else {
            String userPassword = ((User) o).getUserPassword();
            if(oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else{
                resultMap.put("result", "false");
            }
        }

        try {
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            //JSONArray--转换格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //用户管理（重难点）
    private void query(HttpServletRequest request, HttpServletResponse response){
        //查询用户列表
        String queryUserName = request.getParameter("queryname");//用户名
        String temp = request.getParameter("queryUserRole");//用户等级(角色)
        String pageIndex = request.getParameter("pageIndex");//分页
        int queryUserRole = 0;//用户角色设置默认值“--请选择--”

        //获取用户列表
        UserService userService = new UserServiceImpl();
        //获取角色列表
        RoleService roleService = new RoleServiceImpl();

        //第一次访问页面一定是第一页，页面大小是固定的
        long pageSize = Constants.PAGESIZE;//可以写在配置文件里，方便后期调整
        long currentPageNo = 1;//默认当前页为第一页
        long totalCount = userService.getUserCount(queryUserName, queryUserRole);//获取用户总数

        if(queryUserName == null){
            queryUserName = "";
        }
        if(temp!=null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);//将前端获取到的值转换类型并复制
        }
        if(pageIndex != null){
            try {
                currentPageNo = Integer.parseInt(pageIndex);
            } catch (Exception e) {
                try {
                    //发生错误后重定向到错误页面
                    response.sendRedirect("error.jsp");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        //控制首尾页
        long totalPageCount = pageSupport.getTotalPageCount();
        if(currentPageNo < 1){
            //保证当前输入页面不小于第一页
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            //保证当前输入页面不大于最后一页
            currentPageNo = totalPageCount;
        }
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("totalPageCount", totalPageCount);

        //获取用户列表展示
        List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        request.setAttribute("userList", userList);
        request.setAttribute("queryUserName", queryUserName);
        //获取角色列表展示
        List<Role> roleList = roleService.getRoleList();
        request.setAttribute("roleList", roleList);
        request.setAttribute("queryUserRole", queryUserRole);

        try {
            request.getRequestDispatcher("userlist.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //添加用户
    private void add(HttpServletRequest request, HttpServletResponse response){
        //获取表单数据
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        //为对象赋值
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());

        UserService userService = new UserServiceImpl();
        if(userService.userAdd(user)){
            try {
                response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                request.getRequestDispatcher("useradd.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //获取角色(等级)列表
    private void getRoleList(HttpServletRequest request, HttpServletResponse response) {
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //把roleList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
            outPrintWriter.write(JSONArray.toJSONString(roleList));
            outPrintWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outPrintWriter.close();
        }
    }
    //判断后台数据是否存在
    private void userCodeExist(HttpServletRequest request, HttpServletResponse response) {
        //判断用户账号是否可用
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            UserService userService = new UserServiceImpl();
            User user = userService.selectUserCodeExist(userCode, userPassword);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }

        //把resultMap转为json字符串以json的形式输出
        //配置上下文的输出类型
        response.setContentType("application/json");
        //从response对象中获取往外输出的writer对象
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = response.getWriter();
            //把resultMap转为json字符串 输出
            outPrintWriter.write(JSONArray.toJSONString(resultMap));
            outPrintWriter.flush();//刷新
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outPrintWriter.close();//关闭流
        }
    }
    //获取用户UID
    private void getUserId(HttpServletRequest request, HttpServletResponse response, String url){
        String id = request.getParameter("uid");
        if(!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            UserService userService = new UserServiceImpl();
            User user = userService.getUserById(id);
            request.setAttribute("user", user);
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //删除用户
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(id);
        }catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            UserService userService = new UserServiceImpl();
            if(userService.userDelete(delId)){
                resultMap.put("delResult", "true");
            }else{
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
    //修改用户信息
    private void modify(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("uid");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));
        user.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        if(userService.modify(user)){
            try {
                response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                request.getRequestDispatcher("usermodify.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}