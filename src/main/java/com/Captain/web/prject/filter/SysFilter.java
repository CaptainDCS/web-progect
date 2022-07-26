package com.Captain.web.prject.filter;

import com.Captain.web.prject.domain.User;
import com.Captain.web.prject.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SysFilter", urlPatterns = "/jsp/*")
public class SysFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //获取SessionID
        User user = (User)request.getSession().getAttribute(Constants.USER_SESSION);

        if(user ==null){
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }else{
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}