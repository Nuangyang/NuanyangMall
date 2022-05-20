package com.woniuxy.service;

import com.woniuxy.controller.form.AddGoodsAdministForm;
import com.woniuxy.controller.form.AdministratorsSigninForm;

import com.woniuxy.controller.form.XiuGaiGoodsAdministForm;
import com.woniuxy.service.iofo.UserSigninIofo;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.commons.Transactional;
import com.woniuxy.utils.exception.WoniuMallException;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AddministService {


    //通过用户名和密码获取管理员对象
    public  Result getAdministrators(AdministratorsSigninForm asf);


    //预加载获取商品分类信息
    public Result getGoodsAdministratorsFenlei() throws WoniuMallException;

    //获取所有商品信息
    public Result getGoodsAdministratorsAll() throws WoniuMallException;

    //获取所有用户信息
    public Result getUserAdministrators() throws WoniuMallException;

    //添加商品
    @Transactional
    public Result addGoodsAdministrators(AddGoodsAdministForm goodsAdministForm) throws WoniuMallException;

    //下架商品
    @Transactional
    public Result delGoodsAdministrators(Integer gid) throws WoniuMallException;

    //修改商品属性
    @Transactional
    public Result xiugaiGoodsAll(XiuGaiGoodsAdministForm xiuGaiGoodsAdministForm, Integer gid) throws WoniuMallException;

    //根据用户ID禁用用户
    @Transactional
    public Result jingyongUserAdministrators(Integer integer) throws WoniuMallException;

    //根据用户ID解禁用户
    @Transactional
    public Result jieJing(Integer uid) throws WoniuMallException;

    //获取所有管理员用于展示
    public  Result getAdminstraTo() throws WoniuMallException;

    //判断管理员密码是否相同 不相同则修改
    @Transactional
    public  Result upDataAdministrators(String jpwd, String xpwd, Integer id) throws WoniuMallException;

    //获取所有订单信息
    public Result getShowUserShopPingDetails() throws WoniuMallException;
}
