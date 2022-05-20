package com.woniuxy.utils.commons;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;


public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static ThreadLocal<SqlSession> tl=new ThreadLocal<>();
    static {
        try(InputStream is = Resources.getResourceAsStream("/config/mybatis-config.xml");){
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = tl.get();
        if (null == sqlSession) {
            //创建一个并绑定对线程上
            sqlSession = sqlSessionFactory.openSession(false);
            System.out.println(null==sqlSession);
            tl.set(sqlSession);
            return tl.get();
        }
        return sqlSession;
    }
    public static <T>T getMapper(Class<T> clazz){
        return getSqlSession().getMapper(clazz);
    }
    public static void close(){
       tl.get().close();
        tl.remove();
    }


}

