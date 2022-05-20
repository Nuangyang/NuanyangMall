package com.woniuxy.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniuxy.controller.form.DelShoppingForm;
import com.woniuxy.controller.form.GetByGoodsIdForm;
import com.woniuxy.controller.form.GetGoodsCategoryForm;
import com.woniuxy.controller.form.GetGoodsVagueForm;
import com.woniuxy.dao.GoodsDao;
import com.woniuxy.model.*;
import com.woniuxy.service.GoodsService;
import com.woniuxy.service.iofo.*;
import com.woniuxy.utils.commons.MybatisUtil;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.exception.*;

import java.util.ArrayList;
import java.util.List;


public class GoodsServiceImpl implements GoodsService {


    //获取所有的主页商品
    @Override
    public Result getOnlineGoodsAll() throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);

        ArrayList<GoodsOnlineIofo> goodsOnline = mapper.getGoodsOnline();

        if(null==goodsOnline){
            throw new Commoditygetexception(4007,"商品获取异常");
        }else {
            return  Result.success(200,"主页商品获取成功", goodsOnline);
        }



    }

    //获取所有商品
    @Override
    public Result getGoodsShow()throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        ArrayList<Goods> gow = mapper.getGoodsShow();

        if(null!=gow){
            ArrayList<GoodsShowIofo> arr = new ArrayList<>();

            for(Goods gs:gow){
                GoodsShowIofo goods = new GoodsShowIofo();
                goods.setGid(gs.getGid());
                goods.setGname(gs.getGname());
                goods.setGnum(gs.getGnum());
                goods.setGprive(gs.getGprive());
                goods.setGdescribe(gs.getGdescribe());
                goods.setGcategory(gs.getGcategory());
                goods.setImg(gs.getImg());
                arr.add(goods);
            }


            return Result.success(200,"查询成功",arr);
        }else{
            throw new GoodsQueryExcption(4045,"商品查询失败");
        }


    }

    //获取商品分页页数
    @Override
    public Result getFenyeGoods() throws WoniuMallException {
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);


        //配置分页  (ps 它只会对它接下来的第一条查询语句进行分页处理)
        PageHelper.startPage(1,12);
        ArrayList<Goods> goodsShow = mapper.getGoodsShow();


        if(null!=goodsShow){
            PageInfo<Goods> goodsPageInfo=new PageInfo<Goods>(goodsShow);
            //获取一共分了几页


            int pagest = goodsPageInfo.getPages();



            GoodsPagesIofo goodsPagesIofos = new GoodsPagesIofo(pagest);
            return Result.success(200,"页数查询成功",goodsPagesIofos);
        }else{
            throw  new GoodsQueryExcption(4046,"分页页数查询失败");
        }



    }

    //根据页数取对应商品
    @Override
    public Result getPagesShow(String pages) throws WoniuMallException {

        Integer page=new Integer(pages);
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        //配置分页
        PageHelper.startPage(page,12);
        ArrayList<Goods> goodsShow = mapper.getGoodsShow();

        if(null!=goodsShow){
            PageInfo<Goods> pageInfo=new PageInfo<>(goodsShow);
            //转成分页后的数据
            List<Goods> list = pageInfo.getList();

            return Result.success(200,"数据查询成功",list);
        }else{
            throw new GoodsQueryExcption(4047,"分页查询失败");
        }

    }

    //模糊查询商品
    @Override
    public Result getVagueByGoods(GetGoodsVagueForm vague) throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        ArrayList<Goods> vague1 = mapper.getVague(vague);

        if(null!=vague1&&vague1.size()>0){

            return Result.success(200,"模糊查询成功",vague1);

        }else{
            throw new GetVagueException(4048,"模糊查询异常/或是查到对应");
        }

    }

    //根据类别查询商品
    @Override
    public Result getCategoryByGoods(GetGoodsCategoryForm num) throws WoniuMallException{

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        ArrayList<Goods> category = mapper.getCategory(num);

        if(null!=category){
            ArrayList<GoodsShowIofo> arr = new ArrayList<>();
            for(Goods gs:category){
                GoodsShowIofo goods = new GoodsShowIofo();
                goods.setGid(gs.getGid());
                goods.setGname(gs.getGname());
                goods.setGnum(gs.getGnum());
                goods.setGprive(gs.getGprive());
                goods.setGdescribe(gs.getGdescribe());
                goods.setGcategory(gs.getGcategory());
                goods.setImg(gs.getImg());
                arr.add(goods);
            }
        return Result.success(200,"分类查询成功",arr);

        }else{
            throw new CategoryException(4052,"分类查询失败");
        }


    }


    //主页根据商品ID查询对应商品信息
    @Override
    public Result getUserByGoodsId(GetByGoodsIdForm gid) throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        Goods idGoods = mapper.getIdGoods(gid);

        if(null!=idGoods){

            GetGoodsByIdIofo getGoods = new GetGoodsByIdIofo();
            getGoods.setGid(idGoods.getGid());
            getGoods.setGname(idGoods.getGname());
            getGoods.setGnum(idGoods.getGnum());
            getGoods.setGprive(idGoods.getGprive());
            getGoods.setGdescribe(idGoods.getGdescribe());
            getGoods.setGcategory(idGoods.getGcategory());
            getGoods.setImg(idGoods.getImg());

            return Result.success(200,"ID查询成功",getGoods);
        }else{
            throw new GetGoodsByIdException(4053,"商品查询失败");
        }

    }

    //所有根据商品ID查询对应商品信息
    @Override
    public Result getUserByGoodsIdAll(GetByGoodsIdForm gid) throws WoniuMallException {
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        Goods idGoods = mapper.getIdGoodsBy(gid);

        if(null!=idGoods){

            GetGoodsByIdIofo getGoods = new GetGoodsByIdIofo();
            getGoods.setGid(idGoods.getGid());
            getGoods.setGname(idGoods.getGname());
            getGoods.setGnum(idGoods.getGnum());
            getGoods.setGprive(idGoods.getGprive());
            getGoods.setGdescribe(idGoods.getGdescribe());
            getGoods.setGcategory(idGoods.getGcategory());
            getGoods.setImg(idGoods.getImg());

            return Result.success(200,"ID查询成功",getGoods);
        }else{
            throw new GetGoodsByIdException(4053,"商品查询失败");
        }

    }

    //读取购物车表
    //获取之后通过gid查询对应商品信息
    @Override
    public Result getUserByShopping(User user) throws WoniuMallException{
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        if(null!=user){
            ArrayList<ShopPing> shopping = mapper.getShopping(user);

            ArrayList<Goods> arr = new ArrayList<>();
            if (null!=shopping) {
                //把购物车数据转为真实商品信息 用作渲染界面
                for(ShopPing sp:shopping){
                    Goods idShopPingGoodsBy = mapper.getIdShopPingGoodsBy(sp);
                    idShopPingGoodsBy.setGnum(sp.getSnum());
                    arr.add(idShopPingGoodsBy);
                }
                return Result.success(200,"购物车商品查询成功",arr);
            }else{
                //没有查询到信息

                throw new GetShoppingException(4058,"购物车查询失败/可能没有数据");
            }
        }
        return null;
    }


    //通过购物车商品ID删除对应商品信息
    @Override
    public Result delShopping(DelShoppingForm dsf) throws  WoniuMallException{

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        Integer integer = mapper.delShopping(dsf);
        if(integer>0){
            return Result.success(200,"删除购物车商品成功",integer);

        }else{
            //删除异常
            throw  new  DelShoppingException(4059,"删除商品异常");

        }

    }

    //添加购物车对象
    @Override
    public Result addShopping(User userSignin,GetByGoodsIdForm gid)throws WoniuMallException {
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);

        //获取商品对象
        Goods goodsByIdShopping = mapper.getGoodsByIdShopping(gid);


        //判断购物车中是否有相同商品 有则添加数量
        ShopPing shoPingByGoodsId = mapper.getShoPingByGoodsId(goodsByIdShopping);

        if (null != shoPingByGoodsId) {
            //添加数量
            Integer integer = mapper.addShoPingBynum(shoPingByGoodsId);
            if (integer > 0) {
                return Result.success(200, "购物车添加成功", integer);

            } else {
                throw new AddgoodsShoppingException(4061, "购物车添加失败");
            }
        } else {
            //存入购物车
            Integer integer = mapper.addGoodsByShopping(userSignin, goodsByIdShopping);
            if (integer > 0) {

                return Result.success(200, "购物车添加成功", integer);

            } else {
                throw new AddgoodsShoppingException(4061, "购物车添加失败");
            }

        }
    }

    //添加购物车对象al
    @Override
    public Result addShoppingAll(User userSignin, GetByGoodsIdForm gid) throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);

        //获取商品对象
        Goods goodsByIdShopping = mapper.getGoodsByIdShoppingAll(gid);

        //获取商品主表对象
        Goods shoppingGoodsByOnline = mapper.getShoppingGoodsByOnline(goodsByIdShopping);

        //判断购物车中是否有相同商品 有则添加数量
        ShopPing shoPingByGoodsId = mapper.getShoPingByGoodsId(shoppingGoodsByOnline);
        if(null!=shoPingByGoodsId){

            //添加数量
            Integer integer = mapper.addShoPingBynum(shoPingByGoodsId);

            if(integer>0){

                return Result.success(200,"购物车添加成功",integer);

            }else{
                throw new AddgoodsShoppingException(4061,"购物车添加失败");
            }
        }else{
            //存入购物车
            Integer integer = mapper.addGoodsByShopping(userSignin, shoppingGoodsByOnline);

            if(integer>0){

                return Result.success(200,"购物车添加成功",integer);

            }else{
                throw new AddgoodsShoppingException(4061,"购物车添加失败");
            }
        }

    }



    //获取购物车商品数量
    @Override
    public Result getBynum(Integer gid) throws WoniuMallException{

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        ShopPing shoPingById = mapper.getShoPingById(gid);

        if(null!=shoPingById){

            return Result.success(200,"购物车对象获取成功",shoPingById);

        }else{

        throw new GetShopingNumException(4063,"购物车数量获取失败");
        }

    }

    //减少购物车数量
    @Override
    public Result jianShoppingSnum(Integer gid)throws  WoniuMallException{

        //获取商品当前数量
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        ShopPing shoPingById = mapper.getShoPingById(gid);

        if(shoPingById.getSnum()>1){
            //大于1则可以进行减少

            Integer integer = mapper.jianShopingByNum(gid);

            if(integer>0){
                return Result.success(200,"减少成功",integer);
            }else{
                throw new JianShoppingSnumException(4054,"购物车数量已经不能在减少了");
            }

        }else{
            throw new JianShoppingSnumException(4054,"购物车数量已经不能在减少了");
        }

    }

    //增加购物车数量
    @Override
    public Result jiaShoppingSnum(Integer gid) throws WoniuMallException {

        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        //获取商品原库存
        Goods goodsByIdShopping = mapper.getGoodsByIdShopping(new GetByGoodsIdForm(gid));


        //增加购物车商品数量
        ShopPing shoPingById = mapper.getShoPingById(gid);

        //判断当前购物车商品数量不能超过商品库存
        if(shoPingById.getSnum()<goodsByIdShopping.getGnum()){
            //增加数量
            Integer integer = mapper.addShoPingBynum(shoPingById);
            if(integer!=0){
                //添加成功
                return  Result.success(200,"增加成功",integer);
            }else{
                throw new JianShoppingSnumException(4055,"购物车数量已经不能在增加了");
            }

        }else{
            throw new JianShoppingSnumException(4055,"购物车数量已经不能在增加了");

        }


    }


    //修改订单状态
    @Override
    public Result UpDateUserDingDan(String dingdan) throws  WoniuMallException {
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);
        Long aLong = new Long(dingdan);
        Integer xiugaidingdan = mapper.xiugaidingdan(aLong);

        if(xiugaidingdan>0){

            return  Result.success(200,"修改订单成功",xiugaidingdan);
        }else{
            throw new UpdataUserException(4089,"修改订单失败");
        }

    }


    //根据订单编号修改对应商品库存
    @Override
    public Result UpDateGoodsGnumjian(String dingdanbianhao) throws WoniuMallException {

        //通过订单编号获取对应商品数量信息
        GoodsDao mapper = MybatisUtil.getMapper(GoodsDao.class);

        if(null!=dingdanbianhao){

            Long aLong = new Long(dingdanbianhao);
            ArrayList<ShopPingDetails> userShopPingAll = mapper.getUserShopPingAll(aLong);

            //修改数据库对应商品数量
            int num=0;
            for(ShopPingDetails s:userShopPingAll){

                Integer integer = mapper.upDateGoodsNum(s);
                Integer integer1 = mapper.upDateGoodsNumOnline(s);
                num++;
            }
            if(num>0){
                return Result.success(200,"库存减少成功",num);
            }else{
                throw new  UpdataUserException(4092,"修改库存失败");
            }

        }


        return null;
    }

}




