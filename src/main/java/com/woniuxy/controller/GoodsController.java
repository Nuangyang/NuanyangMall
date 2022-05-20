package com.woniuxy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.woniuxy.controller.form.DelShoppingForm;
import com.woniuxy.controller.form.GetByGoodsIdForm;
import com.woniuxy.controller.form.GetGoodsCategoryForm;
import com.woniuxy.controller.form.GetGoodsVagueForm;
import com.woniuxy.model.User;
import com.woniuxy.model.UserSignin;
import com.woniuxy.service.GoodsService;
import com.woniuxy.service.UserService;
import com.woniuxy.service.iofo.UserSigninIofo;
import com.woniuxy.service.serviceImpl.GoodsServiceImpl;
import com.woniuxy.service.serviceImpl.UserServiceImpl;
import com.woniuxy.utils.commons.BaseServlet;
import com.woniuxy.utils.commons.JdkProxyUtil;
import com.woniuxy.utils.commons.Result;
import com.woniuxy.utils.exception.GoodsDelVagueException;
import com.woniuxy.utils.exception.WoniuMallException;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.imageio.IIOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/goods.do")
public class GoodsController extends BaseServlet {
    //事物控制
    GoodsService gs = new JdkProxyUtil<GoodsService>().getProxyObject(new GoodsServiceImpl());
    //获取主页商品
    public void   getOnlineGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //查询所有主页商品
        try {
            Result onlineGoodsAll = gs.getOnlineGoodsAll();
            //获取成功
            if(null!=onlineGoodsAll){
                response.getWriter().write(JSON.toJSONString(onlineGoodsAll));
            }

        } catch (WoniuMallException e) {
            e.printStackTrace();
            //获取失败
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }

    //获取所有商品信息
    public void  getGoodsShow(HttpServletRequest request,HttpServletResponse response) throws IOException {

        try {
            Result goodsShow = gs.getGoodsShow();
            if(null!=goodsShow)response.getWriter().write(JSON.toJSONString(goodsShow));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //分页查询底部导航条
    public void getFenyedao(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            Result fenyeGoods = gs.getFenyeGoods();
            if(null!=fenyeGoods){
                response.getWriter().write(JSON.toJSONString(fenyeGoods));
            }
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //根据用户指定页数展示商品
    public void getPageShow(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String pages = request.getParameter("pages");
        try {
            Result pagesShow = gs.getPagesShow(pages);
            if(null!=pagesShow)response.getWriter().write(JSON.toJSONString(pagesShow));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //模糊查询商品
    public void getVagueGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String vague = request.getParameter("vague");

        if(null!=vague&&vague!=""){
            GetGoodsVagueForm getGoodsVagueForm = new GetGoodsVagueForm(vague);

            try {
                Result vagueByGoods = gs.getVagueByGoods(getGoodsVagueForm);
                response.getWriter().write(JSON.toJSONString(vagueByGoods));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }

    }

    //存储主页模糊查询商品数据
    public void UserByVagueGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String vague = request.getParameter("vague");
        HttpSession session = request.getSession();
        if(null!=vague&&vague!=""){
            session.setAttribute("uservague",vague);
            response.getWriter().write(JSON.toJSONString(Result.success(200,"存储成功",null)));
        }else{
            response.getWriter().write(JSON.toJSONString(Result.fail(4049,"存储失败")));
        }

    }

    //判断用户是否在主页搜索了商品信息
    public void UserByIfVague(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String uservague =(String) session.getAttribute("uservague");

        if(null!=uservague&&uservague!=""){
            //调用模糊查询方法
            GetGoodsVagueForm getGoodsVagueForm = new GetGoodsVagueForm(uservague);
            try {
                Result vagueByGoods = gs.getVagueByGoods(getGoodsVagueForm);
                response.getWriter().write(JSON.toJSONString(vagueByGoods));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }else{
            //没有做查询操作

            response.getWriter().write(JSON.toJSONString(Result.fail(4051,"没有做查询操作")));
        }


    }

    //删除用户主页查询的商品信息
    public  void delGoodsVague(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("uservague");
        response.getWriter().write(JSON.toJSONString(Result.success(200,"删除完成",null)));
    }

    //根据分类展示商品
    public void categoryShowGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String category = request.getParameter("category");
        // 1游戏 2手机 3外设显卡 4化妆品
        //  4     2      6       1
        Integer integer = new Integer(category);

        if(integer==1){
            integer=4;
        }else if(integer==2){
            integer=2;
        }else if(integer==3){
            integer=6;
        }else{
            integer=1;
        }

        try {
            Result categoryByGoods = gs.getCategoryByGoods(new GetGoodsCategoryForm(integer));
            response.getWriter().write(JSON.toJSONString(categoryByGoods));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }
    }

    //主页用户指定商品跳转详情
    public void userGetByGoodsId(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            Integer integer = new Integer(gid);
            GetByGoodsIdForm getByGoodsIdForm = new GetByGoodsIdForm(integer);
            try {
                Result userByGoodsId = gs.getUserByGoodsId(getByGoodsIdForm);

                response.getWriter().write(JSON.toJSONString(Result.success(200,"ID查询成功",userByGoodsId)));

            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }else{

        }
    }

    //所有商品页面用户指定跳转商品详情
    public void getUserByGoodsID(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            Integer integer = new Integer(gid);
            GetByGoodsIdForm getByGoodsIdForm = new GetByGoodsIdForm(integer);
            try {
                Result userByGoodsIdAll = gs.getUserByGoodsIdAll(getByGoodsIdForm);

                response.getWriter().write(JSON.toJSONString(Result.success(200,"ID查询成功",userByGoodsIdAll)));

            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }else{

        }
    }

    //获取购物车商品信息
    public void getShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取登录用户信息
        UserService us = new JdkProxyUtil<UserService>().getProxyObject(new UserServiceImpl());
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result   user1 = us.getUserByuname(user);
            User usc= (User) user1.getData();

            //读取购物车表
            Result userByShopping = gs.getUserByShopping(usc);
            //获取之后通过gid查询对应商品信息

            response.getWriter().write(JSON.toJSONString(userByShopping));

        } catch (WoniuMallException e) {
            e.printStackTrace();

            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //删除购物车商品
    public void delShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid) {
            Integer gsi = new Integer(gid);
            DelShoppingForm delShoppingForm = new DelShoppingForm(gsi);

            //删除商品
            try {
                Result result = gs.delShopping(delShoppingForm);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }
    }

    //添加购物车
    public void addShopping(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            //商品ID
            Integer integer = new Integer(gid);
            GetByGoodsIdForm getByGoodsIdForm = new GetByGoodsIdForm(integer);
            //用户ID
            HttpSession session = request.getSession();
            UserSigninIofo user = (UserSigninIofo) session.getAttribute("user");
            try {
                //通过用户名获取ID
                UserService us = new JdkProxyUtil<UserService>().getProxyObject(new UserServiceImpl());
                Result userByuname = us.getUserByuname(user);
                User data =(User) userByuname.getData();


                Result result = gs.addShopping(data, getByGoodsIdForm);

                response.getWriter().write(JSON.toJSONString(result));

            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }


            //存入数据库


        }

    }

    //添加购物车主页
    public void addShoppingAll(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            //商品ID
            Integer integer = new Integer(gid);
            GetByGoodsIdForm getByGoodsIdForm = new GetByGoodsIdForm(integer);
            //用户ID
            HttpSession session = request.getSession();
            UserSigninIofo user = (UserSigninIofo) session.getAttribute("user");
            try {
                //通过用户名获取ID
                UserService us = new JdkProxyUtil<UserService>().getProxyObject(new UserServiceImpl());
                Result userByuname = us.getUserByuname(user);
                User data =(User) userByuname.getData();


           //获取上线商品数据
                Result result = gs.addShoppingAll(data, getByGoodsIdForm);

                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }



        }

    }

    //获取购物车商品数量
    public void getShoppingBynum(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){

            Integer is=new Integer(gid);
            try {
                Result bynum = gs.getBynum(is);

                response.getWriter().write(JSON.toJSONString(bynum));


            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }else{


        }



    }


    //增加购物车数量
    public void jiaShoppingGnum(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String gid = request.getParameter("gid");

        if(null!=gid){
            Integer integer=new Integer(gid);

            try {
                Result result = gs.jiaShoppingSnum(integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));

            }
        }else{

        }



    }

    //减少购物车数量
    public void  jianShoppingGnum(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            Integer integer=new Integer(gid);

            try {
                Result result = gs.jianShoppingSnum(integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }

    }
}
