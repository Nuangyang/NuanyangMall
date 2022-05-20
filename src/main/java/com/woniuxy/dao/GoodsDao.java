package com.woniuxy.dao;

import com.woniuxy.controller.form.DelShoppingForm;
import com.woniuxy.controller.form.GetByGoodsIdForm;
import com.woniuxy.controller.form.GetGoodsCategoryForm;
import com.woniuxy.controller.form.GetGoodsVagueForm;
import com.woniuxy.model.*;
import com.woniuxy.service.iofo.GoodsOnlineIofo;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface GoodsDao {


    //查询所有主页商品
    @Select("SELECT * FROM goods_online ")
    public ArrayList<GoodsOnlineIofo> getGoodsOnline();

    //查询所有商品
    @Select("SELECT * FROM goods WHERE gnum>0")
    public ArrayList<Goods> getGoodsShow();

    //模糊查询商品
    @Select("SELECT * FROM goods  WHERE gname LIKE #{va.gname} AND gnum>0")
    public ArrayList<Goods> getVague(@Param("va") GetGoodsVagueForm vague);

    //根据商品分类获取商品
    @Select("SELECT * FROM goods WHERE gcategory=#{cg.caid} AND gnum>0")
    public ArrayList<Goods> getCategory(@Param("cg") GetGoodsCategoryForm cg);

    //主页根据商品ID查询商品对应信息
    @Select("SELECT * FROM goods_online WHERE gid=#{id.gid} AND gnum>0")
    public  Goods getIdGoods(@Param("id") GetByGoodsIdForm gs);

    //所有根据商品ID查询商品对应信息
    @Select("SELECT * FROM goods WHERE gid=#{gids.gid} AND gnum>0")
    public  Goods getIdGoodsBy(@Param("gids") GetByGoodsIdForm gs);

    //根据用户ID查询对应购物车数据
    @Select("SELECT * FROM shopping WHERE uid=#{user.uid}")
    public ArrayList<ShopPing> getShopping(@Param("user") User user);

    //所有根据购物车商品ID查询商品对应信息
    @Select("SELECT * FROM goods WHERE gid=#{gids.gid}")
    public  Goods getIdShopPingGoodsBy(@Param("gids") ShopPing gs);

    //根据gid删除购物车数据
    @Delete("DELETE FROM shopping WHERE gid=#{dsp.gid}")
    public Integer delShopping(@Param("dsp") DelShoppingForm ds);

    //通过商品ID获取商品对应信息
    @Select("SELECT * FROM goods WHERE gid=#{ggbi.gid}")
    public Goods getGoodsByIdShopping(@Param("ggbi") GetByGoodsIdForm getByGoodsIdForm);

    //通过商品ID获取主页商品对应信息
    @Select("SELECT * FROM goods_online WHERE gid=#{ggbi.gid}")
    public Goods getGoodsByIdShoppingAll(@Param("ggbi") GetByGoodsIdForm getByGoodsIdForm);


    //添加购物车数据
    @Insert("INSERT INTO shopping VALUES (DEFAULT,#{user.uid},#{goods.gid},1)")
    public Integer addGoodsByShopping(@Param("user") User signin, @Param("goods") Goods goods);

    //通过上线购物的商品名获取主表商品对象
    @Select("SELECT * FROM goods WHERE gname=#{goods.gname}")
    public Goods getShoppingGoodsByOnline(@Param("goods") Goods goods);

    //根据商品ID获取购物车对象
    @Select("SELECT * FROM shopping WHERE gid=#{goods.gid}")
    public ShopPing getShoPingByGoodsId(@Param("goods") Goods goods);

    //添加商品数量
    @Insert("UPDATE shopping  SET snum=snum+1 WHERE gid=#{goods.gid}")
    public Integer addShoPingBynum(@Param("goods") ShopPing shopPing);


    //根据商品ID获取购物车对象
    @Select("SELECT * FROM shopping WHERE gid=#{goods}")
    public ShopPing getShoPingById(@Param("goods") Integer goods);

    //根据商品ID减少对应商品数量
    @Update("UPDATE shopping  SET snum=snum-1 WHERE gid=#{gid}")
    public Integer jianShopingByNum(@Param("gid") Integer integer);


    //支付成功
    @Update("UPDATE shopping_details SET dinstart=2 WHERE uuid=#{uuid}")
    public Integer xiugaidingdan(@Param("uuid") long uii);


    //通过订单编号获取订单数据
    @Select("SELECT * FROM shopping_details WHERE uuid=#{uid}")
    public ArrayList<ShopPingDetails> getUserShopPingAll(@Param("uid") long sas);

    //通过gname减少对应商品num
    @Update("UPDATE goods SET gnum=gnum-#{ss.gnum} WHERE gname=#{ss.ganme}")
    public Integer upDateGoodsNum(@Param("ss") ShopPingDetails shopPingDetails);

    //通过gname减少对应商品num
    @Update("UPDATE goods_online SET gnum=gnum-#{ss.gnum} WHERE gname=#{ss.ganme}")
    public Integer upDateGoodsNumOnline(@Param("ss") ShopPingDetails shopPingDetails);
}
