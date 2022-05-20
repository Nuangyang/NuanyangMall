package com.woniuxy.utils.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5Util {


    public static String getMd5(String pwd){


        //获取算法对象
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //获取加密后的字节数组
        byte[] digest = md5.digest(pwd.getBytes());
        //将加密后的字节数组拼装起来
        String string1 = Base64.getEncoder().encodeToString(digest);

        return  string1;
    }


}
