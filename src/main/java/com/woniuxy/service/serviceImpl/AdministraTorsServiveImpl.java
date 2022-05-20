package com.woniuxy.service.serviceImpl;


import com.woniuxy.controller.form.AddGoodsAdministForm;
import com.woniuxy.controller.form.AdministratorsSigninForm;
import com.woniuxy.controller.form.XiuGaiGoodsAdministForm;
import com.woniuxy.dao.AdministratorsDao;
import com.woniuxy.model.*;
import com.woniuxy.service.AddministService;
import com.woniuxy.service.iofo.AdministraIofo;
import com.woniuxy.utils.commons.MybatisUtil;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.exception.*;

import java.util.ArrayList;

public class AdministraTorsServiveImpl implements AddministService {


    //通过管理名密码获取管理员对象
    @Override
    public Result getAdministrators(AdministratorsSigninForm adf) {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        AdministraTors administraTors = new AdministraTors(0,adf.getAdname(),adf.getAdpwd(),"");
        AdministraTors adm = mapper.getAdministrators(administraTors);

        if(null!=adm){
            AdministraIofo administraIofo = new AdministraIofo(adm.getAdname(),adm.getAdpwd());

            if(null!=adm) return Result.success(8888,"管理员",administraIofo);

        }



        return null;
    }


    //获取商品分类信息
    @Override
    public Result getGoodsAdministratorsFenlei() throws WoniuMallException {
        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        ArrayList<Category> categoryAdministrators = mapper.getCategoryAdministrators();

        if(null!=categoryAdministrators){
            return Result.success(200,"类别获取成功",categoryAdministrators);
        }else{
            throw new GetGoodsAdministratorsExceotion(4074,"类别查询失败");
        }

    }


    //获取所有商品信息
    @Override
    public Result getGoodsAdministratorsAll() throws WoniuMallException{
        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);
        ArrayList<Goods> goodsShow = mapper.getGoodsShow();

        if(null!=goodsShow){
            return Result.success(200,"管理员商品获取成功",goodsShow);
        }else{
            throw new GoodsQueryExcption(4074,"管理员查询商品失败");
        }

    }


    //获取所有用户信息
    @Override
    public Result getUserAdministrators() throws WoniuMallException{

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        ArrayList<User> userShow = mapper.getUserShow();
        if(null!=userShow){

            return Result.success(200,"获取所有用户成功",userShow);
        }else{
            throw  new RxistenceException(4075,"获取所有用户异常");

        }

    }


    //添加商品
    @Override
    public Result addGoodsAdministrators(AddGoodsAdministForm goodsAdministForm) throws WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        Integer integer = mapper.addGoodsAdministrators(goodsAdministForm);

        if(integer>0){

            return  Result.success(200,"添加商品",integer);
        }else{
            throw  new AddGoodsAdministratorsException(4077,"添加商品异常");

        }

    }


    //下架商品
    @Override
    public Result delGoodsAdministrators(Integer gid)throws WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        Integer integer = mapper.delGoodsAdministrators(gid);

        if(integer>0){
            return Result.success(200,"下架成功",integer);
        }else{
            throw new DelShoppingException(4079,"下架商品失败");
        }

    }


    //修改商品属性
    @Override
    public Result xiugaiGoodsAll(XiuGaiGoodsAdministForm xiuGaiGoodsAdministForm,Integer gid) throws WoniuMallException {
        //获取商品ID
        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        Integer integer = mapper.XiugaiGoodsShuXing(xiuGaiGoodsAdministForm, gid);
        if(integer>0){
            return Result.success(200,"修改成功",integer);
        }else{
            throw new  UpdaUserTouxiangException(4079,"修改商品属性异常");
        }

    }

    //禁用用户
    @Override
    public Result jingyongUserAdministrators(Integer integer) throws WoniuMallException{

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        Integer integer1 = mapper.jingYongUser(integer);
        Integer integer2 = mapper.jingYongUserSignin(integer);

        if(integer1>0&&integer2>0){
            return Result.success(200,"禁用用户成功",integer1);
        }else{

            throw  new JingYongUserException(4081,"禁用失败");

        }

    }

    //解禁用户
    @Override
    public Result jieJing(Integer uid)throws WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);
        Integer integer = mapper.jiejingYongUser(uid);
        Integer integer1 = mapper.jiejingYongUserSignin(uid);

        if(integer>0&&integer1>0){

            return Result.success(200,"解开成功",integer);

        }else{

            throw  new JingYongUserException(4082,"解禁失败");
        }

    }


    //获取所有管理员账户
    @Override
    public Result getAdminstraTo() throws WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);
        ArrayList<AdministraTors> admin = mapper.getAdmin();

        if(null!=admin){
            return Result.success(200,"管理员获取成功",admin);
        }else{
            throw new GetGoodsAdministratorsExceotion(4083,"管理员获取失败");
        }

    }



    //判断管理员密码是否相同 不相同则修改
    @Override
    public Result upDataAdministrators(String jpwd, String xpwd,Integer id)throws WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);

        AdministraTors aiministraTorsUpDtae = mapper.getAiministraTorsUpDtae(id);
        if(null!=aiministraTorsUpDtae){
            //判断是否是相同

            if(aiministraTorsUpDtae.getAdpwd().equals(xpwd)){
                throw  new AdministratorsExeption(4082,"密码相同异常");
            }else{
                Integer integer = mapper.UpdataAiministraTors(id, xpwd);
                return Result.success(200,"修改成功",integer);
            }

        }


        return null;
    }


    //获取所有订单信息
    @Override
    public Result getShowUserShopPingDetails() throws  WoniuMallException {

        AdministratorsDao mapper = MybatisUtil.getMapper(AdministratorsDao.class);
        ArrayList<ShopPingDetails> userAllShopPingss = mapper.getUserAllShopPingss();

        if(null!=userAllShopPingss&&userAllShopPingss.size()>0){
            return  Result.success(200,"获取成功",userAllShopPingss);
        }else{
            throw  new GetGoodsAdministratorsExceotion(4498,"管理员获取失败");
        }
    }


}