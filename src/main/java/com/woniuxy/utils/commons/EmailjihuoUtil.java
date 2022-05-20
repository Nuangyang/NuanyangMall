package com.woniuxy.utils.commons;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class EmailjihuoUtil {


    public String EmailUtil(String addressee)  {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol","SMTP");//设置发送邮件使用的协议
        props.setProperty("mail.host","smtp.163.com");//设置发送邮件使用的邮件服务器
        props.setProperty("mail.smtp.auth","true");//是否要进行验证
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //创建验证器对象时，要传入的两个参数，第一个是发送邮箱的邮箱帐号，第二个是对应邮箱的授权密码
                return new PasswordAuthentication("q1106847129@163.com","CJMZQXCYQMHPKLNP");
            }
        });

        MimeMessage message = new MimeMessage(session);

        String substring = null;
        try {
            message.setSubject("Nuangyang商城激活码");//设置邮件标题

            //随机生成激活码
            String string = UUID.randomUUID().toString();

            message.setContent(",你好<br>欢迎激活Nuangyang商城! 请点击链接进行激活:<a href='http://192.168.40.99/user.do?method=activationUser'>点击此处:"+string+"</a>","text/html;charset=utf-8");//设置邮件内容


            System.out.println(addressee);
            if(null!=addressee&&addressee!=""){
                message.setRecipient(Message.RecipientType.TO,new InternetAddress(addressee));//设置收件人
                message.setFrom(new InternetAddress("q1106847129@163.com"));//设置发送人
                Transport.send(message);
            }


        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return substring ;




    }

}
