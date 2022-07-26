package com.Captain.web.prject.servlet.user;
/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/24 12:33
 * description:
 */

import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.service.UserService;
import com.Captain.web.prject.service.impl.UserServiceImpl;
import com.Captain.web.prject.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名和密码
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");

        //调用业务层
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if(user != null){
            request.getSession().setAttribute(Constants.USER_SESSION, user);
            //转发请求--重定向
            response.sendRedirect("jsp/frame.jsp");
        }else {
            //请求转发回登录页面，并提示密码或账号错误
            request.setAttribute("error", "用户名或密码不正确");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}