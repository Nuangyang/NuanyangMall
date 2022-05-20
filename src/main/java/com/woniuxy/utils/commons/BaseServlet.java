package com.woniuxy.utils.commons;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作为公共的servlet父类使用，用于解决项目中由于请求过多而导致的servlet类的爆发式增长
 */
public class BaseServlet extends HttpServlet {

    //http://localhost:8080/user.do?method=register


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("method");

        if (null == methodName || "".equals(methodName)) {
            throw new RuntimeException("没有传递method参数，后台无法调用到具体的方法");
        }

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            Object returnValue = method.invoke(this, req, resp);

            if (null == returnValue) {
                return;
            }else{
                String value = (String) returnValue;
                if (null != value && !"".equals(value)) {

                    if (value.contains(":")) {
                        String[] ss = value.split(":");
                        String option=ss[0];//要做的操作
                        String path=ss[1];//要跳转的路径

                        if (option.equals("r")) resp.sendRedirect(path);
                        else if (option.equals("f")) req.getRequestDispatcher(path).forward(req,resp);
                        else throw new RuntimeException("当前操作不被支持");
                    }else{
                        resp.sendRedirect(value);
                    }

                }else{
                    throw new RuntimeException("没有确定的指向，无法完成下一步操作");
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
