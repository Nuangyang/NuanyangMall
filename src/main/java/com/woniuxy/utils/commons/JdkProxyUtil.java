package com.woniuxy.utils.commons;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyUtil<T> implements InvocationHandler {

    private T t;//真实对象


    public T getProxyObject(T real){
        this.t=real;
        return (T) Proxy.newProxyInstance(real.getClass().getClassLoader(),
                real.getClass().getInterfaces(),
                this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.isAnnotationPresent(Transactional.class)) {//如果方法上有事务处理的注解，则进行事务控制
            Object returnValue = null;
            try {
                returnValue = method.invoke(t, args);
                MybatisUtil.getSqlSession().commit();
            } catch (Exception e) {
                e.printStackTrace();
                MybatisUtil.getSqlSession().rollback();
                if(e instanceof InvocationTargetException){
                    throw ((InvocationTargetException)e).getTargetException();
                }
            } finally {
                MybatisUtil.close();
            }
            return returnValue;
        }else{
            Object invoke = null;
            try {
                invoke = method.invoke(t, args);

            } catch (Exception e) {
                e.printStackTrace();
                MybatisUtil.getSqlSession().rollback();
                if(e instanceof InvocationTargetException){
                    throw ((InvocationTargetException)e).getTargetException();
                }
            } finally {
                MybatisUtil.close();
            }
            return invoke;
        }
    }
}

