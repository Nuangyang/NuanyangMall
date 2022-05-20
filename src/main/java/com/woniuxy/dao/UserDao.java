package com.woniuxy.dao;



import com.woniuxy.controller.form.AddUserRessForm;
import com.woniuxy.controller.form.UserSigninForm;
import com.woniuxy.controller.form.UserUpdaForm;
import com.woniuxy.model.*;

import com.woniuxy.service.iofo.ShopPingDetailsIofo;
import com.woniuxy.service.iofo.UserSigninIofo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


public interface UserDao {


    //根据用户名和密码获取用户对象
    @Select("SELECT * FROM usersignin WHERE uname=#{us.uname} AND upwd=#{us.upwd}")
    public UserSignin getUserSignin(@Param("us") UserSignin usg);

    //根据用户名获取用户对象
    @Select("SELECT * FROM `user` WHERE uname=#{us}")
    public User getUserByuName(@Param("us") String uname);

    //注册用户
    @Insert("INSERT INTO `user` VALUES (DEFAULT,#{us.uname},#{us.upwd},#{us.uemail},#{us.uage},#{us.birthday},#{us.uphone},#{us.address},#{us.state},#{us.head})")
    public Integer addUser(@Param("us") User user);

    //添加用户登录表
    @Insert("INSERT INTO usersignin VALUES(DEFAULT,#{us.uname},#{us.upwd},#{us.state})")
    public Integer addUserSignin(@Param("us") UserSigninForm form);


    //根据用户名获取用户对象
    @Select("SELECT * FROM user WHERE uname=#{us.uname}")
    public User getUserByUname(@Param("us") UserSigninIofo usg);


    //修改用户总表激活状态
    @Update("UPDATE  `user` SET state=1  WHERE  uname=#{us.uname}")
    public Integer Modifyactivation(@Param("us") User user);

    //修改用户登录表激活状态
    @Update("UPDATE  usersignin SET state=1  WHERE  uname=#{us.uname}")
    public Integer ModifyactivationSq(@Param("us") User user);

    //更改用户数据库头像字段
    @Update("UPDATE `user` SET head=#{url} WHERE uid=#{user.uid}")
    public Integer InsertUserTouxiang(@Param("user") User user, @Param("url") String url);

    //更改用户信息
    @Update("UPDATE `user` SET upwd=#{uus.pwd} ,birthday=#{uus.shengri}, uphone=#{uus.shouji} WHERE uid=#{uid}")
    public Integer updateUser(@Param("uus") UserUpdaForm userUpdaForm, @Param("uid") Integer uid);

    //更改登录表用户密码
    @Update("UPDATE `usersignin` SET upwd=#{upwd} WHERE usid=#{uid}")
    public Integer updateUserSignin(@Param("upwd") String upwd, @Param("uid") Integer user);

    //获取用户所有非默认地址
    @Select("SELECT * FROM address WHERE uid=#{u.uid} AND defaults=0 ")
    public ArrayList<Address> getUserAddress(@Param("u") User user);

    //获取用户默认地址
    @Select("SELECT * FROM address WHERE defaults=1 AND uid=#{us.uid}")
    public Address getUserAddressDfulft(@Param("us") User user);


    //获取省集合
    @Select("SELECT * FROM sheng")
    public ArrayList<Province> getProvince();

    //通过sid获取市集合
    @Select("SELECT * FROM shi WHERE sid=#{sid}")
    public ArrayList<City> getCity(@Param("sid") int sid);


    //通过省ID获取省名
    @Select("SELECT * FROM sheng WHERE id=#{ss.sheng}")
    public  Province getProvinceAll(@Param("ss") AddUserRessForm addUserRessForm);

    //通过市ID获取市名
    @Select("SELECT * FROM shi WHERE id=#{ss.shi}")
    public  City getCityAll(@Param("ss") AddUserRessForm addUserRessForm);


    //添加地址信息
    @Insert("INSERT INTO address VALUES (DEFAULT,#{name.sname},#{name.sphon},#{xi},#{us.uid},0)")
    public Integer addUserRess(@Param("us") User user, @Param("xi") String str, @Param("name") AddUserRessForm addUserRessForm);


    //删除地址信息
    @Delete("DELETE FROM address WHERE uid=#{us.uid} AND aid=#{aid}")
    public Integer delUserRess(@Param("us") User user, @Param("aid") Integer integer);



    //修改用户默认地址信息  去旧
    @Update("UPDATE address SET defaults=0 WHERE aid=#{aid}")
    public Integer upDateUserRess(@Param("us") User user, @Param("aid") Integer integer);


    //修改用户默认地址信息  换新
    @Update("UPDATE address SET defaults=1 WHERE aid=#{aid}")
    public Integer upDateUserResscoco(@Param("us") User user, @Param("aid") Integer integer);


    //获取用户购物车所有商品数据
    @Select("SELECT * FROM shopping WHERE uid=#{uid.uid}")
    public ArrayList<ShopPing> getUserShopPing(@Param("uid") User userSigninIofo);

    //根据商品ID获取商品对象
    @Select("SELECT * FROM goods WHERE gid=#{goo.gid}")
    public Goods getUserAddShopPingGoods(@Param("goo") ShopPing shopPing);

    //添加至订单信息
    @Insert("INSERT INTO shopping_details VALUES (DEFAULT,#{go.ganme},#{go.gnum},#{go.gprive},#{go.iid},#{go.address},#{go.creationtime},#{go.dinstart},#{go.uid},#{go.uuid})")
    public  Integer addGoodsShopPing(@Param("go") ShopPingDetailsIofo shopPingDetailsIofo);

    //通过uid和gid删除对应的购物车信息
    @Delete("DELETE FROM shopping WHERE uid=#{du.uid} AND gid=#{gid}")
    public  Integer delUserShopPing(@Param("du") ShopPingDetailsIofo shopPingDetailsIofo, @Param("gid") Integer gid);


    //通过gid与uid获取用户购物车数据
    @Select("SELECT * FROM shopping WHERE uid=#{uid} AND gid=#{gid}")
    public ArrayList<ShopPing> getUserShopPingw(@Param("uid") Integer uid, @Param("gid") Integer gid);

    //通过用户的ui与订单编号获取订单信息
    @Select("SELECT * FROM shopping_details WHERE uid=#{uid.uid} AND uuid=#{uuid}")
    public ArrayList<ShopPingDetails> getUserShopPingAllShow(@Param("uid") User user, @Param("uuid") long uuid);

    //获取用户所有订单信息
    @Select("SELECT * FROM shopping_details WHERE uid=#{us.uid}")
    public ArrayList<ShopPingDetails> getUserShopPingDetailsAll(@Param("us") User user);


    //通过用户的ui与订单编号获取订单信息
    @Select("SELECT * FROM shopping_details WHERE uid=#{uid.uid} AND sdid=#{sdid}")
    public ArrayList<ShopPingDetails> getUserShopPingAll(@Param("uid") User user, @Param("sdid") Integer sdid);


}
