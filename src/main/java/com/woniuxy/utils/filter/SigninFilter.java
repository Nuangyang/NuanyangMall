

package com.woniuxy.utils.filter;

import com.alibaba.fastjson.JSONObject;
import com.woniuxy.model.User;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/homepage.html"})
public class SigninFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //登录拦截
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("开始拦截");
        //转为Httprequest对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //new 一个Session对象
        HttpSession session = request.getSession();

        //判断是否为用户登录状态
        Object user = session.getAttribute("user");
        //判断是否管理员登录
        Object admin = session.getAttribute("admin");

        //获取完整的url，包括Http协议，端口号，servlet名字和映射路径
        String string = request.getRequestURL().toString();

        //非登录后才能访问的放性
        if (string.endsWith("register.html")
                || string.endsWith("Signin.html")
                || string.endsWith("shouwgoods.html")
                || string.endsWith("homepage.html")
                || string.endsWith("gouwuche.html")
        ) {
            //放行
            filterChain.doFilter(request, response);
        } else {
            //登录，放行
            if (null!=user||null!=admin) {
                filterChain.doFilter(request, response);

            } else {
                //判断是否是ajax请求
                String header = request.getHeader("X-Requested-With");
                if (header != null) {
                    response.getWriter().write(new JSONObject().toJSONString(false));
                } else {
                    //重定向登录界面
                    response.setStatus(302);
                    response.sendRedirect("Signin.html");

                }


            }


        }


    }

    @Override
    public void destroy() {

    }
}