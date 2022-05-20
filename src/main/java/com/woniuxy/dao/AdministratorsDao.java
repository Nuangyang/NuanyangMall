package com.woniuxy.dao;


import com.woniuxy.controller.form.AddGoodsAdministForm;
import com.woniuxy.controller.form.AdministratorsSigninForm;
import com.woniuxy.controller.form.XiuGaiGoodsAdministForm;
import com.woniuxy.model.*;
import com.woniuxy.utils.commons.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface AdministratorsDao {


    //根据用户名密码获取管理员对象
    @Select("SELECT * FROM administrators WHERE adname=#{ads.adname} AND adpwd=#{ads.adpwd}")
    public AdministraTors getAdministrators(@Param("ads") AdministraTors asf);

    //获取商品分类信息
    @Select("SELECT * FROM category")
    public ArrayList<Category> getCategoryAdministrators();

    //查询所有商品
    @Select("SELECT * FROM goods WHERE gnum>0 ")
    public ArrayList<Goods> getGoodsShow();

    //获取所有用户信息
    @Select("SELECT * FROM user")
    public ArrayList<User> getUserShow();

    //添加购物车商品
    @Insert("INSERT INTO goods VALUES (DEFAULT,#{add.gname},#{add.gnum},#{add.gprive},#{add.gdescribe},#{add.gcategory},#{add.img})")
    public Integer addGoodsAdministrators(@Param("add") AddGoodsAdministForm goodsAdministForm);

    //修改商品库存(下架)
    @Update("UPDATE goods  SET gnum=0 WHERE gid=#{gid}")
    public Integer delGoodsAdministrators(@Param("gid") Integer gid);

    //通过商品名获取商品对象
    @Select("SELECT * FROM goods WHERE gname=#{str}")
    public Goods getGoodsByName(@Param("str") String string);

    //修改商品属性
    @Update("UPDATE goods SET gname=#{xiugai.gname},gnum=#{xiugai.gnum},gprive=#{xiugai.gprive} WHERE gid=#{gid}")
    public Integer XiugaiGoodsShuXing(@Param("xiugai") XiuGaiGoodsAdministForm xiuGaiGoodsAdministForm, @Param("gid") Integer gids);

    //禁用用户(总表)
    @Update("UPDATE `user` SET state=4 WHERE uid=#{uid}")
    public Integer jingYongUser(@Param("uid") Integer uid);
    //禁用用户（登录表）
    @Update("UPDATE usersignin SET state=4 WHERE usid=#{uid}")
    public Integer jingYongUserSignin(@Param("uid") Integer uid);

    //解禁用户(总表)
    @Update("UPDATE `user` SET state=1 WHERE uid=#{uid}")
    public Integer jiejingYongUser(@Param("uid") Integer uid);
    //解禁用户（登录表）
    @Update("UPDATE usersignin SET state=1 WHERE usid=#{uid}")
    public Integer jiejingYongUserSignin(@Param("uid") Integer uid);

    //获取所有的管理员对象
    @Select("SELECT * FROM administrators")
    public  ArrayList<AdministraTors> getAdmin();

    //根据管理员ID获取管理员对象
    @Select("SELECT * FROM administrators WHERE bsid=#{id}")
    public AdministraTors getAiministraTorsUpDtae(@Param("id") Integer integer);
    //修改管理员密码
    @Update("UPDATE administrators SET adpwd=#{pwd} WHERE bsid=#{bsid}")
    public Integer UpdataAiministraTors(@Param("bsid") Integer integer, @Param("pwd") String pwd);

    //获取所有订单信息
    @Select("SELECT * FROM shopping_details GROUP BY uuid")
    public ArrayList<ShopPingDetails> getUserAllShopPingss();


}
