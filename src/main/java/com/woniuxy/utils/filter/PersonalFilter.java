package com.woniuxy.utils.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.do","*.jsp","*.html"},initParams = @WebInitParam(name="encoding",value = "UTF-8"))
public class PersonalFilter implements Filter {

    //创建一个变量保存字符编码集
    private String charset;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //获取编码集
        String encoding = filterConfig.getInitParameter("encoding");

        if(encoding!=null) charset=encoding;

        this.charset="UTF-8";

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //设置请求对象和响应对象得字符编码级
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response=(HttpServletResponse)servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String string = request.getRequestURL().toString();

        if(string.endsWith(".css")){
            response.setContentType("text/css;charset="+charset);
        }else if(string.endsWith(".js")){
            response.setContentType("text/javascript;charset="+charset);
        }else {
            response.setContentType("text/html;charset="+charset);
        }
        //统一设置完成后放行
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
