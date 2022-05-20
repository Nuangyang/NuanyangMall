package com.woniuxy.service;

import com.woniuxy.controller.form.DelShoppingForm;
import com.woniuxy.controller.form.GetByGoodsIdForm;
import com.woniuxy.controller.form.GetGoodsCategoryForm;
import com.woniuxy.controller.form.GetGoodsVagueForm;
import com.woniuxy.model.User;
import com.woniuxy.model.UserSignin;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.commons.Transactional;
import com.woniuxy.utils.exception.Commoditygetexception;
import com.woniuxy.utils.exception.WoniuMallException;

public interface GoodsService {


    //获取所有的主页商品
    public Result getOnlineGoodsAll() throws Commoditygetexception, WoniuMallException;


    //获取所有商品信息
    public Result getGoodsShow() throws WoniuMallException;


    //获取商品分页页数
    public Result getFenyeGoods() throws WoniuMallException;

    //根据页数取对应商品
    public Result getPagesShow(String pages) throws WoniuMallException;

    //模糊查询商品
    public Result getVagueByGoods(GetGoodsVagueForm vague) throws WoniuMallException;

    //根据类别查询对应商品
    public Result getCategoryByGoods(GetGoodsCategoryForm num) throws WoniuMallException;

    //主页根据商品ID查询对应商品信息
    public Result getUserByGoodsId(GetByGoodsIdForm gid) throws WoniuMallException;

    //所有根据商品ID查询对应商品信息
    public Result getUserByGoodsIdAll(GetByGoodsIdForm gid) throws WoniuMallException;

    //读取购物车表
    //获取之后通过gid查询对应商品信息
    public Result getUserByShopping(User user) throws WoniuMallException;

    //通过购物车商品ID删除对应商品信息
    @Transactional
    public Result delShopping(DelShoppingForm dsf) throws WoniuMallException;

    //添加购物车对象
    @Transactional
    public Result addShopping(User userSignin, GetByGoodsIdForm gid) throws WoniuMallException;

    //添加购物车对象
    @Transactional
    public Result addShoppingAll(User userSignin, GetByGoodsIdForm gid) throws WoniuMallException;

    //获取购物车商品数量
    public Result getBynum(Integer gid) throws WoniuMallException;

    //减少购物车数量
    @Transactional
    public Result jianShoppingSnum(Integer gid) throws WoniuMallException;

    //增加购物车数量
    @Transactional
    public Result jiaShoppingSnum(Integer gid) throws WoniuMallException;


    //修改订单状态
    @Transactional
    public Result UpDateUserDingDan(String dingdan) throws WoniuMallException;


    //根据订单编号减少商品库存
    @Transactional
    public Result UpDateGoodsGnumjian(String dingdanbianhao) throws WoniuMallException;
}
