package com.woniuxy.service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.woniuxy.controller.form.*;
import com.woniuxy.model.User;
import com.woniuxy.model.UserSignin;
import com.woniuxy.service.iofo.UserSigninIofo;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.commons.Transactional;
import com.woniuxy.utils.exception.*;
import org.apache.ibatis.annotations.Insert;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {


    //根据用户名密码获取用户
    public Result getUser(UserSigninForm userSigninForm) throws WoniuMallException;

    //判断用户是否存在
    public Result judgeUser(String name) throws RxistenceException;

    //添加用户
    @Transactional
    public Result addUser(UserAddForm uaf) throws WoniuMallException;

    //添加用户登录表
    @Transactional
    public Result addUserBySignin(UserSigninForm usf) throws WoniuMallException;

    //获取主页用户部分展示信息
    public Result getUserByzhuye(GetUserByzhuyeForm gub) throws RxistenceException, WoniuMallException;

    //根据用户名获取用户信息
    public Result getUserByuname(UserSigninIofo user) throws WoniuMallException;


    //发送激活邮件激活用户
    public Result ModifyActivation(User user) throws WoniuMallException;

    //激活用户
    @Transactional
    public Result ActivationUser(User user) throws WoniuMallException;

    //更新头像
    @Transactional
    public Result updaUserTouxiang(UserSigninIofo usf, String lujing) throws WoniuMallException;


    //修改用户信息
    @Transactional
    public Result updaUserXingXi(UserUpdaForm uuf, UserSigninIofo user) throws WoniuMallException;


    //获取用户所有地址信息
    public Result getUserAddressAll(UserSigninIofo uuf) throws WoniuMallException;

    //获取用户默认地址信息
    public Result getUsermorenAddresss(UserSigninIofo uuf) throws WoniuMallException;

    //添加收获地址
    @Transactional
    public Result addUserRess(UserSigninIofo user, AddUserRessForm addUserRessForm) throws WoniuMallException;

    //删除用户地址信息
    @Transactional
    public Result delUserRessById(UserSigninIofo userSigninIofo, Integer aid) throws WoniuMallException;

    //用户更改默认地址
    @Transactional
    public Result gengHuangRess(UserSigninIofo userSigninIofo, Integer aid) throws WoniuMallException;


    //结算用户购物车所有商品
    @Transactional
    public  Result getUserShoPingByAll(UserSigninIofo userSigninIofo) throws WoniuMallException;


    //结算用户购物车所选商品
    @Transactional
    public  Result getUserShoPingBy(UserSigninIofo userSigninIofo, List<Integer> strings) throws WoniuMallException;

    //通过订单编号与用户ID获取订单表对应数据
    public Result getUserShopPingByUidByUUid(UserSigninIofo userSigninIofo, long uuid) throws WoniuMallException;


    //获取当前用户所有订单信息
    public Result getUserDingDanAll(UserSigninIofo userSigninIofo) throws WoniuMallException;

    //通过订单ID获取订单信息
    public Result getUserDingDandanyi(UserSigninIofo userSigninIofo, Integer sdid) throws WoniuMallException;

}