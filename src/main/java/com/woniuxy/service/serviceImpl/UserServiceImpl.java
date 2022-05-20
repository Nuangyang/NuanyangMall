package com.woniuxy.service.serviceImpl;

import com.sun.org.apache.xalan.internal.xsltc.trax.StAXStream2SAX;
import com.woniuxy.controller.form.*;

import com.woniuxy.dao.UserDao;
import com.woniuxy.model.*;

import com.woniuxy.service.UserService;
import com.woniuxy.service.iofo.GetUserByzhuyeIofo;
import com.woniuxy.service.iofo.ShopPingDetailsIofo;
import com.woniuxy.service.iofo.UserSigninIofo;
import com.woniuxy.utils.commons.*;
import com.woniuxy.utils.exception.*;
import jdk.internal.org.objectweb.asm.commons.RemappingAnnotationAdapter;
import org.apache.ibatis.annotations.Insert;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.List;


public class UserServiceImpl  implements UserService {



    //获取登录对象
    @Override
    public Result getUser(UserSigninForm userSigninForm) throws WoniuMallException {


        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        //根据用户传递过来的参数查询数据库 获取对应用户对象


        UserSignin signin = new UserSignin(0, userSigninForm.getUname(), userSigninForm.getUpwd(), userSigninForm.getState());
        UserSignin userSignin = mapper.getUserSignin(signin);
        //new 一个返回类型对象 获取到需要返回客户端的数据

        if(null!=userSignin){
            UserSigninIofo userSigninIofo = new UserSigninIofo(userSignin.getUname(),userSignin.getUpwd(),userSignin.getState());
            if(null==userSigninIofo) {
                throw new SigninException(2331, "用户不存在");
            }else if(!userSigninIofo.getUpwd().equals(userSigninForm.getUpwd())){
                throw  new PassworderrorException(2332,"密码错误");
            }else{
                return Result.success(200,"登录成功",userSigninIofo);
            }

        }else{
            System.out.println("空");
        }

        return null;
    }


    //判断用户名是否已存在
    @Override
    public Result judgeUser(String uname) throws RxistenceException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByuName = mapper.getUserByuName(uname);

        if(null!=userByuName){
            throw new RxistenceException(5001,"用户已存在");
        }else{
            return Result.success(200,"用户名可以注册",null);
        }




    }

    //增加用户
    @Override
    public Result addUser(UserAddForm uaf)  throws WoniuMallException {

        //加密用户密码
        String md5 = Md5Util.getMd5(uaf.getUpwd());

        User user = new User(0, uaf.getUname(), md5, uaf.getEmail(), "", "", "", "", 0,"");
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        Integer integer = mapper.addUser(user);

        if(integer==0){
            //添加失败
           throw new  AddtoException(4040,"添加失败");
        }else{
            return Result.success(200,"注册成功",null);
        }


    }

    //添加用户登录表
    @Override
    public Result addUserBySignin(UserSigninForm usf) throws WoniuMallException{

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //加密
        String md5 = Md5Util.getMd5(usf.getUpwd());
        usf.setUpwd(md5);
        Integer integer = mapper.addUserSignin(usf);

        if(integer==0){
            throw new  AddtoException(4040,"添加失败");
        }else{
            return Result.success(200,"注册成功",null);
        }


}


    //获取主页用户部分展示信息
    @Override
    public Result getUserByzhuye(GetUserByzhuyeForm gub) throws WoniuMallException {
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByuName = mapper.getUserByuName(gub.getUname());

        if(null!=userByuName){
            GetUserByzhuyeIofo getUserByzhuyeIofo = new GetUserByzhuyeIofo(userByuName.getUname(),userByuName.getState(),userByuName.getHead());
            return Result.success(200,"查询成功",getUserByzhuyeIofo);
        }else{
            throw  new RxistenceException(4040,"没有查询到用户");
        }

    }


    //根据用户名获取用户信息
    @Override
    public Result getUserByuname(UserSigninIofo user) throws WoniuMallException {


        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByUname = mapper.getUserByUname(user);


        if(null!=userByUname){
            return Result.success(200,"获取成功",userByUname);
        }else{
            throw  new EmailException(4047,"查询失败");
        }

    }


    //发送激活邮件
    @Override
    public Result ModifyActivation(User user) throws  WoniuMallException{

        EmailjihuoUtil email=new EmailjihuoUtil();

        if(null!=user){
            String s = email.EmailUtil(user.getUemail());

            if(null!=s){
                return Result.success(200,"发送成功",s);
            }else {
                throw new EmailException(4056,"邮件发送异常");
            }
        }else{
            throw new RxistenceException(4040,"用户不存在异常");
        }

    }

    //激活账户
    @Override
    public Result ActivationUser(User user) throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        Integer mo = mapper.Modifyactivation(user);

        Integer in = mapper.ModifyactivationSq(user);
        if(mo!=0&&in!=0){
            return Result.success(200,"状态修改成功",null);
        }else{
            throw  new EmailException(4057,"激活状态修改失败");
        }

    }

    //更新头像
    @Override
    public Result updaUserTouxiang(UserSigninIofo usf, String lujing) throws WoniuMallException{

        //通过用户名获取用户ID
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        if(null!=usf){
            User userByUname = mapper.getUserByUname(usf);

            if(null!=lujing){
                //更改用户数据库头像字段
                Integer integer = mapper.InsertUserTouxiang(userByUname, lujing);

                if(integer>0){
                    return Result.success(200,"修改成功",integer);
                }else{

                    throw new UpdaUserTouxiangException(4066,"修改头像失败");
                }
            }else{
                throw new UpdaUserTouxiangException(4066,"修改头像失败");
            }
        }else{
            throw new UpdaUserTouxiangException(4066,"修改头像失败");
        }

    }

    //修改用户信息
    @Override
    public Result updaUserXingXi(UserUpdaForm uuf,UserSigninIofo user) throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        //获取当前在线用户ID
        User userByUname = mapper.getUserByUname(user);

        //密码加密
        uuf.setPwd(Md5Util.getMd5(uuf.getPwd()));


        Integer integer = mapper.updateUser(uuf, userByUname.getUid());
        Integer integer1 = mapper.updateUserSignin(uuf.getPwd(), userByUname.getUid());

        if(integer>0&&integer1>0){
            return Result.success(200,"修改用户数据成功",integer);

        }else{
            throw new  UpdataUserException(4059,"修改用户数据失败");
        }

    }


    //获取用户地址信息
    @Override
    public Result getUserAddressAll(UserSigninIofo uuf) throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        //获取用户ID
        User userByUname = mapper.getUserByUname(uuf);

        ArrayList<Address> userAddress = mapper.getUserAddress(userByUname);

        if(null!=userAddress){
            return Result.success(200,"地址查询成功",userAddress);
        }else{

            throw new UserAddressException(4071,"地址查询失败");
        }

    }

    //获取用户默认地址信息
    @Override
    public Result getUsermorenAddresss(UserSigninIofo uuf) throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        //获取用户ID
        User userByUname = mapper.getUserByuName(uuf.getUname());

        //获取用户地址信息
        Address userAddressDfulft = mapper.getUserAddressDfulft(userByUname);

        if(null!=userAddressDfulft){

            return Result.success(200,"默认地址查询成功",userAddressDfulft);
        }else{
            throw new UserAddressException(40220,"地址查询失败");
        }
    }


    //添加用户地址信息
    @Override
    public Result addUserRess(UserSigninIofo user, AddUserRessForm addUserRessForm)throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //获取用户信息
        User userByUname = mapper.getUserByUname(user);

        //获取省市ID对应的数据
        Province provinceAll = mapper.getProvinceAll(addUserRessForm);
        City cityAll = mapper.getCityAll(addUserRessForm);
        String str=provinceAll.getName();
        str+=cityAll.getName();
        str+=addUserRessForm.getXiangqing();
        //添加地址数据库

        Integer integer = mapper.addUserRess(userByUname, str,addUserRessForm);
        if(integer>0){

             return  Result.success(200,"添加成功",integer);
        }else{
            throw  new  AddUserRessExcetpion(4074,"地址添加失败");
        }
    }

    //删除地址信息
    @Override
    public Result delUserRessById(UserSigninIofo userSigninIofo, Integer aid) throws WoniuMallException{

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //获取用户对象
        User userByUname = mapper.getUserByUname(userSigninIofo);
        //删除数据库地址信息
        Integer integer = mapper.delUserRess(userByUname, aid);

        if(integer>0){
            return Result.success(200,"地址删除成功",integer);

        }else{
            throw  new  AddUserRessExcetpion(4074,"地址删除失败");
        }

    }

    //更换默认地址
    @Override
    public Result gengHuangRess(UserSigninIofo userSigninIofo,Integer aid)throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //获取要作操作得用户对象
        User userByUname = mapper.getUserByUname(userSigninIofo);

        //删除之前得默认地址
        Address userAddressDfulft = mapper.getUserAddressDfulft(userByUname);
        Integer integer1=1;
        if(null!=userAddressDfulft){
            integer1 = mapper.upDateUserRess(userByUname, userAddressDfulft.getAid());
        }


        if(integer1>0){
            //更新新的默认地址
            Integer integer = mapper.upDateUserResscoco(userByUname, aid);
            if(integer>0){
                return Result.success(200,"地址更换成功",integer);
            }else{
                throw  new  AddUserRessExcetpion(4076,"地址更换失败");
            }
        }else{
            throw  new  AddUserRessExcetpion(4076,"地址更换失败");
        }


    }


    //结算用户购物车所有商品
    @Override
    public Result getUserShoPingByAll(UserSigninIofo userSigninIofo)throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //获取用户对象
        User userByUname = mapper.getUserByUname(userSigninIofo);

        ArrayList<ShopPing> userShopPing = mapper.getUserShopPing(userByUname);
        //存入订单结算表
        //生成订单编号
        int max=9999999,min=100000;
        long ran2 = (int) (Math.random()*(max-min)+min);
        int is=0;
        for(ShopPing set:userShopPing){
           //获取商品对象
            Goods userAddShopPingGoods = mapper.getUserAddShopPingGoods(set);

            ShopPingDetailsIofo Iofo = new ShopPingDetailsIofo();
            Iofo.setGanme(userAddShopPingGoods.getGname());
            Iofo.setGnum(set.getSnum());
            Iofo.setGprive(userAddShopPingGoods.getGprive()*Iofo.getGnum());
            Iofo.setIid(userAddShopPingGoods.getImg());
            //当前系统时间 距离1970 1.1号的毫秒
            long l = System.currentTimeMillis();
            Iofo.setCreationtime(l);
            Iofo.setAddress(userByUname.getAddress());
            //1 待支付  2 支付成功
            Iofo.setDinstart(1);
            Iofo.setUid(userByUname.getUid());
            Iofo.setUuid(ran2);
            //添加至订单信息
            Integer integer = mapper. addGoodsShopPing(Iofo);


            //删除对应的购物车数据
            Integer integer1 = mapper.delUserShopPing(Iofo, set.getGid());



            if(integer>0&&integer1>0){
                is++;
            }
        }


        if(is>0){
            return Result.success(200,"获取购物车数据成功",ran2);
        }else{
            throw new AddUserShopPingExcetpion(4083,"获取购物车异常");
        }

    }

    //结算用户购物车所选商品
    @Override
    public Result getUserShoPingBy(UserSigninIofo userSigninIofo, List<Integer> strings)throws WoniuMallException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        //获取用户对象
        User userByUname = mapper.getUserByUname(userSigninIofo);

        //通过gid和uid获取对应购物车表数据
        //生成订单编号
        int max=9999999,min=100000;
        long ran2 = (int) (Math.random()*(max-min)+min);
        int is=0;
        for(Integer integer:strings){
            //获取用户购物车需要提交的订单
            ArrayList<ShopPing> userShopPing = mapper.getUserShopPingw(userByUname.getUid(), integer);
            for(ShopPing sp:userShopPing){
                //获取商品对象
                Goods userAddShopPingGoods = mapper.getUserAddShopPingGoods(sp);

                //常见订单对象
                ShopPingDetailsIofo Iofo = new ShopPingDetailsIofo();
                Iofo.setGanme(userAddShopPingGoods.getGname());
                Iofo.setGnum(sp.getSnum());
                Iofo.setGprive(userAddShopPingGoods.getGprive()*sp.getSnum());
                Iofo.setIid(userAddShopPingGoods.getImg());
                Iofo.setAddress(userByUname.getAddress());
                Iofo.setCreationtime(System.currentTimeMillis());
                //1 待支付  2 支付成功
               Iofo.setDinstart(1);
                Iofo.setUid(userByUname.getUid());
                Iofo.setUuid(ran2);

                //添加至订单信息
                Integer ssd = mapper. addGoodsShopPing(Iofo);


                //删除对应的购物车数据
                Integer delshopping = mapper.delUserShopPing(Iofo, sp.getGid());



                if(ssd>0&&delshopping>0){
                    is++;
                }

            }


        }


        if(is>0){
            return Result.success(200,"获取购物车数据成功",ran2);
        }else{
            throw new AddUserShopPingExcetpion(4084,"获取购物车异常");
        }

    }

    //通过订单编号与用户ID获取订单表对应数据
    @Override
    public Result getUserShopPingByUidByUUid(UserSigninIofo userSigninIofo, long uuid)throws WoniuMallException {

      //获取用户对象
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByUname = mapper.getUserByUname(userSigninIofo);

        ArrayList<ShopPingDetails> userShopPingAllShow = mapper.getUserShopPingAllShow(userByUname, uuid);

        if(null!=userShopPingAllShow){

            return Result.success(200,"获取订单成功",userShopPingAllShow);
        }else{

            throw  new GetGoodsAdministratorsExceotion(4088,"订单查询失败");
        }

    }

   //获取用户所有订单信息
    @Override
    public Result getUserDingDanAll(UserSigninIofo userSigninIofo)throws WoniuMallException {

        //获取用户对象
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByUname = mapper.getUserByUname(userSigninIofo);

        ArrayList<ShopPingDetails> userShopPingDetailsAll = mapper.getUserShopPingDetailsAll(userByUname);

        if(null!=userShopPingDetailsAll&&userShopPingDetailsAll.size()>0){

            return Result.success(200,"订单获取成功",userShopPingDetailsAll);

        }else{

            throw  new GetUserDingDanAllException(4849,"获取订单异常");
        }
    }


    //通过订单ID获取订单信息
    @Override
    public Result getUserDingDandanyi(UserSigninIofo userSigninIofo, Integer sdid)throws WoniuMallException {

        //获取用户对象
        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        User userByUname = mapper.getUserByUname(userSigninIofo);

        ArrayList<ShopPingDetails> userShopPingAll = mapper.getUserShopPingAll(userByUname, sdid);

        if(null!=userShopPingAll&&userShopPingAll.size()>0){

            return Result.success(200,"订单详情查询成功",userShopPingAll);

        }else{

            throw  new GetUserDingDanAllException(4849,"获取订单异常");

        }

    }


}
